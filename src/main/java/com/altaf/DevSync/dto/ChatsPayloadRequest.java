package com.altaf.DevSync.dto;

import lombok.Data;

@Data
public class ChatsPayloadRequest {
    private Long workSpaceId;
    private String content;
}
