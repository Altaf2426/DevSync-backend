package com.altaf.DevSync.dto;

import com.altaf.DevSync.Model.TaskPriority;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequest {
    private String title;
    private String description;
    private String category;
    private LocalDate dueDate;
    private TaskPriority priority;
    private Long assignedTo;
}
