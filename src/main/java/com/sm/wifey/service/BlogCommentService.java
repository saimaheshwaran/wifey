package com.sm.wifey.service;

import com.sm.wifey.model.BlogComment;
import com.sm.wifey.model.Contact;
import com.sm.wifey.repository.BlogCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogCommentService {

    @Autowired
    BlogCommentRepository blogCommentRepository;

    public Page<BlogComment> commentPageRequest(int page, int pageSize) {
        return blogCommentRepository.findAll(
                PageRequest.of( page, pageSize,
                        Sort.by(Sort.Direction.DESC, "createdAt")));
    }

    public List<Contact> getAll() {
        return blogCommentRepository.getAllByLatestDate();
    }
}
