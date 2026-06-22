import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    Scanner scanner = new Scanner(System.in);
    private boolean programRunning = false;
    private final TaskManager manager;



    public static void main(String[] args){
        FileManager fileManager = new FileManager("task.txt");
        ArrayList<Task> loadedTasks = fileManager.loadTasks();
        TaskManager manager = new TaskManager(loadedTasks);
        Main app =new Main(manager);
        app.run();
        fileManager.saveTasks(manager.getTasks());
    }
    public Main(TaskManager manager) {
        this.manager=manager;
    }
    
    public void run(){
        System.out.println("====================\nTASK MANAGER\n====================\n");
        programRunning=true;
        while(programRunning){
            showMenu();
            while(!scanner.hasNextInt()){
                scanner.nextLine();
                System.out.println("No letters or words allowed ");
                System.out.println("Try again: ");
            }
            int choice=scanner.nextInt();
            scanner.nextLine();
            handleChoice(choice);
        }
    }

    public void showMenu(){
        System.out.println("1. Add Task");
        System.out.println("2. View Tasks");
        System.out.println("3. Complete Task");
        System.out.println("4. Delete Task");
        System.out.println("5. Change Name of Task");
        System.out.println("6. Mark Task Incomplete");
        System.out.println("7. Set Task Priority");
        System.out.println("8. Set Task Category");
        System.out.println("9. Exit program\n");
        System.out.println("Choose an option:");
    }

    public String getTaskName(){
        String taskName="";
        while(taskName.isEmpty() || taskName.contains(",")){
            System.out.println("Enter task name: ");
            taskName=scanner.nextLine();
            if(taskName.isEmpty()){
                System.out.println("Cannot leave empty, Try Again");
            }
            if(taskName.contains(", ")){
                System.out.println("Cannot Contain Comma, Try Again");
            }
        }
        return taskName;
    }

    public int getTaskNumber(){
        boolean isValid=false;
        System.out.println("Enter task number: ");
        int taskNum = 0;

        while(!isValid){
            while(!scanner.hasNextInt()){
                scanner.nextLine();
                System.out.println("No Letters or Words allowed");
                System.out.println("Try again: ");
            }
            taskNum=scanner.nextInt();
            scanner.nextLine();

            if(!isValidTaskNumber(taskNum)) {
                System.out.println("Task does not exist");
                System.out.println("Try again: ");
            }
            else {
                isValid=true;
            }
       }
        return taskNum;
    }

    public void handleChoice(int choice){
        if(choice==1){
            String taskName=getTaskName();
            manager.addTask(taskName);
            System.out.println("Task Added!!!");
        }
        else if(choice==2){
            manager.displayTaskList();
            System.out.println();
        }
        else if(choice==3){
            if(!manager.hasTask()){
                System.out.println("No tasks available");
                return;
            }
            int taskNum=getTaskNumber();
            manager.completeTask(taskNum);
            System.out.println("Task Completed!");
        }
        else if(choice==4){
            if(!manager.hasTask()){
                System.out.println("No tasks available");
                return;
            }
            int taskNum=getTaskNumber();
            manager.deleteTask(taskNum);
            System.out.println("Task Deleted!");
        }
        else if (choice==5){
            if(!manager.hasTask()){
                System.out.println("No tasks available");
                return;
            }
            int taskNum=getTaskNumber();
            manager.renameTask(taskNum,getTaskName());
            System.out.println("Task Renamed!");
        }
        else if(choice==6){
            if(!manager.hasTask()){
                System.out.println("No tasks available");
                return;
            }
            int taskNum=getTaskNumber();
            manager.markTaskIncomplete(taskNum);
            System.out.println("Task Marked Incomplete!");
        }
        else if(choice==7){
            if(!manager.hasTask()){
                System.out.println("No tasks available");
                return;
            }
            int taskNum=getTaskNumber();
            int priorityNum=getPriorityChoice();
            String priority = getPriorityFromChoice(priorityNum);
            manager.setTaskPriority(taskNum,priority);
            System.out.println("Priority Updated!");
        }
        else if(choice==8){
            if(!manager.hasTask()){
                System.out.println("No tasks available");
                return;
            }
            int taskNum=getTaskNumber();
            int categoryNum=getCategoryChoice();
            String category = getCategoryFromChoice(categoryNum);
            manager.setTaskCategory(taskNum,category);
            System.out.println("Category Updated!");
        }
        else if(choice ==9){
            System.out.println("Have a nice Day!!!");
            programRunning=false;
            scanner.close();
        }
        else if(choice == 99){
            int priorityChoice = getPriorityChoice();
            String priority = getPriorityFromChoice(priorityChoice);

            System.out.println("Choice: " + priorityChoice);
            System.out.println("Priority: " + priority);
        }
        else {
            System.out.println("invalid choice option");
        }
    }

    private boolean isValidTaskNumber(int taskNum){
        return taskNum >= 1 && taskNum <= manager.sizeOfList();
    }

    private String getPriorityFromChoice(int choice){
        if(choice==1){
            return "High";
        }
        else if(choice==2){
            return "Medium";
        }
        else if(choice==3){
            return "Low";
        }
        else{
            return null;
        }
    }

    private int getPriorityChoice(){
        boolean isValid=false;
        System.out.println("1. High\n" +
                "2. Medium\n" +
                "3. Low\n" +
                "\n" +
                "Enter priority:");
        int  priorityNum = 0;

        while(!isValid){
            while(!scanner.hasNextInt()){
                scanner.nextLine();
                System.out.println("No Letters or Words allowed");
                System.out.println("Try again: ");
            }
            priorityNum=scanner.nextInt();
            scanner.nextLine();

            if(priorityNum<1 ||priorityNum>3) {
                System.out.println("Not an available priority");
                System.out.println("Try again: ");
            }
            else {
                isValid=true;
            }
        }
        return priorityNum;
    }

    private String getCategoryFromChoice(int choice){
        if(choice==1){
            return "School";
        }
        else if(choice==2){
            return "Work";
        }
        else if(choice==3){
            return "Personal";
        } else if (choice==4) {
            return "Fitness";
        } else if (choice == 5) {
            return "Church";
        } else{
            return null;
        }
    }

    private int getCategoryChoice(){
        boolean isValid=false;
        System.out.println("1. School\n" +
                "2. Work\n" +
                "3. Personal\n" +
                "4. Fitness\n" +
                "5. Church\n" +
                "6. Other");
        int  categoryNum = 0;

        while(!isValid){
            while(!scanner.hasNextInt()){
                scanner.nextLine();
                System.out.println("No Letters or Words allowed");
                System.out.println("Try again: ");
            }
            categoryNum=scanner.nextInt();
            scanner.nextLine();

            if(categoryNum<1 ||categoryNum>6) {
                System.out.println("Not an available priority");
                System.out.println("Try again: ");
            }
            else {
                isValid=true;
            }
        }
        return categoryNum;
    }

}