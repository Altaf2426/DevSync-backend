package com.altaf.DevSync.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkSpaceResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private String ownerName;
}
