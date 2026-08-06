package com.altaf.DevSync.dto;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String email;
    private String password;
}
