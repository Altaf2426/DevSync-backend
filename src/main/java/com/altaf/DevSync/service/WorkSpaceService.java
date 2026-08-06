package com.altaf.DevSync.service;

import com.altaf.DevSync.Model.User;
import com.altaf.DevSync.Model.WorkSpace;
import com.altaf.DevSync.Repository.UserRepository;
import com.altaf.DevSync.Repository.WorkSpaceRepository;
import com.altaf.DevSync.dto.WorkSpaceRequest;
import com.altaf.DevSync.dto.WorkSpaceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkSpaceService {
    private final WorkSpaceRepository workSpaceRepository;
    private final UserRepository userRepository;

    public WorkSpaceResponse createWorkSpace(Authentication authentication
            , WorkSpaceRequest request){
        String email= authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
        WorkSpace workSpace = new WorkSpace();
        workSpace.setName(request.getName());
        workSpace.setDescription(request.getDescription());
        workSpace.setOwnerName(user.getFullName());
        workSpace.setOwnerEmail(user.getEmail());
        workSpace.setOwnerId(user.getId());
        WorkSpace saved = workSpaceRepository.save(workSpace);

        WorkSpaceResponse response = new WorkSpaceResponse();
        response.setId(saved.getId());
        response.setOwnerId(saved.getOwnerId());
        response.setName(saved.getName());
        response.setCreatedAt(saved.getCreatedAt());
        response.setOwnerName(saved.getOwnerName());
        response.setOwnerEmail(saved.getOwnerEmail());
        response.setDescription(saved.getDescription());
        return  response;
    }
}
