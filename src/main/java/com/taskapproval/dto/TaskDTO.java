package com.taskapproval.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private Long creatorId;
    private String creatorName;
    private Set<ApproverDTO> approvers = new HashSet<>();


}
