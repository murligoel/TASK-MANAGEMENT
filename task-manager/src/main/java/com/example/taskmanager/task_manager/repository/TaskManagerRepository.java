package com.example.taskmanager.task_manager.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.example.taskmanager.task_manager.entity.Task;

@Repository
public class TaskManagerRepository {
    
    private final HashMap<Long, Task> taskList = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public Task add(Task task) {
        Long id = idGenerator.incrementAndGet();
        task.setId(id);
        taskList.put(id, task);
        return task;
    }

    public Task get(String id) {
        Long lId = Long.parseLong(id);
        if(taskList.containsKey(lId)) {
            return taskList.get(lId);
        }
        return null;
    }

    public Task update(String id, Task task) {
        Long lId = Long.parseLong(id);
        if(taskList.containsKey(lId)) {
            Task existingTask = taskList.get(lId);
            if(!Objects.isNull(task.getTitle())) {
                existingTask.setTitle(task.getTitle());
            }
            if(!Objects.isNull(task.getDescription())) {
                existingTask.setDescription(task.getDescription());
            }
            if(!Objects.isNull(task.getStatus())) {
                existingTask.setStatus(task.getStatus());
            }
            if(!Objects.isNull(task.getDueDate())) {
                existingTask.setDueDate(task.getDueDate());
            }
            taskList.put(lId, existingTask);
            return existingTask;
        }
        return null;
    }

    public List<Task> getAll() {
        List<Task> tasks = new ArrayList<>();

        for(Long id : taskList.keySet()) {
            tasks.add(taskList.get(id));
        }

        return tasks;
    }

    public Long delete(String id) {
        Long lId = Long.parseLong(id);
        if(taskList.containsKey(lId)) {
            taskList.remove(lId);
            return lId;
        }
        return null;
    }

}
