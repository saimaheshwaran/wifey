package com.sm.wifey.repository;

import com.sm.wifey.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query(value = "select id, name, subject, message, telephone, email, created_at from contact order by created_at desc", nativeQuery = true)
    List<Contact> getAllByLatestDate();

}
