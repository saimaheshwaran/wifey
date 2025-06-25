package com.sm.newswave.repository;

import com.sm.newswave.model.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogCommentRepository extends JpaRepository<BlogComment, Long> {
}
