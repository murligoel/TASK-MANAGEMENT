package com.example.taskmanager.task_manager.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.task_manager.entity.Task;
import com.example.taskmanager.task_manager.service.TaskManagerService;

@RestController
public class TaskManagementController {

    @Autowired
    private TaskManagerService taskManagerService;

    @PostMapping("/tasks")
    public ResponseEntity<?> createTasks(@RequestBody Task task) {
        if(Objects.isNull(task.getTitle()) && Objects.isNull(task.getDueDate())) {
             return ResponseEntity.badRequest().body("Title and Due Date are required");
        }
        if(Objects.isNull(task.getTitle())) {
            return ResponseEntity.badRequest().body("Title is required");
        }
        if(Objects.isNull(task.getDueDate())) {
            return ResponseEntity.badRequest().body("Due Date is required");
        }
        try {
            taskManagerService.createTask(task);
            return ResponseEntity.status(HttpStatus.CREATED).body("Task Created Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Runtime Error Occured "+ e.getStackTrace());
        }
        
    }
}
