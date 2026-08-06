package com.altaf.DevSync.service;

import com.altaf.DevSync.Model.Role;
import com.altaf.DevSync.Model.User;
import com.altaf.DevSync.Repository.UserRepository;
import com.altaf.DevSync.Security.JwtService;
import com.altaf.DevSync.dto.AuthResponse;
import com.altaf.DevSync.dto.UserLoginRequest;
import com.altaf.DevSync.dto.UserResponse;
import com.altaf.DevSync.dto.UserSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public AuthResponse userSignUp(UserSignUpRequest request){
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);
        AuthResponse response = new AuthResponse();

        String token = jwtService.generateToken(savedUser);
        response.setToken(token);
        response.setMessage("Signup Successful");
        return response;
    }

    public AuthResponse loginUser(UserLoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (request.getEmail(),request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new UsernameNotFoundException("User not found"));

        String token = jwtService.generateToken(user);

        return new AuthResponse( token , "login successful");

    }

    public UserResponse getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow
                (()-> new RuntimeException("User not found"));
        UserResponse response = new UserResponse();
         response.setId(user.getId());
         response.setFullName(user.getFullName());
         response.setEmail(user.getEmail());
         response.setRole(user.getRole());
         response.setPhoneNumber(user.getPhoneNumber());
         response.setCreatedAt(user.getCreatedAt());
         return response;
    }
}
