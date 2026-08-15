package com.altaf.DevSync.service;

import com.altaf.DevSync.Model.Chats;
import com.altaf.DevSync.Model.User;
import com.altaf.DevSync.Model.WorkSpace;
import com.altaf.DevSync.Model.WorkSpaceMember;
import com.altaf.DevSync.Repository.ChatsRepository;
import com.altaf.DevSync.Repository.UserRepository;
import com.altaf.DevSync.Repository.WorkSpaceMemberRepository;
import com.altaf.DevSync.Repository.WorkSpaceRepository;
import com.altaf.DevSync.dto.ChatsRequest;
import com.altaf.DevSync.dto.ChatsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatsService {
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final WorkSpaceRepository workSpaceRepository;
    private final WorkSpaceMemberRepository workSpaceMemberRepository;
    private final ChatsRepository chatsRepository;

    public ChatsResponse sendMessage(Long workSpaceId , ChatsRequest request,
                                     Authentication authentication){
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user not found"));
        WorkSpace workSpace = workSpaceRepository.findById(workSpaceId)
                .orElseThrow(() -> new RuntimeException("workspace not found"));

        WorkSpaceMember workSpaceMember = workSpaceMemberRepository.
                findByWorkSpaceIdAndUserId( workSpaceId ,user.getId())
                .orElseThrow(() -> new RuntimeException("Access denied"));

        Chats chat = new Chats();
        chat.setContent(request.getContent());
        chat.setSender(user);
        chat.setWorkSpace(workSpace);

        Chats savedChat = chatsRepository.save(chat);

        ChatsResponse response = new ChatsResponse();
        response.setId(savedChat.getId());
        response.setContent(savedChat.getContent());
        response.setSenderId(savedChat.getSender());
        response.setWorkSpaceId(savedChat.getWorkSpace());
        response.setCreatedAt(savedChat.getCreatedAt());
        messagingTemplate.convertAndSend("/topic/chat" + workSpaceId , response);
        return response;
    }
}
