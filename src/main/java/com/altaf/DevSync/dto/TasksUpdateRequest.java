package com.altaf.DevSync.dto;

import com.altaf.DevSync.Model.TaskPriority;
import com.altaf.DevSync.Model.TaskStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TasksUpdateRequest {
        private String title;
        private String description;
        private String category;
        private LocalDate dueDate;
        private TaskPriority priority;
        private TaskStatus status;
        private Long assignedTo;
    }

