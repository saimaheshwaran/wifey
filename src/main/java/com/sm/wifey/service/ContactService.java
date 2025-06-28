package com.sm.wifey.service;

import com.sm.wifey.model.Contact;
import com.sm.wifey.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    public Page<Contact> contactPageRequest(int page, int pageSize) {
        return contactRepository.findAll(
                PageRequest.of( page, pageSize,
                        Sort.by(Sort.Direction.DESC, "createdAt")));
    }

    public List<Contact> getAll() {
        return contactRepository.getAllByLatestDate();
    }
}
