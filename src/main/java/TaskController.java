import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    private TaskApiClient taskApiClient;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        taskApiClient = new TaskApiClient();

        try {

            List<ApiTask> apiTasks =
                    taskApiClient.getTasks();

            List<Task> loadedTasks = new ArrayList<>();

            for (ApiTask apiTask : apiTasks) {
                Task convertedTask = convertApiTask(apiTask);
                loadedTasks.add(convertedTask);
            }
            taskManager = new TaskManager(loadedTasks);

        } catch (IOException | InterruptedException e) {
            showError("""
                The task API could not be reached.
                Make sure the Spring Boot server is running.
                """);

            javafx.application.Platform.exit();
            return;
        }

        priorityBox.getItems().addAll(Priority.values());
        categoryBox.getItems().addAll(Category.values());

        priorityBox.setValue(Priority.MEDIUM);
        categoryBox.setValue(Category.OTHER);

        refreshTaskList();

        taskList.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldTask, selectedTask) -> {
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
    private void displayTasks(List<Task> tasksToDisplay) {
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
    private void addTask()  {
        String taskName = taskInput.getText().trim();
        Priority priority = priorityBox.getValue();
        Category category = categoryBox.getValue();

        if (taskName.isEmpty()) {
            showWarning("Task name cannot be empty.");
            return;
        }

        ApiTaskRequest apiTaskRequest = new ApiTaskRequest();
        apiTaskRequest.setName(taskName);
        apiTaskRequest.setCategory(category.name());
        apiTaskRequest.setPriority(priority.name());

        ApiTask createdApiTask;
        try {
            createdApiTask = taskApiClient.addTask(apiTaskRequest);
        } catch (IOException | InterruptedException e) {
            showError("Could not reach the task API.");
            return;
        }
        Task createdTask= convertApiTask(createdApiTask);

        taskManager.addTask(createdTask);
        refreshTaskList();
        clearTaskEditor();
    }

    @FXML
    private void deleteTask() {

        Task selectedTask = taskList.getSelectionModel().getSelectedItem();
        if(selectedTask==null){
            showWarning("No task selected.");
            return;
        }

        try {
            taskApiClient.deleteTask(selectedTask.getId());
        } catch (IOException | InterruptedException e) {
            showError("Could not reach the task API.");
            return;
        }
        taskManager.deleteTask(selectedTask);
        refreshTaskList();
        clearTaskEditor();
        }

    @FXML
    private void completeTask() {
        Task selectedTask = taskList.getSelectionModel().getSelectedItem();
        if(selectedTask==null){
            showWarning("No task selected.");
            return;
        }
        try {
            taskApiClient.completeTask(selectedTask.getId());
        } catch (IOException | InterruptedException e) {
            showError("Could not reach the task API.");
            return;
        }
        taskManager.completeTask(selectedTask);
        refreshTaskList();
    }

    @FXML
    private void markIncomplete(){
        Task selectedTask = taskList.getSelectionModel().getSelectedItem();
        if(selectedTask==null){
            showWarning("No task selected.");
            return;
        }
        try {
            taskApiClient.markTaskIncomplete(selectedTask.getId());
        } catch (IOException | InterruptedException e) {
            showError("Could not reach the task API.");
            return;
        }
        taskManager.markTaskIncomplete(selectedTask);
        refreshTaskList();
    }

    @FXML
    private void updateTaskDetails() {
        Task selectedTask = taskList.getSelectionModel().getSelectedItem();
        if(selectedTask==null){
            showWarning("No task selected.");
            return;
        }

         String newName = taskInput.getText().trim();
        if (newName.isEmpty()) {
            showWarning("Task name cannot be empty.");
            return;
        }
         Priority newPriority = priorityBox.getValue();
         Category newCategory = categoryBox.getValue();

         ApiTaskRequest apiTaskRequest = new ApiTaskRequest();
         apiTaskRequest.setName(newName);
         apiTaskRequest.setCategory(newCategory.name());
         apiTaskRequest.setPriority(newPriority.name());


        ApiTask updatedApiTask;

        try {
            updatedApiTask = taskApiClient.updateTask(
                    selectedTask.getId(),
                    apiTaskRequest
            );
        } catch (IOException | InterruptedException e) {
            showError("Could not reach the task API.");
            return;
        }
        selectedTask.changeName(updatedApiTask.getName());
        selectedTask.setPriority(Priority.valueOf(updatedApiTask.getPriority()));
        selectedTask.setCategory(Category.valueOf(updatedApiTask.getCategory()));
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

    private void clearTaskEditor() {
        taskInput.clear();
        priorityBox.setValue(Priority.MEDIUM);
        categoryBox.setValue(Category.OTHER);
        taskList.getSelectionModel().clearSelection();
    }

    private Task convertApiTask(ApiTask apiTask) {
        Task convertedTask = new Task(
                apiTask.getId().intValue(),
                apiTask.getName(),
                apiTask.isCompleted(),
                Priority.valueOf(apiTask.getPriority()),
                Category.valueOf(apiTask.getCategory())
        );
        return convertedTask;
    }
}