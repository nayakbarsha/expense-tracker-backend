package com.taskapproval.service;

import com.taskapproval.dto.ApproverDTO;
import com.taskapproval.dto.TaskCreateDTO;
import com.taskapproval.dto.TaskDTO;
import com.taskapproval.exception.ResourceNotFoundException;
import com.taskapproval.model.Approval;
import com.taskapproval.model.Task;
import com.taskapproval.model.User;
import com.taskapproval.repository.ApprovalRepository;
import com.taskapproval.repository.TaskRepository;
import com.taskapproval.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ApprovalRepository approvalRepository;
    private final EmailService emailService;
    
    @Transactional
    public TaskDTO createTask(TaskCreateDTO taskCreateDTO, Long creatorId) {
        User creator = userRepository.findById(creatorId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + creatorId));
        
        Task task = new Task(
            taskCreateDTO.getTitle(),
            taskCreateDTO.getDescription(),
            creator
        );
        
        Task savedTask = taskRepository.save(task);
        
        // Add approvers
        for (Long approverId : taskCreateDTO.getApproverIds()) {
            User approver = userRepository.findById(approverId)
                .orElseThrow(() -> new ResourceNotFoundException("Approver not found with id: " + approverId));
            
            Approval approval = new Approval(savedTask, approver);
            approvalRepository.save(approval);
            
            // Send email notification to approver
            emailService.sendApprovalRequestEmail(
                approver.getEmail(),
                creator.getName(),
                savedTask.getTitle(),
                savedTask.getId()
            );
        }
        
        return mapTaskToDTO(savedTask);
    }
    
    @Transactional(readOnly = true)
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        return mapTaskToDTO(task);
    }
    
    @Transactional(readOnly = true)
    public List<TaskDTO> getTasksByCreatorId(Long creatorId) {
        return taskRepository.findByCreatorId(creatorId).stream()
            .map(this::mapTaskToDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<TaskDTO> getTasksAwaitingApproval(Long approverId) {
        return taskRepository.findTasksAwaitingApproval(approverId).stream()
            .map(this::mapTaskToDTO)
            .collect(Collectors.toList());
    }
    
    private TaskDTO mapTaskToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus().toString());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setCompletedAt(task.getCompletedAt());
        dto.setCreatorId(task.getCreator().getId());
        dto.setCreatorName(task.getCreator().getName());
        
        Set<ApproverDTO> approvers = task.getApprovals().stream()
            .map(approval -> {
                ApproverDTO approverDTO = new ApproverDTO();
                approverDTO.setId(approval.getApprover().getId());
                approverDTO.setName(approval.getApprover().getName());
                approverDTO.setStatus(approval.getStatus().toString());
                approverDTO.setRespondedAt(approval.getRespondedAt());
                return approverDTO;
            })
            .collect(Collectors.toSet());
        
        dto.setApprovers(approvers);
        return dto;
    }
}
