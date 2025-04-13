package com.taskapproval.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentCreateDTO {
    @NotBlank(message = "Comment content is required")
    private String content;

    private Long taskId;
}
