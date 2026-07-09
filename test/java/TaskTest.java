import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    Task task;
    @BeforeEach
    void setTask(){
        task=new Task("Study Java");
    }


    @Test
    void newTaskShouldStartIncomplete(){
        assertFalse(task.isCompleted());
    }

    @Test
    void markCompletedShouldCompleteTask(){
        task.markCompleted();
        assertTrue(task.isCompleted());
    }

    @Test
    void changingNameShouldUpdateName(){
        task.changeName("Study Alg");
        assertEquals("Study Alg",task.getName());
    }

    @Test
    void changingPriorityShouldUpdatePriority(){
        task.setPriority(Priority.HIGH);
        assertEquals(Priority.HIGH,task.getPriority());
    }

    @Test
    void changingCategoryShouldUpdateCategory(){
        task.setCategory(Category.CHURCH);
        assertEquals(Category.CHURCH,task.getCategory());
    }


}