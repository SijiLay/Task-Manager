import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private boolean programRunning = false;
    private final TaskManager manager;

    public Main(TaskManager manager) {
        this.manager = manager;
    }

    public static void main(String[] args) {
        FileManager fileManager = new FileManager("task.txt");
        ArrayList<Task> loadedTasks = fileManager.loadTasks();
        TaskManager manager = new TaskManager(loadedTasks);
        Main app = new Main(manager);
        app.run();
        fileManager.saveTasks(manager.getTasks());
    }

    // =========================================================
    // Main Loop
    // =========================================================

    public void run() {
        System.out.println("""
        =========================================
                      TASK MANAGER
        =========================================
        Created by Olamayowa Siji Layeni
 
        A simple console task manager.
        =========================================
        """);
        programRunning = true;
        while (programRunning) {
            showMainMenu();
            int choice = getMainMenuChoice();
            handleChoice(choice);
            System.out.println();
        }
    }

    public void showMainMenu() {
        System.out.println("""
        ============================
                 TASK MENU          
        ============================
        1. Add Task           2. View Tasks
        3. Search Task        4. Filter Task
        5. Sort Task          6. Complete Task
        7. Delete Task        8. Change Task Name
        9. Mark Incomplete   10. Set Priority
        11. Set Category     12. Exit Program
        ============================""");
    }

    public void handleChoice(int choice) {
        switch (choice) {
            case 1:
                handleAddTask();
                break;
            case 2:
                handleViewTask();
                break;
            case 3:
                handleSearchTasks();
                break;
            case 4:
                handleFilterTasks();
                break;
            case 5:
                handleSortTasks();
                break;
            case 6:
                handleCompleteTask();
                break;
            case 7:
                handleDeleteTask();
                break;
            case 8:
                handleRenameTask();
                break;
            case 9:
                handleMarkIncomplete();
                break;
            case 10:
                handleSetPriority();
                break;
            case 11:
                handleSetCategory();
                break;
            case 12:
                handleExit();
                break;
            default:
                displayError("Invalid choice option.");
                break;
        }
    }

    // =========================================================
    // Handlers
    // =========================================================

    public void handleAddTask() {
        String taskName = getTaskName();
        manager.addTask(taskName);
        displaySuccess("Task added.");
    }

    public void handleViewTask() {
        displayHeader("Current Tasks");
        displayTasks(manager.getTasks());
    }

    public void handleSearchTasks() {
        if (!ensureTasksExist()) {
            return;
        }
        String searchTerm = getSearchTerm();
        if (searchTerm.equals("0")) {
            return;
        }
        ArrayList<Task> results = manager.searchTasks(searchTerm);
        displayHeader("Search Results");
        displayTasks(results);
    }

    public void handleFilterTasks() {
        if (!ensureTasksExist()) {
            return;
        }
        int filterChoice = getFilterChoice();
        if (filterChoice == 0) {
            return;
        } else if (filterChoice == 1) {
            int priorityChoice = getPriorityChoice();
            Priority priority = getPriorityFromChoice(priorityChoice);
            ArrayList<Task> results = manager.filterByPriority(priority);

            displayHeader("Filtered Tasks");
            displayTasks(results);

        } else if (filterChoice == 2) {
            int categoryChoice = getCategoryChoice();
            Category category = getCategoryFromChoice(categoryChoice);
            ArrayList<Task> results = manager.filterByCategory(category);
            displayHeader("Filtered Tasks");
            displayTasks(results);

        } else if (filterChoice == 3) {
            int completionChoice = getCompletionChoice();
            boolean completion = getCompletionFromChoice(completionChoice);
            ArrayList<Task> results = manager.filterByCompletionStatus(completion);

            displayHeader("Filtered Tasks");
            displayTasks(results);
        }
    }

    public void handleSortTasks() {
        if (!ensureTasksExist()) {
            return;
        }
        int sortChoice = getSortChoice();
        if (sortChoice == 0) {
            return;
        } else if (sortChoice == 1) {
            ArrayList<Task> results = manager.sortByPriority();
            displayHeader("Sorted Tasks");
            displayTasks(results);
        } else if (sortChoice == 2) {
            ArrayList<Task> results = manager.sortByCompletionStatus();
            displayHeader("Sorted Tasks");
            displayTasks(results);
        }
    }

    public void handleCompleteTask() {
        if (!ensureTasksExist()) {
            return;
        }
        int taskNum = getTaskNumber();
        if (taskNum == 0) {
            return;
        }
        manager.completeTask(taskNum);
        displaySuccess("Task completed.");
    }

    public void handleDeleteTask() {
        if (!ensureTasksExist()) {
            return;
        }
        int taskNum = getTaskNumber();
        if (taskNum == 0) {
            return;
        }
        manager.deleteTask(taskNum);
        displaySuccess("Task deleted.");
    }

    public void handleRenameTask() {
        if (!ensureTasksExist()) {
            return;
        }
        int taskNum = getTaskNumber();
        if (taskNum == 0) {
            return;
        }
        manager.renameTask(taskNum, getTaskName());
        displaySuccess("Task renamed.");
    }

    public void handleMarkIncomplete() {
        if (!ensureTasksExist()) {
            return;
        }
        int taskNum = getTaskNumber();
        if (taskNum == 0) {
            return;
        }
        manager.markTaskIncomplete(taskNum);
        displaySuccess("Task marked incomplete.");
    }

    public void handleSetPriority() {
        if (!ensureTasksExist()) {
            return;
        }
        int taskNum = getTaskNumber();
        if (taskNum == 0) {
            return;
        }
        int priorityNum = getPriorityChoice();
        if (priorityNum == 0) {
            return;
        }
        Priority priority = getPriorityFromChoice(priorityNum);
        manager.setTaskPriority(taskNum, priority);
        displaySuccess("Priority updated.");
    }

    public void handleSetCategory() {
        if (!ensureTasksExist()) {
            return;
        }
        int taskNum = getTaskNumber();
        if (taskNum == 0) {
            return;
        }
        int categoryNum = getCategoryChoice();
        if (categoryNum == 0) {
            return;
        }
        Category category = getCategoryFromChoice(categoryNum);
        manager.setTaskCategory(taskNum, category);
        displaySuccess("Category updated.");
    }

    public void handleExit() {
        displaySuccess("Have a nice day.");
        programRunning = false;
        scanner.close();
    }

    // =========================================================
    // Menu Display
    // =========================================================

    public void showFilterMenu() {
        displayHeader("Filter Menu");
        System.out.println("""
        1. Filter by Priority
        2. Filter by Category
        3. Filter by Completion Status
        0. Return""");
    }

    public void showPriorityMenu() {
        displayHeader("Priority Menu");
        System.out.println("""
        1. High
        2. Medium
        3. Low
        0. Return""");
    }

    public void showCategoryMenu() {
        displayHeader("Category Menu");
        System.out.println("""
        1. School
        2. Work
        3. Personal
        4. Fitness
        5. Church
        6. Other
        0. Return""");
    }

    public void showSortMenu() {
        displayHeader("Sort Menu");
        System.out.println("""
        1. Sort by Priority
        2. Sort by Completion Status
        0. Return""");
    }

    public void showCompletionMenu() {
        displayHeader("Completion Menu");
        System.out.println("""
        1. Completed
        2. Incomplete
        0. Return""");
    }

    // =========================================================
    // Input / Choice Getters
    // =========================================================

    public String getTaskName() {
        String taskName = "";
        while (taskName.isEmpty() || taskName.contains(",")) {
            System.out.println("Enter task name: ");
            taskName = scanner.nextLine();
            if (taskName.isEmpty()) {
                displayError("Cannot leave empty. Try again.");
            }
            if (taskName.contains(",")) {
                displayError("Cannot contain a comma. Try again.");
            }
        }
        return taskName;
    }

    public String getSearchTerm() {
        String searchTerm = "";
        while (searchTerm.isEmpty()) {
            System.out.println("Enter task name: ");
            searchTerm = scanner.nextLine();
            if (searchTerm.isEmpty()) {
                displayError("Cannot leave empty. Try again.");
            }
            if (searchTerm.equals("0")) {
                return "0";
            }
        }
        return searchTerm;
    }

    public int getTaskNumber() {
        return getValidatedInt("Enter a Task Number:", "Invalid task number!", 1, manager.sizeOfList(), true);
    }

    private int getMainMenuChoice() {
        return getValidatedInt("Enter Main Menu Option: ", "Number is not in range.", 1, 12, false);
    }

    private int getPriorityChoice() {
        showPriorityMenu();
        return getValidatedInt("Enter Priority Choice:", "Invalid Priority Choice.", 1, 3, true);
    }

    private int getCategoryChoice() {
        showCategoryMenu();
        return getValidatedInt("Enter Category Choice:", "Invalid Category Choice.", 1, 6, true);
    }

    private int getFilterChoice() {
        showFilterMenu();
        return getValidatedInt("Enter Filter Choice:", "Invalid Filter Choice.", 1, 3, true);
    }

    public int getSortChoice() {
        showSortMenu();
        return getValidatedInt("Enter Sort Choice", "Invalid Sort Choice", 1, 2, true);
    }

    public int getCompletionChoice() {
        showCompletionMenu();
        return getValidatedInt("Enter Completion Choice", "Invalid Completion Choice", 1, 2, true);
    }

    public int getValidatedInt(String prompt, String errorMessage, int min, int max, boolean allowZero) {
        System.out.println(prompt);
        boolean isValid = false;
        int num = 0;

        while (!isValid) {
            while (!scanner.hasNextInt()) {
                scanner.nextLine();
                displayError(errorMessage);
                displayError("Try again.");
            }
            num = scanner.nextInt();
            scanner.nextLine();
            if (allowZero && num == 0) {
                return 0;
            } else if (num < min || num > max) {
                displayError(errorMessage);
                displayError("Try again.");
            } else {
                isValid = true;
            }
        }
        return num;
    }

    // =========================================================
    // Choice Conversion Helpers
    // =========================================================

    private Priority getPriorityFromChoice(int choice) {
        return switch (choice) {
            case 1 -> Priority.HIGH;
            case 2 -> Priority.MEDIUM;
            case 3 -> Priority.LOW;
            default -> null;
        };
    }

    private Category getCategoryFromChoice(int choice) {
        return switch (choice) {
            case 1 -> Category.SCHOOL;
            case 2 -> Category.WORK;
            case 3 -> Category.PERSONAL;
            case 4 -> Category.FITNESS;
            case 5 -> Category.CHURCH;
            case 6 -> Category.OTHER;
            default -> null;
        };
    }

    private boolean getCompletionFromChoice(int choice) {
        switch (choice) {
            case 1:
                return true;
            default:
                return false;
        }
    }

    // =========================================================
    // Display Helpers
    // =========================================================

    public void displayTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            displayError("No tasks found.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i + 1 + ". " + tasks.get(i));
            }
        }
    }

    public void displaySuccess(String message) {
        System.out.println(message);
    }

    public void displayError(String message) {
        System.out.println(message);
    }

    public void displayHeader(String title) {
        System.out.printf("%n%s%n------------------------------%n", title);
    }

    private boolean ensureTasksExist() {
        if (!manager.hasTask()) {
            displayError("No tasks available.");
            return false;
        }
        return true;
    }
}

