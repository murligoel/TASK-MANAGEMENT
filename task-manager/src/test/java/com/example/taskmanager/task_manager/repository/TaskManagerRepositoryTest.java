package com.example.taskmanager.task_manager.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.taskmanager.task_manager.entity.Task;

class TaskManagerRepositoryTest {

    private TaskManagerRepository repository;

    @BeforeEach
    void setUp() {
        repository = new TaskManagerRepository();
    }

    @Test
    void addTaskAssignsIdAndStoresTask() {
        Task task = new Task();
        task.setTitle("Test Task");

        Task saved = repository.add(task);

        assertNotNull(saved.getId());
        assertEquals("Test Task", saved.getTitle());
    }

    @Test
    void getTaskExistingIdReturnsTask() {
        Task task = new Task();
        task.setTitle("Task A");

        Task saved = repository.add(task);

        Task result = repository.get(saved.getId().toString());

        assertNotNull(result);
        assertEquals(saved.getId(), result.getId());
    }

    @Test
    void getTaskNonExistingIdReturnsNull() {
        Task result = repository.get("99");

        assertNull(result);
    }

    @Test
    void updateTaskUpdatesOnlyProvidedFields() {
        Task task = new Task();
        task.setTitle("Old Title");
        task.setDescription("Old Desc");

        Task saved = repository.add(task);

        Task update = new Task();
        update.setTitle("New Title");

        Task result = repository.update(saved.getId().toString(), update);

        assertEquals("New Title", result.getTitle());
        assertEquals("Old Desc", result.getDescription());
    }

    @Test
    void updateTaskNonExistingIdReturnsNull() {
        Task update = new Task();
        update.setTitle("Title");

        Task result = repository.update("100", update);

        assertNull(result);
    }

    @Test
    void getAllReturnsAllStoredTasks() {
        Task task1 = new Task();
        task1.setDueDate(LocalDate.now().plusDays(1));

        Task task2 = new Task();
        task2.setDueDate(LocalDate.now().plusDays(1));

        repository.add(task1);
        repository.add(task2);

        List<Task> tasks = repository.getAll();

        assertEquals(2, tasks.size());
    }

    @Test
    void deleteTaskExistingIdRemovesAndReturnsId() {
        Task task = new Task();
        Task saved = repository.add(task);

        Long result = repository.delete(saved.getId().toString());

        assertEquals(saved.getId(), result);
        assertNull(repository.get(saved.getId().toString()));
    }

    @Test
    void deleteTaskNonExistingIdReturnsNull() {
        Long result = repository.delete("200");

        assertNull(result);
    }
}

