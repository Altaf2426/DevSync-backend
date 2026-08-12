package com.altaf.DevSync.Controller;

import com.altaf.DevSync.dto.TaskRequest;
import com.altaf.DevSync.dto.TaskResponse;
import java.util.List;

import com.altaf.DevSync.dto.TasksUpdateRequest;
import com.altaf.DevSync.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/{workspace_id}")
    public TaskResponse createTask(@PathVariable("workspace_id") Long workspaceId,
                                   @RequestBody TaskRequest request,
                                   Authentication authentication){
        return taskService.createTask(workspaceId , request , authentication);
    }
    @GetMapping("/{workspace_id}")
    public List<TaskResponse> getAllTask(@PathVariable("workspace_id") Long workSpaceId,
                                         Authentication authentication){
        return taskService.getAllTask(workSpaceId , authentication);
    }
    @GetMapping("/{workspace_id}/{task_id}")
    public TaskResponse getTaskById(@PathVariable("workspace_id") Long workSpaceId,
                                    @PathVariable("task_id") Long taskId,
                                    Authentication authentication){
        return taskService.getTaskById(workSpaceId , taskId , authentication);
    }
    @PutMapping("/{workspaceId}/{taskId}")
    public TaskResponse updateTask(
            @PathVariable Long workspaceId,
            @PathVariable Long taskId,
            @RequestBody TasksUpdateRequest request,
            Authentication authentication) {

        return taskService.updateTask(workspaceId, taskId, request, authentication);
        }

    @DeleteMapping("/{workspaceId}/{taskId}")
    public String deleteTask(
            @PathVariable Long workspaceId,
            @PathVariable Long taskId,
            Authentication authentication) {

        return taskService.deleteTask(workspaceId, taskId, authentication);
    }
}
