package com.altaf.DevSync.Controller;


import com.altaf.DevSync.dto.WorkSpaceRequest;
import com.altaf.DevSync.dto.WorkSpaceResponse;
import com.altaf.DevSync.service.WorkSpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{id}")
    public WorkSpaceResponse getWorkSpaceById( @PathVariable Long id , Authentication authentication){
        return workSpaceService.getWorkSpaceById(id , authentication);

    }
    @DeleteMapping("/{id}")
    public String deleteWorkSpaceById(@PathVariable Long id , Authentication authentication){
        return workSpaceService.deleteWorkSpaceById(id , authentication);
    }
}
