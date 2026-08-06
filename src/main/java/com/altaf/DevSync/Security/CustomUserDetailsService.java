package com.altaf.DevSync.Security;

import com.altaf.DevSync.Model.User;
import com.altaf.DevSync.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

            User user = userRepository.findByEmail(email)
                    .orElseThrow(()->new UsernameNotFoundException("User not found"));

            return user;
    }
}
