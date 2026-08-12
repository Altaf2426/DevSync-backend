package com.altaf.DevSync.dto;

import com.altaf.DevSync.Model.TaskPriority;
import com.altaf.DevSync.Model.TaskStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private String category;
    private LocalDate dueDate;
    private TaskStatus status;
    private TaskPriority priority;
    private Long workSpaceId;
    private Long assignedTo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
