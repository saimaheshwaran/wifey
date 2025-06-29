package com.sm.wifey.repository;

import com.sm.wifey.model.BlogComment;
import com.sm.wifey.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogCommentRepository extends JpaRepository<BlogComment, Long> {

    @Query(value = "select * from blog_comment order by created_at desc", nativeQuery = true)
    List<Contact> getAllByLatestDate();

    BlogComment findBlogCommentById(Long id);

}
