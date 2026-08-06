package com.altaf.DevSync.dto;

import lombok.Data;

@Data
public class UserSignUpRequest {
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;

}
