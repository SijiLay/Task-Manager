import java.util.ArrayList;

public class TaskManager {
    private ArrayList<Task> tasks;


    public TaskManager(){
        tasks= new ArrayList<>();
    }

    public void addTask(String name){
        Task task =new Task(name);
        tasks.add(task);
    }

    public boolean hasTask(){
       return !tasks.isEmpty();
    }

    public Task getTaskByNumber(int taskNumber){
        return tasks.get(taskNumber - 1);
    }

    public void completeTask(int taskNumber){
        getTaskByNumber(taskNumber).markCompleted();
    }

    public void deleteTask(int taskNumber){
        tasks.remove(getTaskByNumber(taskNumber));
    }

    public void displayTaskList(){
        if (tasks.isEmpty()){
            System.out.println("no task available");
        }
        for(int i=0;i<tasks.size();i++){
            System.out.println(i+1+". "+ tasks.get(i));
        }
    }

    public int sizeOfList(){
        return tasks.size();
    }
}
