package com.example.taskmanager.task_manager.service;

import java.util.List;

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
        return taskManagerRepository.add(task);
    }

    public Task getTaskWithId(String id) {
        return taskManagerRepository.get(id);
    }

    public Task updateTaskWithId(String id, Task task) {
        return taskManagerRepository.update(id, task);
    }

    public List<Task> getTasks() {
        return taskManagerRepository.getAll();
    }

    public Long deleteTaskWithId(String id) {
        return taskManagerRepository.delete(id);
    }
}
