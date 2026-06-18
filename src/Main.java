import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    Scanner scanner = new Scanner(System.in);
    private boolean programRunning = false;
    private final TaskManager manager;



    public static void main(String[] args){
        FileManager fileManager = new FileManager("task.txt");
        ArrayList<Task> loadedTasks = fileManager.loadTask();
        TaskManager manager = new TaskManager(loadedTasks);
        Main app =new Main(manager);
        app.run();
        fileManager.saveTask(manager.getTasks());
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
        System.out.println("7. Exit program\n");
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

            isValidTaskNumber(taskNum);

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
        else if(choice ==7){
            System.out.println("Have a nice Day!!!");
            programRunning=false;
            scanner.close();
        }
        else {
            System.out.println("invalid choice option");
        }
    }

    private boolean isValidTaskNumber(int taskNum){
        return taskNum >= 1 && taskNum <= manager.sizeOfList();
    }


}