package org.example.storemanagementbestpractice.services;

import lombok.extern.slf4j.Slf4j;
import org.example.storemanagementbestpractice.repository.UserDetailsRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    public CustomUserDetailsService(
            UserDetailsRepository userDetailsRepository
    ) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading UserDetails for {}", username);
        return userDetailsRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found")
                );
    }
}