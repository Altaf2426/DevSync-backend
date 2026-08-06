package com.altaf.DevSync.dto;

import com.altaf.DevSync.Model.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkSpaceMemberResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private LocalDateTime joinAt;
}
