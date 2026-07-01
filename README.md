# Task Manager

## Overview

Task Manager is a Java console application that allows users to create, organize, update, search, filter, sort, and save tasks. The project was built across multiple versions, with each version focusing on improving a specific part of the program.

The goal of this project was to practice Java fundamentals, object-oriented programming, input validation, file handling, and clean program structure. By Version 8, the focus shifted from adding major new features to making the project easier to understand, more professional, and ready to show on GitHub.

## Features

* Add new tasks
* View all tasks
* Search tasks by name
* Filter tasks by priority
* Filter tasks by category
* Filter tasks by completion status
* Sort tasks by priority
* Sort tasks by completion status
* Mark tasks as complete
* Mark tasks as incomplete
* Rename tasks
* Delete tasks
* Set task priority
* Set task category
* Save tasks to a text file
* Load saved tasks when the program starts
* Validate user input to prevent common crashes

## Project Structure

### Main.java

Handles the main program loop, menu display, user input, input validation, and user interaction. This class controls how the user moves through the program.

### Task.java

Represents a single task. Each task has a name, completion status, priority, and category.

### TaskManager.java

Handles the main task logic. This includes adding tasks, deleting tasks, renaming tasks, completing tasks, searching, filtering, sorting, and updating task information.

### FileManager.java

Handles saving and loading tasks from a text file so that task data can persist after the program closes.

## How to Run

1. Make sure Java is installed on your computer.
2. Download or clone this repository.
3. Open the project in an IDE such as IntelliJ IDEA, Eclipse, or VS Code.
4. Run `Main.java`.
5. Use the console menu to manage your tasks.
## Demo

When the program starts, the user is shown the main task menu:

```text
=========================================
              TASK MANAGER
=========================================
Created by Olamayowa Siji Layeni

A simple console task manager.
=========================================

============================
         TASK MENU
============================
1. Add Task           2. View Tasks
3. Search Task        4. Filter Task
5. Sort Task          6. Complete Task
7. Delete Task        8. Change Task Name
9. Mark Incomplete   10. Set Priority
11. Set Category     12. Exit Program
============================
```

Example task display:

```text
1. Finish README [ ] (High) [School]
2. Review Java code [ ] (Medium) [Personal]
3. Push project to GitHub [X] (Low) [Other]
```
## Example Menu

```text
TASK MENU
1. Add Task           2. View Tasks
3. Search Task        4. Filter Task
5. Sort Task          6. Complete Task
7. Delete Task        8. Change Task Name
9. Mark Incomplete   10. Set Priority
11. Set Category     12. Exit Program
```

## Version History

### V1: Basic Console Task Manager

Created the first version of the task manager with basic task actions such as adding, viewing, completing, deleting, and exiting the program.

### V2: Input Validation and Error Handling

Improved the program so invalid input would not crash the application. Added stronger validation for menu choices and task numbers.

### V3: Code Organization

Improved the structure of the project by separating responsibilities across different classes.

### V4: File Saving and Loading

Added file handling so tasks could be saved and loaded between program runs.

### V5: Task Editing

Expanded task management by allowing users to rename tasks and update task status.

### V6: Priority and Category Organization

Added priority and category fields to tasks so users could organize their task list more effectively.

### V7: Refactoring and Navigation Improvements

Cleaned up repeated validation logic, improved helper methods, and made the program easier to navigate.

### V8: Portfolio Documentation and GitHub Professionalization

Focused on improving the README, documentation, GitHub presentation, and overall professionalism of the project.

## What I Learned

Through this project, I practiced:

* Java classes and objects
* Object-oriented programming
* ArrayLists
* Loops and conditionals
* User input with Scanner
* Input validation
* File reading and writing
* Separating code responsibilities across multiple classes
* Refactoring repeated code
* Building a project across multiple versions
* Writing documentation for a portfolio project

## Future Improvements

Possible future improvements include:

* Adding due dates for tasks
* Adding a graphical user interface
* Using a database instead of a text file
* Adding user accounts
* Improving search and sorting options
* Adding task editing for multiple fields at once

## Author

Created by Olamayowa Siji Layeni.
