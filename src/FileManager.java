import java.io.*;
import java.util.ArrayList;

public class FileManager {
    private final String fileName;
    public FileManager(String fileName){
        this.fileName=fileName;
    }

    // Serious file-saving errors are handled by Main.
    public void saveTasks(ArrayList<Task> tasks) throws IOException{
        try(BufferedWriter writer = (new BufferedWriter(new FileWriter(fileName)))) {

            for (Task task : tasks) {
                String name = task.getName();
                boolean status = task.isCompleted();
                Priority priority=task.getPriority();
                Category category = task.getCategory();
                writer.write(name + "," + status+","+priority+","+category);
                writer.newLine();
            }
        }
    }

    // Serious file-reading errors are handled by Main.
    public ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> taskList = new ArrayList<>();
        File file = new File(fileName);
        int lineNum=0;
        System.out.println("Loading task...");

        if (!file.exists()) {
            System.out.println("No file found");
            return taskList;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                lineNum++;
                if(line.trim().isEmpty()){
                    continue;
                }
                // A corrupted task should not stop the rest of the file from loading.
                try {
                    Task task=parseTaskLine(line,lineNum);
                    taskList.add(task);
                }
                // Skip invalid task data and continue loading the remaining tasks.
                catch (IllegalArgumentException e){
                    System.out.println("Warning: Skipping line "+lineNum+ " - "+e.getMessage());
                }
            }
        }
        return taskList;
    }

    private Task parseTaskLine(String line, int lineNumber){
        String[] parts = line.split(",");

        if(parts.length!=4){
            throw new IllegalArgumentException("Expected 4 values but found "+ parts.length);
        }

        String completedText = parts[1].trim();
        if(!completedText.equalsIgnoreCase("true") && !completedText.equalsIgnoreCase("false")){
            throw new IllegalArgumentException("Completed option is invalid");
        }



        String taskName = parts[0].trim();

        if(taskName.isEmpty()){
            throw new IllegalArgumentException("Task Name is Empty");
        }

        boolean completionStatus = Boolean.parseBoolean(completedText);
        Priority priority = Priority.fromString(parts[2].trim());
        Category category = Category.fromString(parts[3].trim());



        return new Task(taskName,completionStatus,priority,category);

    }
}

