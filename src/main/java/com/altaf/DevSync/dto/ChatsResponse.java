package com.altaf.DevSync.dto;

import com.altaf.DevSync.Model.User;
import com.altaf.DevSync.Model.WorkSpace;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatsResponse {
    private Long id;
    private String content;
    private User senderId;
    private WorkSpace workSpaceId;
    private LocalDateTime createdAt;
}
