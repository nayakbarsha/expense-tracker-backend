package com.taskapproval.service;

import com.taskapproval.dto.AuthRequest;
import com.taskapproval.dto.AuthResponse;
import com.taskapproval.exception.AuthenticationException;
import com.taskapproval.model.User;
import com.taskapproval.repository.UserRepository;
import com.taskapproval.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    
    public AuthResponse authenticate(AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(),
                    authRequest.getPassword()
                )
            );
            
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtTokenProvider.generateToken(userDetails.getUsername());
            
            User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            return new AuthResponse(token, user.getId(), user.getName(), user.getEmail());
        } catch (Exception e) {
            throw new AuthenticationException("Invalid email/password combination");
        }
    }
}
