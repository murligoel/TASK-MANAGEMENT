package com.example.taskmanager.task_manager.repository;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.example.taskmanager.task_manager.entity.Task;

@Repository
public class TaskManagerRepository {
    
    private final HashMap<Long, Task> taskList = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public void add(Task task) {
        Long id = idGenerator.incrementAndGet();
        task.setId(id);
        taskList.put(id, task);
    }

}
