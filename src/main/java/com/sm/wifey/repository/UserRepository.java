package com.sm.wifey.repository;

import com.sm.wifey.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User findByUsername(String username);

    User findByEmail(String email);

    @Query(value = "select * from users where username=?1 or email=?1 order by created_at desc", nativeQuery = true)
    User findByUsernameOrEmail(String usernameOremail);
}
