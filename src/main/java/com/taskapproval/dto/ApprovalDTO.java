package com.taskapproval.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApprovalDTO {
    private Long id;
    private Long taskId;
    private String taskTitle;
    private Long approverId;
    private String approverName;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime respondedAt;
    private String comment;
}
