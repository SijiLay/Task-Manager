import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {

    TaskManager manager;
    private DatabaseManager databaseManager;

    @BeforeEach
    void setUp() {
        databaseManager = new DatabaseManager();
        databaseManager.initializeDatabase();

        manager = new TaskManager(new ArrayList<>(), databaseManager);
    }

    @Test
    void addTaskShouldAddTaskToList() {
        manager.addTask("Study Java");
        assertEquals(1,manager.sizeOfList());
        assertEquals("Study Java",manager.getTaskByNumber(1).getName());
    }

    @Test
    void hasTaskShouldReturnTrueWhenTasksExist() {
        manager.addTask("Study Java");
        assertTrue(manager.hasTask());
    }

    @Test
    void hasTaskShouldReturnFalseWhenNoTasksExist() {
        assertFalse(manager.hasTask());
    }

    @Test
    void sizeOfListShouldReturnCorrectSize() {
        manager.addTask("Study Java");
        assertEquals(1,manager.sizeOfList());
        manager.addTask("Study Python");
        manager.addTask("Study Alg");
        assertEquals(3,manager.sizeOfList());
    }

    @Test
    void completeTaskShouldMarkTaskAsCompleted() {
        manager.addTask("Study Java");
        manager.completeTask(1);
        assertTrue(manager.getTaskByNumber(1).isCompleted());
    }

    @Test
    void markTaskIncompleteShouldMarkTaskAsIncomplete() {
        manager.addTask("Study Java");
        manager.completeTask(1);
        manager.markTaskIncomplete(1);
        assertFalse(manager.getTaskByNumber(1).isCompleted());
    }

    @Test
    void deleteTaskShouldRemoveTaskFromList() {
        manager.addTask("Study Java");
        manager.deleteTask(1);
        assertFalse(manager.hasTask());
    }

    @Test
    void renameTaskShouldUpdateTaskName() {
        manager.addTask("Study Java");
        manager.renameTask(1,"Study CompSCI");
        assertEquals("Study CompSCI", manager.getTaskByNumber(1).getName());
    }

    @Test
    void setTaskPriorityShouldUpdatePriority() {
        manager.addTask("Study Java");
        manager.setTaskPriority(1,Priority.HIGH);
        assertEquals(Priority.HIGH,manager.getTaskByNumber(1).getPriority());
    }

    @Test
    void setTaskCategoryShouldUpdateCategory() {
        manager.addTask("Study Java");
        manager.setTaskCategory(1,Category.CHURCH);
        assertEquals(Category.CHURCH,manager.getTaskByNumber(1).getCategory());
    }

    @Test
    void searchTasksShouldReturnMatchingTasks() {
        manager.addTask("Study Java");
        assertEquals("Study Java",manager.searchTasks("Study").getFirst().getName());
    }

    @Test
    void searchTasksShouldReturnEmptyListWhenNoMatchExists() {
        manager.addTask("Study Java");
        assertTrue(manager.searchTasks("Learn").isEmpty());
    }

    @Test
    void filterByPriorityShouldReturnMatchingTasks() {
        manager.addTask("Study Java");

        assertEquals(Priority.MEDIUM,manager.filterByPriority(Priority.MEDIUM).getFirst().getPriority());

    }

    @Test
    void filterByCategoryShouldReturnMatchingTasks() {
        manager.addTask("Study Java");
        assertEquals(Category.OTHER,manager.filterByCategory(Category.OTHER).getFirst().getCategory());
    }

    @Test
    void filterByCompletionStatusShouldReturnMatchingTasks() {
        manager.addTask("Study Java");
        assertFalse(manager.filterByCompletionStatus(false).getFirst().isCompleted());
    }

    @Test
    void sortByPriorityShouldReturnTasksInPriorityOrder() {
        manager.addTask("Study Java");
        manager.addTask("Study Python");
        manager.addTask("Study Kotlin");

        manager.setTaskPriority(1,Priority.LOW);
        manager.setTaskPriority(3,Priority.HIGH);


        ArrayList<Task> sorted = manager.sortByPriority();

        assertEquals(Priority.HIGH, sorted.get(0).getPriority());
        assertEquals(Priority.MEDIUM, sorted.get(1).getPriority());
        assertEquals(Priority.LOW, sorted.get(2).getPriority());
    }

    @Test
    void sortByCompletionStatusShouldReturnTasksInCompletionOrder() {
        manager.addTask("Study Java");
        manager.addTask("Study Python");
        manager.addTask("Study Kotlin");

        manager.getTaskByNumber(1).markCompleted();

        ArrayList<Task> sorted = manager.sortByCompletionStatus();

        assertFalse(sorted.get(0).isCompleted());
        assertFalse(sorted.get(1).isCompleted());
        assertTrue(sorted.get(2).isCompleted());
    }

}