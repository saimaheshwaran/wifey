package com.sm.wifey.repository;

import com.sm.wifey.model.BlogComment;
import com.sm.wifey.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogCommentRepository extends JpaRepository<BlogComment, Long> {

    @Query(value = "select id, author_name, content, created_at, show_enabled, post_id, parent_comment_id from blog_comment order by created_at desc", nativeQuery = true)
    List<Contact> getAllByLatestDate();

}
