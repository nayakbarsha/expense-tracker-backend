package com.taskapproval.controller;

import com.taskapproval.dto.ApprovalActionDTO;
import com.taskapproval.dto.ApprovalDTO;
import com.taskapproval.security.CustomUserDetails;
import com.taskapproval.service.ApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/approvals")
@RequiredArgsConstructor
public class ApprovalController {
    
    private final ApprovalService approvalService;
    
    @PostMapping("/tasks/{taskId}")
    public ResponseEntity<ApprovalDTO> approveOrRejectTask(
            @PathVariable Long taskId,
            @Valid @RequestBody ApprovalActionDTO approvalAction,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((CustomUserDetails) userDetails).getUserId();
        ApprovalDTO approvalDTO = approvalService.approveTask(taskId, userId, approvalAction);
        return ResponseEntity.ok(approvalDTO);
    }
    
    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<List<ApprovalDTO>> getApprovalsByTaskId(@PathVariable Long taskId) {
        return ResponseEntity.ok(approvalService.getApprovalsByTaskId(taskId));
    }
    
    @GetMapping("/my-approvals")
    public ResponseEntity<List<ApprovalDTO>> getMyApprovals(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((CustomUserDetails) userDetails).getUserId();
        return ResponseEntity.ok(approvalService.getApprovalsByApproverId(userId));
    }
}
