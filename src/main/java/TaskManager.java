import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final List<Task> tasks;


    public TaskManager(List<Task> tasks){
        this.tasks=tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public boolean hasTask(){
       return !tasks.isEmpty();
    }

    public void completeTask(Task task){
        task.markCompleted();
    }

    public void markTaskIncomplete(Task task){
        task.markIncomplete();
    }

    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    public List<Task> getTasks() {
        return tasks;
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
