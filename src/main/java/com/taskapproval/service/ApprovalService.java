package com.taskapproval.service;

import com.taskapproval.dto.ApprovalActionDTO;
import com.taskapproval.dto.ApprovalDTO;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApprovalService {
    
    private final ApprovalRepository approvalRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    
    @Transactional
    public ApprovalDTO approveTask(Long taskId, Long approverId, ApprovalActionDTO approvalAction) {
        Approval approval = approvalRepository.findByTaskIdAndApproverId(taskId, approverId)
            .orElseThrow(() -> new ResourceNotFoundException("Approval request not found"));
        
        Task task = approval.getTask();
        User approver = approval.getApprover();
        
        if (approvalAction.isApproved()) {
            approval.approve(approvalAction.getComment());
        } else {
            approval.reject(approvalAction.getComment());
        }
        
        approvalRepository.save(approval);
        
        // Notify task creator of the approval action
        emailService.sendApprovalActionNotification(
            task.getCreator().getEmail(),
            approver.getName(),
            task.getTitle(),
            approvalAction.isApproved(),
            task.getId()
        );
        
        // If task is fully approved, notify all parties
        if (task.getStatus().toString().equals("APPROVED")) {
            for (Approval taskApproval : task.getApprovals()) {
                emailService.sendTaskCompletedNotification(
                    taskApproval.getApprover().getEmail(),
                    task.getTitle(),
                    task.getId()
                );
            }
            
            // Also notify the task creator
            emailService.sendTaskCompletedNotification(
                task.getCreator().getEmail(),
                task.getTitle(),
                task.getId()
            );
        }
        
        return mapApprovalToDTO(approval);
    }
    
    @Transactional(readOnly = true)
    public List<ApprovalDTO> getApprovalsByTaskId(Long taskId) {
        return approvalRepository.findByTaskId(taskId).stream()
            .map(this::mapApprovalToDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ApprovalDTO> getApprovalsByApproverId(Long approverId) {
        return approvalRepository.findByApproverId(approverId).stream()
            .map(this::mapApprovalToDTO)
            .collect(Collectors.toList());
    }
    
    private ApprovalDTO mapApprovalToDTO(Approval approval) {
        ApprovalDTO dto = new ApprovalDTO();
        dto.setId(approval.getId());
        dto.setTaskId(approval.getTask().getId());
        dto.setTaskTitle(approval.getTask().getTitle());
        dto.setApproverId(approval.getApprover().getId());
        dto.setApproverName(approval.getApprover().getName());
        dto.setStatus(approval.getStatus().toString());
        dto.setCreatedAt(approval.getCreatedAt());
        dto.setRespondedAt(approval.getRespondedAt());
        dto.setComment(approval.getComment());
        return dto;
    }
}
