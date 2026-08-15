package com.altaf.DevSync.Controller;

import com.altaf.DevSync.dto.ChatsPayloadRequest;
import com.altaf.DevSync.dto.ChatsRequest;
import com.altaf.DevSync.service.ChatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatsController {
    private final ChatsService chatsService;

    @MessageMapping("/chat")
    public void sendMessage(ChatsPayloadRequest payload,
                            Authentication authentication){

        ChatsRequest request = new ChatsRequest();
         request.setContent(payload.getContent());
         chatsService.sendMessage(payload.getWorkSpaceId() , request , authentication);
    }

}
