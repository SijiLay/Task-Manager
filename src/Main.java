import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    TaskManager manager = new TaskManager();
    Scanner scanner = new Scanner(System.in);
    private boolean programRunning = false;

    public static void main(String[] args){
        Main app = new Main();
        app.run();
    }


    public void run(){
        programRunning=true;
        while(programRunning){
            showMenu();
            while(!scanner.hasNextInt()){
                scanner.nextLine();
                System.out.println("Not a Valid Number ");
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
        System.out.println("5. Exit program");
    }

    public String getTaskName(){
        String taskName="";
        while(taskName.isEmpty()){
            System.out.println("Enter task name: ");
            taskName=scanner.nextLine();
            if(taskName.isEmpty()){
                System.out.println("Cannot leave empty, Try Again");
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
                System.out.println("Not a Valid Number");
                System.out.println("Try again: ");
            }
            taskNum=scanner.nextInt();
            scanner.nextLine();
            if((taskNum < 1) || (taskNum > manager.sizeOfList())) {
                System.out.println("Task does not exist");
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
            System.out.println("Success");
        }
        else if(choice==2){
            manager.displayTaskList();
        }
        else if(choice==3){
            int taskNum=getTaskNumber();
            manager.completeTask(taskNum);
            //display message
        }
        else if(choice==4){
            int taskNum=getTaskNumber();
            manager.deleteTask(taskNum);
        }
        else if(choice ==5){
            programRunning=false;
            scanner.close();
        }
        else {
            System.out.println("invalid choice option");
        }
    }
}