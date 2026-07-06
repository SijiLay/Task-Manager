public class Task {
    private String name;
    private boolean completed;
    private Priority priority;
    private Category category;

    public Task(String name){
        this.name=name;
        this.completed=false;
        this.priority=Priority.MEDIUM;
        this.category=Category.OTHER;
    }

     public Task(String name,boolean completed,Priority priority,Category category){
        this.name=name;
        this.completed=completed;
        this.priority=priority;
        this.category=category;
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
        return name + (completed ? " [X]" : " [ ]")+" ("+priority+")"+" ["+category+"]";
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public Priority getPriority() {
        return priority;
    }
}
