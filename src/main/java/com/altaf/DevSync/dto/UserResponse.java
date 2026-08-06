package com.altaf.DevSync.dto;

import com.altaf.DevSync.Model.Role;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDateTime;
@JsonPropertyOrder({
        "id",
        "fullName",
        "email",
        "phoneNumber",
        "role",
        "createdAt"

})

@Data
public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Role role;
    private LocalDateTime createdAt;
}
