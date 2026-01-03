package com.example.taskmanager.task_manager.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            Task createdTask = taskManagerService.createTask(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Runtime Error Occured "+ e.getStackTrace());
        }
    }

    @GetMapping("tasks/{id}")
    public ResponseEntity<?> getTask(@PathVariable("id") String id) {
        if(Objects.isNull(id)){
            return ResponseEntity.badRequest().body("Id is null");
        }
        try {
            Task task = taskManagerService.getTaskWithId(id);
            if(Objects.isNull(task)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Task Found with id " + id);
            }
            return ResponseEntity.status(HttpStatus.OK).body(task);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Runtime Error Occured "+ e.getStackTrace());
        }
    }

    @PutMapping("tasks/{id}")
    public ResponseEntity<?> updateTask(@PathVariable("id") String id, @RequestBody Task task) {
        if(Objects.isNull(id)){
            return ResponseEntity.badRequest().body("Id is null");
        }
        try {
            Task updatedTask = taskManagerService.updateTaskWithId(id, task);
            if(Objects.isNull(updatedTask)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Task Found with id " + id);
            }
            return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Runtime Error Occured "+ e.getStackTrace());
        }
    }

    @GetMapping("tasks")
    public ResponseEntity<?> getAllTasks() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(taskManagerService.getTasks());
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Runtime Error Occured "+ e.getStackTrace());
        }
    }

    @DeleteMapping("tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") String id) {
        if(Objects.isNull(id)){
            return ResponseEntity.badRequest().body("Id is null");
        }
        try {
            Long taskId = taskManagerService.deleteTaskWithId(id);
            if(Objects.isNull(taskId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Task Found with id " + id);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Task Deleted with ID " + taskId);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Runtime Error Occured "+ e.getStackTrace());
        }
    }
 
}
