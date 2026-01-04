package com.example.taskmanager.task_manager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.taskmanager.task_manager.entity.Task;
import com.example.taskmanager.task_manager.repository.TaskManagerRepository;
import com.example.taskmanager.task_manager.util.TaskStatus;

@ExtendWith(MockitoExtension.class)
class TaskManagerServiceTest {

    @InjectMocks
    private TaskManagerService taskManagerService;

    @Mock
    private TaskManagerRepository taskManagerRepository;

    @Test
    void createTaskSetsDefaultStatusWhenNull() {
        Task task = new Task();
        task.setStatus(null);

        Task savedTask = new Task();
        savedTask.setStatus(TaskStatus.IN_PROGRESS);

        when(taskManagerRepository.add(task)).thenReturn(savedTask);

        Task result = taskManagerService.createTask(task);

        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());
    }

    @Test
    void createTaskKeepsExistingStatus() {
        Task task = new Task();
        task.setStatus(TaskStatus.DONE);

        when(taskManagerRepository.add(task)).thenReturn(task);

        Task result = taskManagerService.createTask(task);

        assertEquals(TaskStatus.DONE, result.getStatus());
    }

    @Test
    void getTaskWithIdSuccess() {
        String id = "1";
        Task task = new Task();

        when(taskManagerRepository.get(id)).thenReturn(task);

        Task result = taskManagerService.getTaskWithId(id);

        assertEquals(task, result);
    }

    @Test
    void getTaskWithIdNotFound() {
        String id = "1";

        when(taskManagerRepository.get(id)).thenReturn(null);

        Task result = taskManagerService.getTaskWithId(id);

        assertNull(result);
    }

    @Test
    void updateTaskWithIdSuccess() {
        String id = "1";
        Task task = new Task();

        when(taskManagerRepository.update(id, task)).thenReturn(task);

        Task result = taskManagerService.updateTaskWithId(id, task);

        assertEquals(task, result);
    }

    @Test
    void updateTaskWithIdNotFound() {
        String id = "1";
        Task task = new Task();

        when(taskManagerRepository.update(id, task)).thenReturn(null);

        Task result = taskManagerService.updateTaskWithId(id, task);

        assertNull(result);
    }

    @Test
    void getTaskSuccess() {
        List<Task> tasks = List.of(new Task(), new Task());

        when(taskManagerRepository.getAll()).thenReturn(tasks);

        List<Task> result = taskManagerService.getTasks();

        assertEquals(tasks, result);
    }

    @Test
    void deleteTaskWithIdSuccess() {
        String id = "1";

        when(taskManagerRepository.delete(id)).thenReturn(1L);

        Long result = taskManagerService.deleteTaskWithId(id);

        assertEquals(1L, result);
    }

    @Test
    void deleteTaskWithIdNotFound() {
        String id = "1";

        when(taskManagerRepository.delete(id)).thenReturn(null);

        Long result = taskManagerService.deleteTaskWithId(id);

        assertNull(result);
    }
}
