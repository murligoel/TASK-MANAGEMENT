package com.example.taskmanager.task_manager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.taskmanager.task_manager.entity.Task;
import com.example.taskmanager.task_manager.service.TaskManagerService;

@ExtendWith(MockitoExtension.class)
public class TaskManagerControllerTest {
    
    @InjectMocks
    private TaskManagementController taskController;

    @Mock
    private TaskManagerService taskManagerService;
    
    // Create Tasks Unit Tests

    @Test
    void returnsBadRequestWhenTitleAndDueDateAreMissing() {
        Task task = new Task();

        ResponseEntity<?> response = taskController.createTasks(task);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Title and Due Date are required", response.getBody());
    }

    @Test
    void returnsBadRequestWhenTitleIsMissing() {
        Task task = new Task();
        task.setDueDate(LocalDate.now());

        ResponseEntity<?> response = taskController.createTasks(task);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Title is required", response.getBody());
    }

    @Test
    void returnsBadRequestWhenDueDateIsMissing() {
        Task task = new Task();
        task.setTitle("My task");

        ResponseEntity<?> response = taskController.createTasks(task);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Due Date is required", response.getBody());
    }

    @Test
    void createsTaskWhenInputIsValid() {
        Task inputTask = new Task();
        inputTask.setTitle("My task");
        inputTask.setDueDate(LocalDate.now());

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle("My task");
        savedTask.setDueDate(inputTask.getDueDate());

        when(taskManagerService.createTask(inputTask))
                .thenReturn(savedTask);

        ResponseEntity<?> response = taskController.createTasks(inputTask);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedTask, response.getBody());
    }

    @Test
    void returnsServerErrorWhenServiceFails() {
        Task task = new Task();
        task.setTitle("My task");
        task.setDueDate(LocalDate.now());

        when(taskManagerService.createTask(task))
                .thenThrow(new RuntimeException("Something went wrong"));

        ResponseEntity<?> response = taskController.createTasks(task);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // GET TASK WITH ID UNIT TESTS

    @Test
    void returnsTaskIdIsNull() {
        String id = null;
        
        ResponseEntity<?> response = taskController.getTask(id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Id is null", response.getBody());
    }

    @Test
    void returnTaskIdIsNotFound() {
        String id = "1";

        when(taskManagerService.getTaskWithId(id)).thenReturn(null);

        ResponseEntity<?> response = taskController.getTask(id);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No Task Found with id 1", response.getBody());
    }

    @Test
    void returnTaskFound() {
        Long id = 1L;

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle("My task");
        savedTask.setDueDate(LocalDate.now());

        when(taskManagerService.getTaskWithId(Long.toString(id))).thenReturn(savedTask);

        ResponseEntity<?> response = taskController.getTask(Long.toString(id));
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedTask, response.getBody());
    }

    // UPDATE TASK UNIT TEST

    @Test
    void returnUpdateTaskIdIsNull() {
        String id = null;

        ResponseEntity<?> response = taskController.updateTask(id, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Id is null", response.getBody());
    }

    @Test
    void returnUpdateTaskIsNull() {
        String id = "1";

        ResponseEntity<?> response = taskController.updateTask(id, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Task is null", response.getBody());
    }

    @Test
    void returnUpdateTaskIdNotPresent() {
        String id = "1";

        ResponseEntity<?> response = taskController.updateTask(id, null);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Task is null", response.getBody());
        
    }

    @Test
    void returnUpdateTaskSuccess() {
        String id = "1";

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Updated task");
        task.setDescription("Task Got Updated");
        task.setDueDate(LocalDate.now());
        

        when(taskManagerService.updateTaskWithId(id, task)).thenReturn(task);

        ResponseEntity<?> response = taskController.updateTask(id, task);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    // GET ALL TASKS UNIT TEST

    @Test
    void getAllTasksSuccess() {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task1");
        task1.setDueDate(LocalDate.now());

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task2");
        task2.setDueDate(LocalDate.now());

        List<Task> tasks = List.of(task1, task2);

        when(taskManagerService.getTasks()).thenReturn(tasks);

        ResponseEntity<?> response = taskController.getAllTasks(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tasks, response.getBody());
    }

    @Test
    void getAllTasksRuntimeException() {
        when(taskManagerService.getTasks())
                .thenThrow(new RuntimeException("DB error"));

        ResponseEntity<?> response = taskController.getAllTasks(null);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Runtime Error Occured"));
    }

    // DELETE TASK UNIT TEST

    @Test
    void deleteTaskIdIsNull() {
        ResponseEntity<?> response = taskController.deleteTask(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Id is null", response.getBody());
    }

    @Test
    void deleteTaskTaskNotFound() {
        String id = "1";

        when(taskManagerService.deleteTaskWithId(id)).thenReturn(null);

        ResponseEntity<?> response = taskController.deleteTask(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No Task Found with id 1", response.getBody());
    }

    @Test
    void deleteTaskSuccess() {
        String id = "1";

        when(taskManagerService.deleteTaskWithId(id)).thenReturn(1L);

        ResponseEntity<?> response = taskController.deleteTask(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Task Deleted with ID 1", response.getBody());
    }

    @Test
    void deleteTaskRuntimeException() {
        String id = "1";

        when(taskManagerService.deleteTaskWithId(id))
                .thenThrow(new RuntimeException("DB error"));

        ResponseEntity<?> response = taskController.deleteTask(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Runtime Error Occured"));
    }



}
