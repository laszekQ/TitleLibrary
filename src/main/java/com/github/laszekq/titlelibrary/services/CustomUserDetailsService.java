package com.github.laszekq.titlelibrary.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userService.getUserByLogin(login)
                .map(dto -> User.builder()
                        .username(dto.getLogin())
                        .password(dto.getPassword())
                        .roles(dto.getRole().getName())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User " + login + " not found"));
    }
}
