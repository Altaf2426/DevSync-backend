package com.altaf.DevSync.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String category;
    private LocalDate dueDate;
    @Enumerated(EnumType.STRING )
    private TaskStatus status;
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;
    private Long workSpaceId;
    private Long assignedTo;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
