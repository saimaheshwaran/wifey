package com.sm.wifey.repository;

import com.sm.wifey.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    BlogPost findBlogPostByBlogUrl(String url);

    BlogPost findBlogPostById(Long postId);

    @Query(value = "select * from blog_post where id in (select blog_post_id from Blog_post_tags where tags in :tags)", nativeQuery = true)
    List<BlogPost> findBlogPostByTags(@Param("tags") List<String> tags);

    @Query(value = "select * from blog_post where title like %?1% or summary like %?1% or content like %?1%", nativeQuery = true)
    List<BlogPost> searchPosts(String keyword);

}
