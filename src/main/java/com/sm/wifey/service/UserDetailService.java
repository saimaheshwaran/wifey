package com.sm.wifey.service;

import com.sm.wifey.model.User;
import com.sm.wifey.repository.UserRepository;
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
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(new BCryptPasswordEncoder().encode(user.getPassword())) // Ensure the password is encrypted
                .authorities(mapRolesToAuthorities(roles))
                .build();
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<User.Role> roles) {
        // Map your application's roles to Spring Security's GrantedAuthority
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name().equalsIgnoreCase("ADMIN") ? "ROLE_ADMIN" : role.name()))
                .collect(Collectors.toList());
    }

}
