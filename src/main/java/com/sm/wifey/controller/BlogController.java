package com.sm.wifey.controller;

import com.sm.wifey.model.BlogComment;
import com.sm.wifey.model.BlogPost;
import com.sm.wifey.service.BlogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping
    public String getBlog(@RequestParam(defaultValue = "0") int page, HttpServletRequest request, Model model) {
        model.addAttribute("postPage", blogService.blogPostPageRequest(page, 12));
        model.addAttribute("updateFlag", false);
        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("title", "Blog");
        model.addAttribute("content", "blog/index");
        return "layout";
    }

    @GetMapping("/search")
    public String searchBlogs(@RequestParam("query") String query, Model model, HttpServletRequest request) {

        List<BlogPost> searchResults = blogService.searchPosts(query);
        Pageable pageable = PageRequest.of(0, 12);

        // Sub-list to simulate pagination
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), searchResults.size());
        List<BlogPost> pagedList = searchResults.subList(start, end);

        // Wrap in PageImpl
        Page<BlogPost> blogPage = new PageImpl<>(pagedList, pageable, searchResults.size());

        model.addAttribute("postPage", blogPage);
        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("title", "Blog");
        model.addAttribute("content", "blog/index");
        return "layout";
    }

    @GetMapping("/{path}")
    public String getPostByPath(@PathVariable("path") String path, HttpServletRequest request, Model model) {

        BlogPost blogPost = blogService.findBlogPostByBlogUrl(path);
        List<BlogPost> relatedPosts = blogService.findBlogPostByTags(blogPost.getTags())
                .stream().filter(post -> !post.getId().equals(blogPost.getId())).toList();;

        model.addAttribute("post", blogPost);
        model.addAttribute("title", blogPost.getTitle());
        model.addAttribute("content", "blog/post");
        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("relatedPosts", relatedPosts);

        return "layout";
    }

    @PostMapping("/{postId}/comment")
    public String addComment(@PathVariable Long postId, @RequestParam(required = false) Long comId, BlogComment comment) {
        blogService.addComment(postId, comId, comment);
        return "redirect:/blog/" + blogService.findBlogPostById(postId).getBlogUrl();
    }

    @ResponseBody
    @PostMapping("/{postId}/react/{reaction}")
    public String addReaction(@PathVariable Long postId, @PathVariable String reaction) {
        int newCount = blogService.addReaction(postId, reaction);
        return "{\"success\": true, \"newCount\": " + newCount + "}";
    }

}
