package com.taskapproval.service;

import com.taskapproval.dto.UserDTO;
import com.taskapproval.dto.UserRegistrationDTO;
import com.taskapproval.exception.ResourceNotFoundException;
import com.taskapproval.exception.UserAlreadyExistsException;
import com.taskapproval.model.User;
import com.taskapproval.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public UserDTO registerUser(UserRegistrationDTO registrationDTO) {
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new UserAlreadyExistsException("Email already in use: " + registrationDTO.getEmail());
        }
        
        User user = new User(
            registrationDTO.getName(),
            registrationDTO.getEmail(),
            passwordEncoder.encode(registrationDTO.getPassword())
        );
        
        User savedUser = userRepository.save(user);
        return mapUserToDTO(savedUser);
    }
    
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return mapUserToDTO(user);
    }
    
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
            .map(this::mapUserToDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<UserDTO> getPotentialApprovers(Long userId) {
        return userRepository.findAllPotentialApprovers(userId).stream()
            .map(this::mapUserToDTO)
            .collect(Collectors.toList());
    }
    
    private UserDTO mapUserToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
