package com.taskapproval.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class TaskCreateDTO {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotEmpty(message = "At least 3 approvers are required")
    @Size(min = 3, message = "At least 3 approvers are required")
    private List<Long> approverIds;
}
