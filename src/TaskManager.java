import java.util.ArrayList;

public class TaskManager {
    ArrayList<Task> tasks;


    public TaskManager(){
        tasks= new ArrayList<>();
    }
    public void addTask(String name){
        Task task =new Task(name);
        tasks.add(task);
    }
    public boolean hasTask(){
        if(tasks.size()>0){
            return true;
        }
        else {
            return false;
        }
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
    public void getTaskList(){
        for(int i=0;i<tasks.size();i++){
            int number=1;
            System.out.println(number+". "+tasks.indexOf(i));
        }
    }



}
