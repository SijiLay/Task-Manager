import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
public class Main {
    private Scanner scanner = new Scanner(System.in);
    private boolean programRunning = false;
    private final TaskManager manager;

    public static void main(String[] args) {
        FileManager fileManager = new FileManager("task.txt");
        ArrayList<Task> loadedTasks = fileManager.loadTasks();
        TaskManager manager = new TaskManager(loadedTasks);
        Main app = new Main(manager);
        app.run();
        fileManager.saveTasks(manager.getTasks());
    }

    public Main(TaskManager manager) {
        this.manager = manager;
    }

    public void run() {
        System.out.println("=============================================================\n   Welcome to TASK MANAGER Project by Olmayowa Siji Layeni \n=============================================================\n");
        programRunning = true;
        while (programRunning) {
            showMainMenu();
            int choice = getMainMenuChoice();
            handleChoice(choice);
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
        ============================""");    }

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
                System.out.println("Invalid choice option.");
                break;
        }
    }

    public String getTaskName() {
        String taskName = "";
        while (taskName.isEmpty() || taskName.contains(",")) {
            System.out.println("Enter task name: ");
            taskName = scanner.nextLine();
            if (taskName.isEmpty()) {
                displayError("Cannot leave empty, Try Again");
            }
            if (taskName.contains(",")) {
                displayError("Cannot Contain Comma, Try Again");
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
                displayError("Cannot leave empty, Try Again");
            }
            if (searchTerm.equals("0")){
                return "0";
            }
        }
        return searchTerm;
    }

    public int getTaskNumber() {
        return getValidatedInt("Enter a Task Number:","Invalid task number!",1,manager.sizeOfList(),true);
    }

    private String getPriorityFromChoice(int choice) {
        if (choice == 1) {
            return "High";
        } else if (choice == 2) {
            return "Medium";
        } else if (choice == 3) {
            return "Low";
        } else {
            return null;
        }
    }

    private int getPriorityChoice() {
        showPriorityMenu();
        return getValidatedInt("Enter Priority Choice:","Invalid Priority Choice.",1,3,true);
    }

    private String getCategoryFromChoice(int choice) {
        if (choice == 1) {
            return "School";
        } else if (choice == 2) {
            return "Work";
        } else if (choice == 3) {
            return "Personal";
        } else if (choice == 4) {
            return "Fitness";
        } else if (choice == 5) {
            return "Church";
        } else if (choice == 6) {
            return "Other";
        } else {
            return null;
        }
    }


    private int getCategoryChoice() {
        showCategoryMenu();
        return getValidatedInt("Enter Category Choice:","Invalid Category Choice.",1,6,true);
    }

    public void showFilterMenu() {
        System.out.println("""
        1. Filter by Priority
        2. Filter by Category
        3. Filter by Completion Status
        0. Return""");
    }

    public void showPriorityMenu(){
        System.out.println("""
        1. High
        2. Medium
        3. Low
        0. Return
        """);
    }

    public void showCategoryMenu(){
        System.out.println("""
        1. School
        2. Work
        3. Personal
        4. Fitness
        5. Church
        6. Other
        0. Return""");
    }

    public int getCompletionChoice() {

        System.out.println("""
        1. Completed
        2. Incomplete
        0. Return""");

        return getValidatedInt("Enter Completion Choice","Invalid Completion Choice",1,2,true);
    }

    private boolean getCompletionFromChoice(int choice) {
        if (choice == 1) {
            return true;
        } else {
            return false;
        }
    }

    private int getFilterChoice() {
        showFilterMenu();
        return getValidatedInt("Enter Filter Choice:","Invalid Filter Choice.",1,3,true);
    }

    public void showSortMenu() {
        System.out.println("""
            1. Sort by Priority
            2. Sort by Completion Status
            0. Return""");
    }

    public int getSortChoice() {
        showSortMenu();
        return getValidatedInt("Enter Sort Choice","Invalid Sort Choice",1,2,true);
    }

    public void handleAddTask() {
        String taskName = getTaskName();
        manager.addTask(taskName);
        System.out.println("Task Added.");
    }

    public void handleViewTask() {
        displayHeader("Current Tasks");
        displayTasks(manager.getTasks());
        System.out.println();
    }

    public void handleSearchTasks() {
        if (!ensureTasksExist()) {
            return;
        }
        String searchTerm = getSearchTerm();
        if(searchTerm.equals("0")){
            return;
        }
        ArrayList<Task> results = manager.searchTasks(searchTerm);
        displayHeader("Search Results");
        displayTasks(results);

    }

    public void handleFilterTasks(){
        if (!ensureTasksExist()) {
            return;
        }
        int filterChoice = getFilterChoice();
        if(filterChoice==0){
            return;
        }
        else if (filterChoice == 1) {
            int priorityChoice = getPriorityChoice();
            String priority = getPriorityFromChoice(priorityChoice);
            ArrayList<Task> results = manager.filterByPriority(priority);

            displayHeader("Filtered Tasks");
            displayTasks(results);

        }
        else if (filterChoice == 2) {
            int categoryChoice = getCategoryChoice();
            String category = getCategoryFromChoice(categoryChoice);
            ArrayList<Task> results = manager.filterByCategory(category);
            displayHeader("Filtered Tasks");
            displayTasks(results);

        }
        else if (filterChoice == 3) {
            int completionChoice = getCompletionChoice();
            boolean completion = getCompletionFromChoice(completionChoice);
            ArrayList<Task> results = manager.filterByCompletionStatus(completion);

            displayHeader("Filtered Tasks");
            displayTasks(results);

        }
    }

    public void handleSortTasks(){
        if (!ensureTasksExist()) {
            return;
        }
        int sortChoice = getSortChoice();
        if(sortChoice==0){
            return;
        }
        else if (sortChoice == 1) {
            ArrayList<Task> results = manager.sortByPriority();
            displayHeader("Sorted Tasks");

            displayTasks(results);
        } else if (sortChoice == 2) {
            ArrayList<Task> results = manager.sortByCompletionStatus();
            displayHeader("Sorted Tasks");

            displayTasks(results);

        }
    }

    public void handleCompleteTask(){
        if (!ensureTasksExist()) {
            return;
        }
        int taskNum = getTaskNumber();
        if(taskNum==0){
            return;
        }
        manager.completeTask(taskNum);
        System.out.println("Task Completed!");
    }

    public void handleDeleteTask(){
        if (!ensureTasksExist()) {
            return;
        }
        int taskNum = getTaskNumber();
        if(taskNum==0){
            return;
        }
        manager.deleteTask(taskNum);
        displaySuccess("Task Deleted.");

    }

    public void handleRenameTask(){
        if (!ensureTasksExist()) {
            return;
        }
        int taskNum = getTaskNumber();
        if(taskNum==0){
            return;
        }
        manager.renameTask(taskNum, getTaskName());
        displaySuccess("Task Renamed.");
    }

    public void handleMarkIncomplete(){
        if (!ensureTasksExist()) {
            return;
        }
        int taskNum = getTaskNumber();
        if(taskNum==0){
            return;
        }
        manager.markTaskIncomplete(taskNum);
        displaySuccess("Task Marked Incomplete.");

    }

    public void handleSetPriority(){
        if (!ensureTasksExist()) {
            return;
        }
        int taskNum = getTaskNumber();
        if(taskNum==0){
            return;
        }
        int priorityNum = getPriorityChoice();
        if(priorityNum==0){
            return;
        }
        String priority = getPriorityFromChoice(priorityNum);
        manager.setTaskPriority(taskNum, priority);
        displaySuccess("Priority Updated.");

    }

    public void handleSetCategory(){
        if (!ensureTasksExist()) {
            return;
        }
        int taskNum = getTaskNumber();
        if(taskNum==0){
            return;
        }
        int categoryNum = getCategoryChoice();
        if(categoryNum==0){
            return;
        }
        String category = getCategoryFromChoice(categoryNum);
        manager.setTaskCategory(taskNum, category);
        displaySuccess("Category Updated.");
    }

    public void handleExit(){
        System.out.println("Have a nice Day.");
        programRunning=false;
        scanner.close();
    }

    public void displayTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            displayError("no task available.");
        } else {
            for(int i=0;i<tasks.size();i++){
                System.out.println(i+1+". "+ tasks.get(i));
            }
        }
    }
    public void displaySuccess(String message){
        System.out.println(message);
    }
    public void displayError(String message){
        System.out.println(message);
    }

    public void displayHeader(String title) {
        System.out.printf("%n%s%n------------------------------%n", title);
    }

    private int getMainMenuChoice(){
        return getValidatedInt("Enter Main Menu Option: ","Number is not in range.", 1,12,false);
    }

    public int getValidatedInt(String prompt, String errorMessage, int min, int max, boolean allowZero){
        System.out.println(prompt);
        boolean isValid = false;
        int num = 0;

        while (!isValid) {
            while (!scanner.hasNextInt()) {
                scanner.nextLine();
                displayError(errorMessage);
                System.out.println("Try again: ");
            }
            num = scanner.nextInt();
            scanner.nextLine();
            if(allowZero && num==0){
                return 0;
            }
            else if (num < min || num > max) {
                displayError(errorMessage);
                System.out.println("Try again: ");
            }
            else {
                isValid = true;
            }
        }
        return num;
    }

    private boolean ensureTasksExist(){
        if(!manager.hasTask()){
            displayError("No tasks available");
            return false;
        }
        return true;
    }
}