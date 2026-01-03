# TASK-MANAGEMENT
Building task management API


# CREATE TASK
POST http://localhost:8080/tasks
Content-Type: application/json

{
  "title": "Finish assignment",
  "description": "Complete the task manager",
  "dueDate": "2026-01-10"
}

# GET TASK WITH ID


# UPDATE TASK WITH ID
PUT http://localhost:8080/tasks/1
Content-Type: application/json

{
  "title": "Edit Finish assignment 1"
}

# GET ALL TASKS
GET http://localhost:8080/tasks