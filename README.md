# TASK-MANAGEMENT
Building task management API

# STEPS TO COMPILE AND RUN APPLICATION USING Gihub Codespace 

1. Visit the GitHub repository:  
   [https://github.com/murligoel/TASK-MANAGEMENT/tree/development](https://github.com/murligoel/TASK-MANAGEMENT/tree/development)
2. Click **Code → Codespaces → Create Codespace** on the **development branch**.
3. A browser-based VS Code window will open with your project.
4. Install **Extension Pack For Java** if prompted.
5. Open `TaskManagerApplication.java`.
6. Click **Run Java** in the top-right corner.
7. If prompted, allow permission to run Java in standard mode.
8. The project will import dependencies automatically.
9. Once dependencies are imported, the application will start running on **PORT 8080**.


# STEPS TO TEST API's
We recommend using the **REST Client extension** by Huachao Mao in VS Code:

1. Install **REST Client** extension.
2. Create or open `test.http` file in the project.
3. Copy the commands below and click **Send Request** for each.


# CREATE TASK
POST http://localhost:8080/tasks
Content-Type: application/json

{
  "title": "Finish assignment",
  "description": "Complete the task manager",
  "dueDate": "2026-01-10"
}

# GET TASK WITH ID
GET http://localhost:8080/tasks/1

# UPDATE TASK WITH ID
PUT http://localhost:8080/tasks/1
Content-Type: application/json

{
  "title": "Edit Finish assignment 1"
}

# GET ALL TASKS
GET http://localhost:8080/tasks

# DELETE TASK
DELETE http://localhost:8080/tasks/1