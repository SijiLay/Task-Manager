import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        TaskManager manager = new TaskManager();
        System.out.println(manager.hasTask());
        manager.addTask("Dishes");
        System.out.println(manager.hasTask());
        System.out.println(manager.getTaskByNumber(1));
        manager.completeTask(1);
        System.out.println(manager.getTaskByNumber(1));
        manager.addTask("Clothes");
        manager.getTaskList();
        manager.deleteTask(1);
        System.out.println(manager.hasTask());
        
    }
}