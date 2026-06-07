import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        TaskManager manager = new TaskManager();
        System.out.println(manager.hasTask());
        manager.addTask("Dishes");
        System.out.println(manager.hasTask());
    }
}