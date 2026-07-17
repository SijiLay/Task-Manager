import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TaskController implements Initializable {

    @FXML
    private TextField taskInput, searchInput;

    @FXML
    private ComboBox<Priority> priorityBox;

    @FXML
    private ComboBox<Category> categoryBox;

    @FXML
    private ListView<Task> taskList;


    private TaskManager taskManager;
    private FileManager fileManager;

    private boolean saveEnabled = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fileManager=new FileManager("task.txt");
        try {
            ArrayList<Task> loadedTasks = fileManager.loadTasks();
            taskManager=new TaskManager(loadedTasks);
        } catch (IOException e) {
            taskManager = new TaskManager();
            saveEnabled = false;
            showError("Tasks could not be loaded. Saving has been disabled to protect your data.");
        }

        priorityBox.getItems().addAll(Priority.values());
        categoryBox.getItems().addAll(Category.values());
        priorityBox.setValue(Priority.MEDIUM);
        categoryBox.setValue(Category.OTHER);

        refreshTaskList();

        taskList.getSelectionModel().selectedItemProperty().addListener((observable, oldTask, selectedTask) -> {
            if (selectedTask != null) {
                taskInput.setText(selectedTask.getName());
                priorityBox.setValue(selectedTask.getPriority());
                categoryBox.setValue(selectedTask.getCategory());
            }
        });
        taskList.setPlaceholder(new Label("No tasks found."));
    }
    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showError(String message){
        Alert alert =new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void displayTasks(ArrayList<Task> tasksToDisplay) {
        taskList.getItems().setAll(tasksToDisplay);
    }
    private void refreshTaskList() {
        displayTasks(taskManager.getTasks());
    }
    private int getSelectedTaskNumber(){
        Task selectedTask=taskList.getSelectionModel().getSelectedItem();
        if(selectedTask==null){
            return -1;
        }
        return taskManager.getTasks().indexOf(selectedTask)+1;
    }


    @FXML
    private void addTask() {
        String taskName = taskInput.getText().trim();
        Priority priority = priorityBox.getValue();
        Category category = categoryBox.getValue();

        if (taskName.isEmpty()) {
            showWarning("Task name cannot be empty.");
            return;
        }
        if(taskName.contains(",")){
            showWarning("Task name cannot contain commas.");
            return;
        }

        taskManager.addTask(taskName, priority, category);
        refreshTaskList();
    }

    @FXML
    private void deleteTask(){
        int taskNumber=getSelectedTaskNumber();

        if(taskNumber==-1){
            showWarning("No task selected");
            return;
        }

        taskManager.deleteTask(taskNumber);
        refreshTaskList();
        clearTaskEditor();
        }

    @FXML
    private void completeTask() {
        int taskNumber=getSelectedTaskNumber();
        if(taskNumber==-1){
            showWarning("No task selected");
            return;
        }

        taskManager.completeTask(taskNumber);
        refreshTaskList();
    }

    @FXML
    private void markIncomplete(){
        int taskNumber = getSelectedTaskNumber();
        if(taskNumber==-1){
            showWarning("No task selected");
            return;
        }
            taskManager.markTaskIncomplete(taskNumber);
            refreshTaskList();
    }

    @FXML
    private void renameTask(){
        int taskNumber=getSelectedTaskNumber();
        String newName=taskInput.getText().trim();

        if(taskNumber==-1){
            showWarning("Please select a task to rename");
            return;
        }
        if(newName.isEmpty()){
            showWarning("New task name cannot be empty");
            return;
        }
        if(newName.contains(",")){
            showWarning("Task name cannot contain commas.");
            return;
        }

        taskManager.renameTask(taskNumber,newName);
        refreshTaskList();
        clearTaskEditor();
    }

    @FXML
    private void updateTaskDetails(){
        int taskNumber=getSelectedTaskNumber();
        Priority selectedPriority=priorityBox.getValue();
        Category selectedCategory=categoryBox.getValue();

        if(taskNumber==-1){
            showWarning("Please select a task to update");
            return;
        }
        taskManager.setTaskPriority(taskNumber,selectedPriority);
        taskManager.setTaskCategory(taskNumber,selectedCategory);
        refreshTaskList();
    }

    @FXML
    private void searchTasks(){
        String searchTerm=searchInput.getText().trim();
        if(searchTerm.isEmpty()){
            showWarning("Search term cannot be empty");
            return;
        }
        ArrayList<Task> searchResults= taskManager.searchTasks(searchTerm);
        displayTasks(searchResults);
    }

    @FXML
    private void showAllTasks(){
        searchInput.clear();
        refreshTaskList();
    }

    @FXML
    private void filterByPriority(){
        Priority selectedPriority  = priorityBox.getValue();
        ArrayList<Task> results= taskManager.filterByPriority(selectedPriority);

        displayTasks(results);
    }

    @FXML
    private void filterByCategory(){
        Category selectedCategory=categoryBox.getValue();

        ArrayList<Task> results=taskManager.filterByCategory(selectedCategory);
        displayTasks(results);
    }

    @FXML
    private void filterCompleted(){
        ArrayList<Task> results=taskManager.filterByCompletionStatus(true);
        displayTasks(results);
    }

    @FXML
    private void filterIncomplete(){
        ArrayList<Task> results=taskManager.filterByCompletionStatus(false);
        displayTasks(results);
    }

    @FXML
    private void sortByPriority(){
        ArrayList<Task> results= taskManager.sortByPriority();
        displayTasks(results);
    }

    @FXML
    private void sortByCompletion(){
        ArrayList<Task> results=taskManager.sortByCompletionStatus();
        displayTasks(results);
    }

    public void saveTasks() {
        if (!saveEnabled) {
            return;
        }

        try {
            fileManager.saveTasks(taskManager.getTasks());
        } catch (IOException e) {
            showError("Tasks could not be saved. Your latest changes may be lost.");
        }
    }
    private void clearTaskEditor() {
        taskInput.clear();
        priorityBox.setValue(Priority.MEDIUM);
        categoryBox.setValue(Category.OTHER);
        taskList.getSelectionModel().clearSelection();
    }
}