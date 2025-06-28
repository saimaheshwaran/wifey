package com.sm.wifey.service;

import com.sm.wifey.model.BlogComment;
import com.sm.wifey.model.BlogPost;
import com.sm.wifey.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BlogService {

    @Autowired
    BlogPostRepository blogPostRepository;

    public Page<BlogPost> blogPostPageRequest(int page, int pageSize) {
        return blogPostRepository.findAll(
                PageRequest.of( page, pageSize,
                        Sort.by(Sort.Direction.DESC, "publishedDate")));
    }

    public BlogPost findBlogPostById(Long id) {
        return blogPostRepository.findById(id).orElseThrow();
    }

    public BlogPost findBlogPostByBlogUrl(String url) {
        return blogPostRepository.findBlogPostByBlogUrl(url);
    }

    public List<BlogPost> searchPosts(String keyword) { return blogPostRepository.searchPosts(keyword); }

    public List<BlogPost> findBlogPostByTags(List<String> tags) { return blogPostRepository.findBlogPostByTags(tags); }

    public void addComment(Long postId, Long comId, BlogComment comment) {

        BlogPost post = findBlogPostById(postId);
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setShowEnabled(true);

        // If this is a reply, find the parent comment
        if (comId != null) {
            BlogComment parentComment = post.getComments().stream()
                    .filter(c -> c.getId().equals(comId))
                    .findFirst()
                    .orElse(null);

            if (parentComment != null) {
                comment.setParentComment(parentComment);
                parentComment.getReplies().add(comment);
            }
        } else {
            post.getComments().add(comment);
        }

        blogPostRepository.save(post);
    }

    public int addReaction(Long postId, String reaction) {
        BlogPost post = blogPostRepository.findBlogPostById(postId);
        switch (reaction) {
            case "like":
                post.setLikeCount(post.getLikeCount() + 1);
                break;
            case "love":
                post.setLoveCount(post.getLoveCount() + 1);
                break;
            case "thanks":
                post.setThanksCount(post.getThanksCount() + 1);
                break;
        }
        blogPostRepository.save(post);
        return getReactionCount(post, reaction);
    }

    private int getReactionCount(BlogPost post, String reaction) {
        return switch (reaction) {
            case "like" -> post.getLikeCount();
            case "love" -> post.getLoveCount();
            case "thanks" -> post.getThanksCount();
            default -> 0;
        };
    }

}
