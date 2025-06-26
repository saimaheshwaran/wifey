package com.sm.wifey.repository;

import com.sm.wifey.model.BlogPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    BlogPost findBlogPostByBlogUrl(String url);

    BlogPost findBlogPostById(Long postId);

    @Query(value = "select * from blog_post where title like %?1% or summary like %?1% or content like %?1%", nativeQuery = true)
    List<BlogPost> searchPosts(String keyword);

}
