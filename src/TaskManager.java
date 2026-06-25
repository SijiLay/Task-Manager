import java.util.ArrayList;
import java.util.Collections;

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

    public ArrayList<Task> searchTask(String searchTerm){
        ArrayList<Task> searchList = new ArrayList<>();

        for(Task task:tasks){
            if(task.getName().toLowerCase().contains(searchTerm.toLowerCase())){
               searchList.add(task);
            }
        }
        return searchList;
    }

    public ArrayList<Task> filterByPriority(String priority){
        ArrayList<Task> filteredList = new ArrayList<>();
        for(Task task:tasks){
            if(task.getPriority().contentEquals(priority)){
                filteredList.add(task);
            }
        }
        return filteredList;
    }

    public ArrayList<Task> filterByCategory(String category){
        ArrayList<Task> filteredList = new ArrayList<>();
        for(Task task:tasks){
            if(task.getCategory().contentEquals(category)){
                filteredList.add(task);
            }
        }
        return filteredList;
    }

    public ArrayList<Task> filterByCompletionStatus(boolean completed) {
        ArrayList<Task> filteredList = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isCompleted() == completed) {
                filteredList.add(task);
            }
        }
        return filteredList;
    }

    public ArrayList<Task> sortByPriority(){
        ArrayList<Task> results = new ArrayList<>(tasks);

        Collections.sort(results, (task1, task2) ->
                getPriorityValue(task1.getPriority()) - getPriorityValue(task2.getPriority()));
        return results;
    }

    public ArrayList<Task> sortByCompletionStatus(){
        ArrayList<Task> results = new ArrayList<>(tasks);

        Collections.sort(results, (task1, task2) ->
                getCompletionValue(task1.isCompleted()) - getCompletionValue(task2.isCompleted()));
        return results;
    }

    private int getPriorityValue(String priority){
        if(priority.equals("High")){
            return 1;
        }
        else if(priority.equals("Medium")){
            return 2;
        }
        else if(priority.equals("Low")){
            return 3;
        }
        else {
            return 4;
        }
    }

    private int getCompletionValue(boolean completion ){
        if(!completion){
            return 1;
        }
        else {
            return 2;
        }
    }
}
