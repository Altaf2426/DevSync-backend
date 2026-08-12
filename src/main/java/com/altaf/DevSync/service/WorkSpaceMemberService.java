package com.altaf.DevSync.service;

import com.altaf.DevSync.Model.Role;
import com.altaf.DevSync.Model.User;
import com.altaf.DevSync.Model.WorkSpace;
import com.altaf.DevSync.Model.WorkSpaceMember;
import com.altaf.DevSync.Repository.UserRepository;
import com.altaf.DevSync.Repository.WorkSpaceMemberRepository;
import com.altaf.DevSync.Repository.WorkSpaceRepository;
import com.altaf.DevSync.dto.AddWorkSpaceMemberRequest;
import com.altaf.DevSync.dto.WorkSpaceMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkSpaceMemberService {
    private final WorkSpaceMemberRepository workSpaceMemberRepository;
    private final UserRepository userRepository;
    private final WorkSpaceRepository workSpaceRepository;

    public WorkSpaceMemberResponse addMember(Long workSpaceId, AddWorkSpaceMemberRequest request
            , Authentication authentication) {
        String authEmail = authentication.getName();

        User loggedInUser = userRepository.findByEmail(authEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        WorkSpace workSpace = workSpaceRepository.findById(workSpaceId)
                .orElseThrow(() -> new RuntimeException("Workshop is not found"));

        if(!workSpace.getOwner().getId().equals(loggedInUser.getId())){
            throw  new RuntimeException("you are not the owner of this workspace");
        }

        String email = request.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));

        WorkSpaceMember member = new WorkSpaceMember();
        member.setWorkSpace(workSpace);
        member.setUser(user);
        member.setRole(Role.EMPLOYEE);

        WorkSpaceMember savedMember = workSpaceMemberRepository.save(member);

        WorkSpaceMemberResponse response = new WorkSpaceMemberResponse();

        response.setId(savedMember.getId());
        response.setName(savedMember.getUser().getUsername());
        response.setEmail(savedMember.getUser().getEmail());
        response.setRole(savedMember.getRole());
        response.setJoinAt(savedMember.getJoinAt());

        return response;


    }

    public List<WorkSpaceMemberResponse> getAllMember
            (Long workSpaceId, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Boolean isMember = workSpaceMemberRepository
                .existsByWorkSpaceIdAndUserId(workSpaceId , user.getId());

        if(!isMember){
            throw new RuntimeException("you are not the member of this workspace");
        }
        List<WorkSpaceMember> members = workSpaceMemberRepository
                .findAllByWorkSpaceId(workSpaceId);

        return members.stream()
                .map(member ->{
                    WorkSpaceMemberResponse response = new WorkSpaceMemberResponse();
                    response.setId(member.getId());
                            response.setName(member.getUser().getUsername());
                    response.setEmail(member.getUser().getEmail());
                    response.setRole(member.getRole());
                    response.setJoinAt(member.getJoinAt());
                    return response;
                }).toList();

    }

    public String removeMember(Long workSpaceId, Long memberId, Authentication authentication) {
         String email = authentication.getName();
         User user = userRepository.findByEmail(email)
                 .orElseThrow(() -> new RuntimeException("user not found"));
         WorkSpace workSpace = workSpaceRepository.findById(workSpaceId)
                 .orElseThrow(() -> new RuntimeException("WorkSpace not found"));
         WorkSpaceMember currentMember = workSpaceMemberRepository
                 .findByWorkSpaceIdAndUserId(workSpaceId,user.getId())
                 .orElseThrow(() -> new RuntimeException("You are not a member of this workspace"));

         if(currentMember.getRole()!=Role.OWNER){
             throw new RuntimeException("You are not the owner of this workspace");
         }
         workSpaceMemberRepository.deleteById(memberId);
         return "Member deleted";
    }
}
