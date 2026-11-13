package com.taskmanager.controller;

import com.taskmanager.dto.*;
import com.taskmanager.entity.Task;
import com.taskmanager.service.TaskService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @RequestBody TaskCreateRequest request) {
       Task task = taskService.createTask(
               request.name(),
               request.description());


        return ResponseEntity.status(HttpStatus.CREATED).body(toTaskResponse(task));
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestBody TaskStatusUpdateRequest request
    ){
        Task task = taskService.updateStatusTask(
                taskId,
                request.taskStatus());

        return ResponseEntity.ok(toTaskResponse(task));
    }

    @PatchMapping("/{taskId}/assignee")
    public ResponseEntity<TaskResponse> assignTask(
            @PathVariable Long taskId,
            @RequestBody TaskAssignRequest request
    ) {
        Task task = taskService.assignTask(taskId, request.assigneeId());



        return ResponseEntity.ok(toTaskResponse(task));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTask(
            @PathVariable Long taskId) {
        Task task = taskService.getTaskById(taskId);
        return ResponseEntity.ok(toTaskResponse(task));
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponse>> getAllTasks(
            @PageableDefault(size = 20)
            Pageable pageable
    ) {
        Page<TaskResponse> response = taskService.getAllTasks(pageable)
                .map(this::toTaskResponse);

        return ResponseEntity.ok(response);
    }



    private TaskResponse toTaskResponse(Task task) {
        UserResponse assignee = null;

        if (task.getAssignee() != null) {
            assignee = new UserResponse(
                    task.getAssignee().getId(),
                    task.getAssignee().getName(),
                    task.getAssignee().getEmail()
            );
        }

        return new TaskResponse(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getStatus(),
                assignee
        );
    }

}
