package com.sm.newswave.service;

import com.sm.newswave.model.User;
import com.sm.newswave.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user;
        Collection< User.Role > roles = new ArrayList<>();

        try {
            user = userRepository.findByUsernameOrEmail(username);
            roles.add(user.getRole());
        }
        catch (Exception ignored) {
            System.out.println("User not found: " + username);
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // Create UserDetails object
        return new org.springframework.security.core.userdetails.User(
                username,
                new BCryptPasswordEncoder().encode(user.getPassword()), // Ensure the password is encrypted
                mapRolesToAuthorities(roles)
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<User.Role> roles) {
        // Map your application's roles to Spring Security's GrantedAuthority
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

}
