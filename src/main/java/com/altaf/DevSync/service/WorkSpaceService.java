package com.altaf.DevSync.service;

import com.altaf.DevSync.Model.Role;
import com.altaf.DevSync.Model.User;
import com.altaf.DevSync.Model.WorkSpace;
import com.altaf.DevSync.Model.WorkSpaceMember;
import com.altaf.DevSync.Repository.UserRepository;
import com.altaf.DevSync.Repository.WorkSpaceMemberRepository;
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
    private final WorkSpaceMemberRepository workSpaceMemberRepository;
    private final UserRepository userRepository;

    public WorkSpaceResponse createWorkSpace(Authentication authentication
            , WorkSpaceRequest request){
        String email= authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
        WorkSpace workSpace = new WorkSpace();
        workSpace.setName(request.getName());
        workSpace.setDescription(request.getDescription());
        workSpace.setOwner(user);
        WorkSpace saved = workSpaceRepository.save(workSpace);

        WorkSpaceMember ownerMember = new WorkSpaceMember();
        ownerMember.setWorkSpace(saved);
        ownerMember.setUser(user);
        ownerMember.setRole(Role.OWNER);
        workSpaceMemberRepository.save(ownerMember);

        WorkSpaceResponse response = new WorkSpaceResponse();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setCreatedAt(saved.getCreatedAt());
        response.setDescription(saved.getDescription());
        response.setOwnerName(user.getFullName());
        return  response;
    }

    public WorkSpaceResponse getWorkSpaceById(Long id, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
        WorkSpace workSpace = workSpaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkSpace not found"));

        if(!workSpace.getOwner().getId().equals(user.getId())){
            throw new RuntimeException("Access denied");
        }

        WorkSpaceResponse response = new WorkSpaceResponse();
        response.setId(workSpace.getId());
        response.setName(workSpace.getName());
        response.setDescription(workSpace.getDescription());
        response.setCreatedAt(workSpace.getCreatedAt());
        response.setOwnerName(user.getFullName());

        return  response;
    }

    public String deleteWorkSpaceById(Long id, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
        WorkSpace workSpace = workSpaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkSpace not found"));
        if(!workSpace.getOwner().getId().equals(user.getId())){
            throw new RuntimeException("Access denied");
        }
        workSpaceRepository.deleteById(id);
        return "WorkSpace is deleted successfully";

    }
}
