package com.taskapproval.controller;

import com.taskapproval.dto.UserDTO;
import com.taskapproval.security.CustomUserDetails;
import com.taskapproval.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        // Assuming the UserDetails implementation has a method to get the user ID
        Long userId = ((CustomUserDetails) userDetails).getUserId();
        return ResponseEntity.ok(userService.getUserById(userId));
    }
    
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
    @GetMapping("/potential-approvers")
    public ResponseEntity<List<UserDTO>> getPotentialApprovers(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((CustomUserDetails) userDetails).getUserId();
        return ResponseEntity.ok(userService.getPotentialApprovers(userId));
    }
}
