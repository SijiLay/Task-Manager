public class Task {
    private String name;
    private boolean completed;

    public Task(String name){
        this.name=name;
        completed=false;
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
        name=newName;
    }

    @Override
    public String toString(){
        if (completed){
            return name+" [X]";
        }
        else{
            return name+" [ ]";
        }
    }
}
