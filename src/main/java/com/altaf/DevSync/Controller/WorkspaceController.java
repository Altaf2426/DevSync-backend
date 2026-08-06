package com.altaf.DevSync.Controller;


import com.altaf.DevSync.Model.WorkSpace;
import com.altaf.DevSync.dto.WorkSpaceRequest;
import com.altaf.DevSync.dto.WorkSpaceResponse;
import com.altaf.DevSync.service.WorkSpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workspace")
@RequiredArgsConstructor
public class WorkspaceController {
    private final WorkSpaceService workSpaceService;

    @PostMapping("/create")
    public WorkSpaceResponse createWorkSpace(
            Authentication authentication , @RequestBody WorkSpaceRequest request){
        WorkSpaceResponse response = workSpaceService.createWorkSpace(authentication , request);
        return response;
    }
}
