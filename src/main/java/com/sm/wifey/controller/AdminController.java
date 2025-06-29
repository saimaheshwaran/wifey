package com.sm.wifey.controller;

import com.sm.wifey.model.BlogPost;
import com.sm.wifey.repository.AuthorRepository;
import com.sm.wifey.service.BlogCommentService;
import com.sm.wifey.service.BlogPostService;
import com.sm.wifey.service.ContactService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller @AllArgsConstructor
@RequestMapping("admin")
public class AdminController {

    BlogPostService blogPostService;
    ContactService contactService;
    AuthorRepository authorRepository;
    BlogCommentService blogCommentService;

    @GetMapping
    public String getAdmin(HttpServletRequest request, Model model) {
        model.addAttribute("title", "Admin");
        model.addAttribute("content", "admin/index");
        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/layout";
    }

    @GetMapping("/blog")
    public String getBlog(@RequestParam(defaultValue = "0") int page, HttpServletRequest request, Model model) {
        model.addAttribute("postPage", blogPostService.blogPostPageRequest(page, 12));
        model.addAttribute("updateFlag", true);
        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("title", "Admin/Blog");
        model.addAttribute("content", "blog/index");
        return "admin/layout";
    }

    @GetMapping("/blog/{path}")
    public String getPostByPath(@PathVariable("path") String path, HttpServletRequest request, Model model) {

        BlogPost blogPost = blogPostService.findBlogPostByBlogUrl(path);

        model.addAttribute("blogPost", blogPost);
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("title", "Admin/Blog/Update");
        model.addAttribute("content", "admin/blog/add");
        model.addAttribute("requestURI", request.getRequestURI());

        return "admin/layout";
    }

    @GetMapping("/blog/add")
    public String getBlogForm(@ModelAttribute BlogPost blogPost, HttpServletRequest request, Model model) {
        model.addAttribute("blogPost", new BlogPost());
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("title", "Admin/Blog");
        model.addAttribute("content", "admin/blog/add");
        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/layout";
    }

    @PostMapping("/blog/add")
    public String addBlogPost(@ModelAttribute BlogPost blogPost) {
        BlogPost post = blogPostService.findBlogPostByBlogUrl(blogPost.getBlogUrl());
        if(post == null) {
            blogPostService.save(blogPost);
            return "redirect:/admin/blog/add?success";
        }
        else {
            post.setAuthor(blogPost.getAuthor());
            post.setTitle(blogPost.getTitle());
            post.setSummary(blogPost.getSummary());
            post.setContent(blogPost.getContent());
            post.setBlogUrl(blogPost.getBlogUrl());
            post.setImageUrl(blogPost.getImageUrl());
            post.setTags(blogPost.getTags());
            post.setCommentsEnabled(blogPost.getCommentsEnabled());
            blogPostService.save(post);
            return "redirect:/admin/blog?success";
        }
    }

    @GetMapping("comment")
    public String getComments(@RequestParam(defaultValue = "0") int page, Model model, HttpServletRequest request) {

        model.addAttribute("title", "Admin/Comments");
        model.addAttribute("content", "admin/comment");
        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("commentPage",
                blogCommentService.commentPageRequest(page, 10));
        return "admin/layout";
    }

    @PostMapping("commentEnabled/toggle/{id}")
    public String toggleCommentVisibility(@PathVariable Long id, @RequestParam(defaultValue = "0") int page, RedirectAttributes redirectAttributes) {
        blogCommentService.toggleCommentVisibility(id);
        redirectAttributes.addAttribute("page", page);
        return "redirect:/admin/comment";
    }

    @GetMapping("contact")
    public String getContacts(@RequestParam(defaultValue = "0") int page, Model model, HttpServletRequest request) {

        model.addAttribute("title", "Admin/Contacts");
        model.addAttribute("content", "admin/contact");
        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("contactPage",
                contactService.contactPageRequest(page, 10));
        return "admin/layout";
    }

    @GetMapping("download-db")
    public ResponseEntity<Resource> downloadDatabase() throws IOException {
        File file = new File("./src/main/resources/static/database/data.mv.db");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        String dateTime = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename="+ dateTime +"_data.mv.db")
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
