import java.util.ArrayList;

public class TaskManager {
    private ArrayList<Task> tasks;


    public TaskManager(){
        tasks= new ArrayList<>();
    }

    public TaskManager(ArrayList<Task> tasks){
        this.tasks=tasks;
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
    public void markTaskIncomplete(int taskNumber){
        getTaskByNumber(taskNumber).markIncomplete();
    }

    public void deleteTask(int taskNumber){
        tasks.remove(getTaskByNumber(taskNumber));
    }

    public void displayTaskList(){
        if (tasks.isEmpty()){
            System.out.println("no task available");
        }
        else{
        System.out.println("Current Tasks: \n");}
        for(int i=0;i<tasks.size();i++){
            System.out.println(i+1+". "+ tasks.get(i));
        }
    }

    public void renameTask(int taskNumber,String newName){
        getTaskByNumber(taskNumber).changeName(newName);
    }


    public int sizeOfList(){
        return tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTaskPriority(int taskNumber, String priority){
        getTaskByNumber(taskNumber).setPriority(priority);
    }

    public void setTaskCategory(int taskNumber, String category){
        getTaskByNumber(taskNumber).setCategory(category);
    }
}
