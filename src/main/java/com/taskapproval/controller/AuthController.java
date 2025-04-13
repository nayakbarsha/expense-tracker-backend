package com.taskapproval.controller;

import com.taskapproval.dto.AuthRequest;
import com.taskapproval.dto.AuthResponse;
import com.taskapproval.dto.UserDTO;
import com.taskapproval.dto.UserRegistrationDTO;
import com.taskapproval.service.AuthService;
import com.taskapproval.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    private final UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        UserDTO userDTO = userService.registerUser(registrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.authenticate(authRequest);
        return ResponseEntity.ok(authResponse);
    }
}
