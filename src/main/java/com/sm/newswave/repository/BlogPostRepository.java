package com.sm.newswave.repository;

import com.sm.newswave.model.BlogPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    List<BlogPost> findTopByOrderByPublishedDateDesc(Pageable pageable);

    BlogPost findBlogPostByBlogUrl(String url);

    BlogPost findBlogPostById(Long postId);
}
