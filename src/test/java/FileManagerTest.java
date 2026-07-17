import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class FileManagerTest {

    FileManager fileManager;
    File testFile;

    @BeforeEach
    void setUp() throws IOException {
        testFile=new File("test_tasks.txt");
        testFile.createNewFile();
        fileManager=new FileManager("test_tasks.txt");
    }

    @AfterEach
    void tearDown() {
        testFile.delete();
    }

    // ---------- Load Tests ----------

    @Test
    void loadTasksShouldLoadSingleValidTask() throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            writer.write("Study Java,false,HIGH,SCHOOL");
            writer.close();
            ArrayList<Task> loadedTasks = fileManager.loadTasks();
            assertEquals(1, loadedTasks.size());
            assertEquals("Study Java", loadedTasks.getFirst().getName());
            assertFalse(loadedTasks.getFirst().isCompleted());
            assertEquals(Priority.HIGH,loadedTasks.getFirst().getPriority());
            assertEquals(Category.SCHOOL,loadedTasks.getFirst().getCategory());
        }
    }

    @Test
    void loadTasksShouldLoadMultipleValidTasks() throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            writer.write("Study Java,false,HIGH,SCHOOL");
            writer.newLine();
            writer.write("Workout,true,MEDIUM,FITNESS");
            writer.newLine();
            writer.write("Buy Groceries,false,LOW,PERSONAL");
        }

        ArrayList<Task> loadedTasks=fileManager.loadTasks();

        assertEquals(3, loadedTasks.size());

        // First task
        assertEquals("Study Java", loadedTasks.get(0).getName());
        assertFalse(loadedTasks.get(0).isCompleted());
        assertEquals(Priority.HIGH, loadedTasks.get(0).getPriority());
        assertEquals(Category.SCHOOL, loadedTasks.get(0).getCategory());

        // Second task
        assertEquals("Workout", loadedTasks.get(1).getName());
        assertTrue(loadedTasks.get(1).isCompleted());
        assertEquals(Priority.MEDIUM, loadedTasks.get(1).getPriority());
        assertEquals(Category.FITNESS, loadedTasks.get(1).getCategory());

        // Third task
        assertEquals("Buy Groceries", loadedTasks.get(2).getName());
        assertFalse(loadedTasks.get(2).isCompleted());
        assertEquals(Priority.LOW, loadedTasks.get(2).getPriority());
        assertEquals(Category.PERSONAL, loadedTasks.get(2).getCategory());
    }

    @Test
    void loadTasksShouldReturnEmptyListWhenFileIsEmpty() throws IOException {
        ArrayList<Task> loadedTasks=fileManager.loadTasks();
        assertTrue(loadedTasks.isEmpty());
    }

    @Test
    void loadTasksShouldIgnoreBlankLines() throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))){
            writer.write("Study Java,false,HIGH,SCHOOL");
            writer.newLine();
            writer.newLine();   // Blank line
            writer.write("Workout,true,LOW,FITNESS");
        }
        ArrayList<Task> loadedTask=fileManager.loadTasks();
        assertEquals(2,loadedTask.size());
    }

    @Test
    void loadTasksShouldSkipTaskWithInvalidPriority() throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))){
            writer.write("Study Java,false,URGENT,SCHOOL");
        }
        ArrayList<Task> loadedTask=fileManager.loadTasks();
        assertEquals(0,loadedTask.size());
    }

    @Test
    void loadTasksShouldSkipTaskWithInvalidCategory() throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))){
            writer.write("Study Java,false,HIGH,GAMING");
        }
        ArrayList<Task> loadedTask=fileManager.loadTasks();
        assertEquals(0,loadedTask.size());
    }

    @Test
    void loadTasksShouldSkipTaskWithInvalidCompletedValue() throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))){
            writer.write("Study Java,maybe,HIGH,SCHOOL");
        }
        ArrayList<Task> loadedTask=fileManager.loadTasks();
        assertEquals(0,loadedTask.size());
    }


    @Test
    void loadTasksShouldSkipTaskWithEmptyTaskName() throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))){
            writer.write(",false,HIGH,SCHOOL");
        }
        ArrayList<Task> loadedTask=fileManager.loadTasks();
        assertEquals(0,loadedTask.size());
    }

    @Test
    void loadTasksShouldSkipTaskWithWrongNumberOfValues() throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))){
            writer.write("Study Java,false,HIGH");
        }
        ArrayList<Task> loadedTask=fileManager.loadTasks();
        assertEquals(0,loadedTask.size());
    }

    @Test
    void loadTasksShouldLoadRemainingValidTasksAfterCorruptedLine() throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))){
            writer.write("Study Java,false,HIGH,SCHOOL");
            writer.newLine();
            writer.write("Workout,maybe,LOW,FITNESS");
            writer.newLine();
            writer.write("Buy Groceries,true,MEDIUM,PERSONAL");
        }
        ArrayList<Task> loadedTask=fileManager.loadTasks();
        assertEquals(2,loadedTask.size());
    }

    // ---------- Save Tests ----------

    @Test
    void saveTasksShouldSaveSingleTaskCorrectly() throws IOException {
        ArrayList<Task> savedTasks = new ArrayList<>();
        savedTasks.add(new Task("Study Java",false,Priority.HIGH,Category.SCHOOL));
        fileManager.saveTasks(savedTasks);
        try(BufferedReader reader= new BufferedReader(new FileReader(testFile))){
            assertEquals("Study Java,false,HIGH,SCHOOL",reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    void saveTasksShouldSaveMultipleTasksCorrectly() throws IOException {
        ArrayList<Task> savedTasks = new ArrayList<>();

        savedTasks.add(new Task(
                "Study Java",
                false,
                Priority.HIGH,
                Category.SCHOOL));

        savedTasks.add(new Task(
                "Workout",
                true,
                Priority.MEDIUM,
                Category.FITNESS));

        savedTasks.add(new Task(
                "Buy Groceries",
                false,
                Priority.LOW,
                Category.PERSONAL));

        fileManager.saveTasks(savedTasks);
        try(BufferedReader reader= new BufferedReader(new FileReader(testFile))) {
            assertEquals("Study Java,false,HIGH,SCHOOL", reader.readLine());
            assertEquals("Workout,true,MEDIUM,FITNESS", reader.readLine());
            assertEquals("Buy Groceries,false,LOW,PERSONAL", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    void saveTasksShouldSaveEmptyTaskList() throws IOException {
        ArrayList<Task> emptyTaskList = new ArrayList<>();
        fileManager.saveTasks(emptyTaskList);
        try(BufferedReader reader= new BufferedReader(new FileReader(testFile))){
            assertNull(reader.readLine());
        }
    }

}