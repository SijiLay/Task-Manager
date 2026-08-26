public class ApiTaskRequest {
    private String name;
    private String priority;
    private String category;


    public ApiTaskRequest(){
    }

    public void setName(String name){
        this.name = name;
    }
    public void setPriority(String priority){
        this.priority = priority;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public String getName(){
        return this.name;
    }
    public String getPriority(){
        return this.priority;
    }
    public String getCategory(){
        return this.category;
    }

}
