package com.altaf.DevSync.Controller;

import com.altaf.DevSync.dto.AddWorkSpaceMemberRequest;
import com.altaf.DevSync.dto.WorkSpaceMemberResponse;
import com.altaf.DevSync.service.WorkSpaceMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
}
