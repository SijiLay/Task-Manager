public class ApiTask {
    private Long id;
    private String name;
    private boolean completed;
    private String priority;
    private String category;

    public ApiTask() {
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public void setCategory(String category) {
        this.category = category;
    }


    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public boolean isCompleted(){
        return completed;
    }
    public String getPriority(){
        return priority;
    }
    public String getCategory(){
        return category;
    }
}
