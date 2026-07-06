import java.io.*;
import java.util.ArrayList;

public class FileManager {
    private final String fileName;
    public FileManager(String fileName){
        this.fileName=fileName;
    }
    public void saveTasks(ArrayList<Task> tasks) {
        try {
            BufferedWriter writer = (new BufferedWriter(new FileWriter(fileName)));
            for (Task task : tasks) {
                String name = task.getName();
                boolean status = task.isCompleted();
                Priority priority=task.getPriority();
                Category category = task.getCategory();
                writer.write(name + "," + status+","+priority+","+category);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> taskList = new ArrayList<>();
        File file = new File(fileName);
        System.out.println("Loading task...");

        if (!file.exists()) {
            System.out.println("No file found");
            return taskList;
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Priority priority = Priority.fromString(parts[2]);
                Category category = Category.fromString(parts[3]);

                String taskName = parts[0];
                boolean completionStatus = Boolean.parseBoolean(parts[1]);

                Task task = new Task(taskName, completionStatus, priority, category);
                taskList.add(task);
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return taskList;
    }
}

