package com.example.taskmanager.task_manager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taskmanager.task_manager.entity.Task;
import com.example.taskmanager.task_manager.repository.TaskManagerRepository;
import com.example.taskmanager.task_manager.util.TaskStatus;

@Service
public class TaskManagerService {

    
    @Autowired
    private TaskManagerRepository taskManagerRepository;

    public Task createTask(Task task) {
        if(task.getStatus() == null) {
            task.setStatus(TaskStatus.IN_PROGRESS);
        }
        validateDueDate(task.getDueDate());
        return taskManagerRepository.add(task);
    }

    private void validateDueDate(LocalDate dueDate) {
        if (!dueDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Due date must be in the future");
        }
    }

    public Task getTaskWithId(String id) {
        return taskManagerRepository.get(id);
    }

    public Task updateTaskWithId(String id, Task task) {
        if(!Objects.isNull(task.getDueDate())) {
            validateDueDate(task.getDueDate());
        }
        return taskManagerRepository.update(id, task);
    }

    public List<Task> getTasks() {
        return taskManagerRepository.getAll();
    }

    public Long deleteTaskWithId(String id) {
        return taskManagerRepository.delete(id);
    }
}
