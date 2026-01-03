package com.example.taskmanager.task_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taskmanager.task_manager.entity.Task;
import com.example.taskmanager.task_manager.repository.TaskManagerRepository;
import com.example.taskmanager.task_manager.util.TaskStatus;

@Service
public class TaskManagerService {

    
    @Autowired
    private TaskManagerRepository taskManagerRepository;

    public void createTask(Task task) {
        if(task.getStatus() == null) {
            task.setStatus(TaskStatus.IN_PROGRESS);
        }
        taskManagerRepository.add(task);
    }

    public Task getTaskWithId(String id) {
        return taskManagerRepository.getTask(id);
    }
}
