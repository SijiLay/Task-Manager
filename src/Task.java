public class Task {
    private String name;
    private boolean completed;

    public Task(String name){
        this.name=name;
        this.completed=false;
    }

     public Task(String name,boolean completed){
        this.name=name;
        this.completed=completed;
     }

    public String getName(){
        return name;
    }

    public boolean isCompleted(){
        return completed;
    }

    public void markCompleted(){
        completed=true;
    }

    public void markIncomplete(){
        completed=false;
    }

    public void changeName(String newName){
        this.name=newName;
    }

    @Override
    public String toString(){
        return name + (completed ? " [X]" : " [ ]");
    }
}
