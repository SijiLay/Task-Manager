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

    public void addTask(String name, Priority priority, Category category) {
        Task task = new Task(name, false, priority, category);
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

    public void renameTask(int taskNumber,String newName){
        getTaskByNumber(taskNumber).changeName(newName);
    }


    public int sizeOfList(){
        return tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTaskPriority(int taskNumber, Priority priority){
        getTaskByNumber(taskNumber).setPriority(priority);
    }

    public void setTaskCategory(int taskNumber, Category category){
        getTaskByNumber(taskNumber).setCategory(category);
    }

    public ArrayList<Task> searchTasks(String searchTerm){
        ArrayList<Task> searchList = new ArrayList<>();

        for(Task task:tasks){
            if(task.getName().toLowerCase().contains(searchTerm.toLowerCase())){
               searchList.add(task);
            }
        }
        return searchList;
    }

    public ArrayList<Task> filterByPriority(Priority priority){
        ArrayList<Task> filteredList = new ArrayList<>();
        for(Task task:tasks){
            if(task.getPriority()==priority){
                filteredList.add(task);
            }
        }
        return filteredList;
    }

    public ArrayList<Task> filterByCategory(Category category){
        ArrayList<Task> filteredList = new ArrayList<>();
        for(Task task:tasks){
            if(task.getCategory() == category){
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

        results.sort((task1, task2) ->
                (task1.getPriority().getRank()) - (task2.getPriority().getRank()));
        return results;
    }

    public ArrayList<Task> sortByCompletionStatus(){
        ArrayList<Task> results = new ArrayList<>(tasks);

        results.sort((task1, task2) ->
                getCompletionValue(task1.isCompleted()) - getCompletionValue(task2.isCompleted()));
        return results;
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
