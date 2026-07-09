# Task Manager

## Overview

Task Manager is a Java console application that allows users to create, organize, update, search, filter, sort, and save tasks. The project was built across multiple versions, with each version focusing on improving a specific part of the program.

The goal of this project was to practice Java fundamentals, object-oriented programming, input validation, file handling, and clean program structure. By Version 8, the focus shifted from adding major new features to making the project easier to understand, more professional, and ready to show on GitHub.

## Features

- Add new tasks
- View all tasks
- Search tasks by name (case-insensitive)
- Filter tasks by priority
- Filter tasks by category
- Filter tasks by completion status
- Sort tasks by priority
- Sort tasks by completion status
- Mark tasks as complete
- Mark tasks as incomplete
- Rename tasks
- Change task priority
- Change task category
- Save tasks to a text file
- Automatically load saved tasks on startup
- Input validation to prevent invalid user input
- Robust file loading with corrupted task recovery
- Warning messages for invalid task data
- Enum-based priorities and categories
- JUnit unit tests

## Reliability Improvements

Version 10 introduced stronger error handling throughout the application.

- Invalid task lines are skipped instead of crashing the program.
- Blank lines in the save file are ignored.
- File resources are automatically closed using try-with-resources.
- Serious file loading and saving errors are handled by the main application.
- Invalid priorities, categories, task names, and completion values are detected during loading.

## Project Structure

### Main.java

Handles the main program loop, menu display, user input, input validation, and user interaction. This class controls how the user moves through the program.

### Task.java

Represents a single task. Each task has a name, completion status, priority, and category.

### FileManager.java

Handles saving and loading tasks from a text file. It validates loaded task data, skips corrupted task entries without stopping the program, and reports serious file I/O errors back to the main application.

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
## Repository Structure

```text
TaskManager/
├── src/
│   ├── Main.java
│   ├── Task.java
│   ├── TaskManager.java
│   ├── FileManager.java
│   ├── Priority.java
│   └── Category.java
├── test/
│   ├── TaskTest.java
│   ├── TaskManagerTest.java
│   └── FileManagerTest.java
├── README.md
├── .gitignore
└── task.txt
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

### V9: Stronger Data Modeling with Enums

Replaced String-based priority and category values with Java enums to improve type safety, readability, and maintainability. This version introduced stronger data modeling while keeping the application's features the same.

### V10: Exception Handling and Unit Testing

Improved the reliability of the application by adding structured exception handling and comprehensive JUnit tests. Corrupted task data is now safely skipped during loading, file resources are managed using try-with-resources, and serious file I/O errors are handled by the main application.

## What I Learned

Through this project, I practiced:

- Object-oriented programming
- Designing classes with clear responsibilities
- Java enums
- Collections using ArrayList
- Input validation
- File reading and writing
- Exception handling
- try-with-resources
- Custom parsing and validation
- Unit testing with JUnit 5
- Refactoring large methods into helper methods
- Git and GitHub workflow
- Building software incrementally through versioned development

## Skills Practiced

- Java
- Object-Oriented Programming (OOP)
- Enums
- Exception Handling
- File I/O
- ArrayLists
- JUnit 5
- Input Validation
- Refactoring
- Separation of Responsibilities
- Console Application Development
- Git
- GitHub

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
