package com.altaf.DevSync.service;

import com.altaf.DevSync.Repository.WorkSpaceMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkSpaceMemberService {
    private final WorkSpaceMemberRepository workSpaceMemberRepository;

//    public WorkSpaceMemberResponse addMember(Long workSpaceId, AddWorkSpaceMemberRequest request, Authentication authentication) {
//    }
}
