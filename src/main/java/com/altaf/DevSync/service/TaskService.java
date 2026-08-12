package com.altaf.DevSync.service;

import com.altaf.DevSync.Model.*;
import com.altaf.DevSync.Repository.TaskRepository;
import com.altaf.DevSync.Repository.UserRepository;
import com.altaf.DevSync.Repository.WorkSpaceRepository;
import com.altaf.DevSync.dto.TaskRequest;
import com.altaf.DevSync.dto.TaskResponse;
import com.altaf.DevSync.dto.TasksUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final UserRepository userRepository;
    private final WorkSpaceRepository workSpaceRepository;
    private final TaskRepository taskRepository;
    public TaskResponse createTask(Long workspaceId,
                                   TaskRequest request,
                                   Authentication authentication) {

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found "));
        WorkSpace workSpace = workSpaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException("WorkSpace is not found"));

        Role role = user.getRole();
        Boolean isOwner = workSpace.getOwner().getId().equals(user.getId());
        Boolean admin = role== Role.ADMIN;

        if( !isOwner && !admin){
            throw new RuntimeException("Access Denied :cannot create task");
        }

        Tasks task = new Tasks();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCategory(request.getCategory());
        task.setDueDate(request.getDueDate());
        task.setWorkSpaceId(workspaceId);
        task.setStatus(TaskStatus.TODO);
        task.setPriority(request.getPriority());
        task.setAssignedTo(request.getAssignedTo());
        Tasks savedTask = taskRepository.save(task);

        TaskResponse response = mapToResponse(savedTask);
        return response;
    }

    public List<TaskResponse> getAllTask(Long workSpaceId, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found "));
        WorkSpace workSpace = workSpaceRepository.findById(workSpaceId)
                .orElseThrow(() -> new RuntimeException("WorkSpace is not found"));

        List<Tasks> tasks = taskRepository.findByWorkSpaceId(workSpaceId);

        TaskResponse response = new TaskResponse();
        List<TaskResponse> responses = new ArrayList<>();

        for (Tasks task : tasks) {
                responses.add(mapToResponse(task));
            }

        return responses;
    }

    public TaskResponse getTaskById(Long workSpaceId , Long taskId, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found "));
        WorkSpace workSpace = workSpaceRepository.findById(workSpaceId)
                .orElseThrow(() -> new RuntimeException("WorkSpace is not found"));
        Tasks tasks = taskRepository.findById(taskId).orElseThrow(
                () -> new RuntimeException("Task not found"));
        if (!tasks.getWorkSpaceId().equals(workSpaceId)) {
            throw new RuntimeException("Task does not belong to this workspace");
        }

        TaskResponse response = mapToResponse(tasks);
        return response;

    }

    public TaskResponse updateTask(Long workSpaceId, Long taskId,
                                   TasksUpdateRequest request,
                                   Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found "));
        WorkSpace workSpace = workSpaceRepository.findById(workSpaceId)
                .orElseThrow(() -> new RuntimeException("WorkSpace is not found"));
        Tasks tasks = taskRepository.findById(taskId).orElseThrow(
                () -> new RuntimeException("Task not found"));
        if (!tasks.getWorkSpaceId().equals(workSpaceId)) {
            throw new RuntimeException("Task does not belong to this workspace");
        }
        tasks.setStatus(request.getStatus());
        tasks.setDueDate(request.getDueDate());
        tasks.setPriority(request.getPriority());
        tasks.setCategory(request.getCategory());
        tasks.setTitle(request.getTitle());
        tasks.setDescription(request.getDescription());
        tasks.setAssignedTo(request.getAssignedTo());
        Tasks tasks1 = taskRepository.save(tasks);
        TaskResponse response = mapToResponse(tasks1);
        return response;

    }
    private TaskResponse mapToResponse(Tasks task) {
        TaskResponse response = new TaskResponse();

        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setCategory(task.getCategory());
        response.setDueDate(task.getDueDate());
        response.setStatus(task.getStatus());
        response.setPriority(task.getPriority());
        response.setWorkSpaceId(task.getWorkSpaceId());
        response.setAssignedTo(task.getAssignedTo());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());

        return response;
    }

    public String deleteTask(Long workSpaceId, Long taskId, Authentication authentication) {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        WorkSpace workSpace = workSpaceRepository.findById(workSpaceId)
                .orElseThrow(() -> new RuntimeException("WorkSpace is not found"));

        if (!workSpace.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("Access Denied: cannot delete task");
        }

        Tasks task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getWorkSpaceId().equals(workSpaceId)) {
            throw new RuntimeException("Task does not belong to this workspace");
        }

        taskRepository.delete(task);

        return "Task deleted successfully";
    }
    
}
