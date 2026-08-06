package com.altaf.DevSync.Controller;


import com.altaf.DevSync.dto.AuthResponse;
import com.altaf.DevSync.dto.UserLoginRequest;
import com.altaf.DevSync.dto.UserResponse;
import com.altaf.DevSync.dto.UserSignUpRequest;
import com.altaf.DevSync.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> userSignUp( @RequestBody UserSignUpRequest request){
        AuthResponse response = authService.userSignUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody UserLoginRequest request){
        AuthResponse response = authService.loginUser(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/me")
    public UserResponse getCurrentUser(Authentication authentication){
        return authService.getCurrentUser(authentication);


    }
}
