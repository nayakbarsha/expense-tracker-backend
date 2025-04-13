package com.taskapproval.controller;

import com.taskapproval.dto.TaskCreateDTO;
import com.taskapproval.dto.TaskDTO;
import com.taskapproval.security.CustomUserDetails;
import com.taskapproval.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    
    private final TaskService taskService;
    
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(
            @Valid @RequestBody TaskCreateDTO taskCreateDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((CustomUserDetails) userDetails).getUserId();
        TaskDTO createdTask = taskService.createTask(taskCreateDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }
    
    @GetMapping("/my-tasks")
    public ResponseEntity<List<TaskDTO>> getMyTasks(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((CustomUserDetails) userDetails).getUserId();
        return ResponseEntity.ok(taskService.getTasksByCreatorId(userId));
    }
    
    @GetMapping("/pending-approvals")
    public ResponseEntity<List<TaskDTO>> getTasksAwaitingMyApproval(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((CustomUserDetails) userDetails).getUserId();
        return ResponseEntity.ok(taskService.getTasksAwaitingApproval(userId));
    }
}
