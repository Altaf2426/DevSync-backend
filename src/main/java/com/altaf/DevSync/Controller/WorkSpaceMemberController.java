package com.altaf.DevSync.Controller;

import com.altaf.DevSync.dto.AddWorkSpaceMemberRequest;
import com.altaf.DevSync.dto.WorkSpaceMemberResponse;
import com.altaf.DevSync.service.WorkSpaceMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workspace")
@RequiredArgsConstructor
public class WorkSpaceMemberController {

    private final WorkSpaceMemberService workSpaceMemberService;

    @PostMapping("/{workspace_id}/member")
    public WorkSpaceMemberResponse addMember(@PathVariable("workspace_id") Long workSpace_id
            , @RequestBody AddWorkSpaceMemberRequest request , Authentication authentication){

        return workSpaceMemberService.addMember(workSpace_id , request , authentication);

    }
    @GetMapping("/{workspace_id}/member")
    public List<WorkSpaceMemberResponse> getAllMember
            (@PathVariable("workspace_id") Long workSpace_id,Authentication authentication){
        return workSpaceMemberService.getAllMember(workSpace_id , authentication);
    }

    @DeleteMapping("/{workspace_id}/member/{member_id}")
    public String removeMember(@PathVariable("workspace_id") Long workSpace_id,
                               @PathVariable("member_id") Long member_id,
                               Authentication authentication){
        return workSpaceMemberService.removeMember(workSpace_id , member_id , authentication);
    }
}
