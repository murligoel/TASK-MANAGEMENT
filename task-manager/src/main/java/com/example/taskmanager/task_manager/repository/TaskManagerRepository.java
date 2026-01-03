package com.example.taskmanager.task_manager.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.example.taskmanager.task_manager.entity.Task;

@Repository
public class TaskManagerRepository {
    
    private final List<Task> taskList = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public void add(Task task) {
        Long id = idGenerator.incrementAndGet();
        task.setId(id);
        taskList.add(task);
    }

    public Task getTask(String id) {
        Long lId = Long.parseLong(id);
        for(Task task : taskList) {
            if(task.getId().equals(lId)) {
                return task;
            }
        }
        return null;
    }

}
