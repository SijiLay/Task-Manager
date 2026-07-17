import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.util.ArrayList;

public class TaskManagerGUI extends Application {

    private TaskManager taskManager;
    private FileManager fileManager;
    private ListView<Task> taskList;
    private TextField taskInput;
    private TextField searchInput;
    private ComboBox<Priority> priorityBox;
    private ComboBox<Category> categoryBox;

    @Override
    public void start(Stage stage) {
        fileManager = new FileManager("task.txt");

        try {
            ArrayList<Task> loadedTasks = fileManager.loadTasks();
            taskManager = new TaskManager(loadedTasks);
        } catch (IOException e) {
            taskManager = new TaskManager();
            showError("Tasks could not be loaded");
        }

        Label title = new Label("Task Manager");
        taskInput = new TextField();
        taskInput.setPromptText("Enter task name");

        searchInput =new TextField();
        searchInput.setPromptText("Search Task");

        HBox mainActionRow = new HBox(10);
        HBox editActionRow = new HBox(10);
        HBox searchRow = new HBox(10);
        HBox filterRow = new HBox(10);
        HBox sortRow = new HBox(10);
        HBox optionRow = new HBox(10);

        priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll(Priority.values());
        priorityBox.setValue(Priority.MEDIUM);

        categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll(Category.values());
        categoryBox.setValue(Category.OTHER);

        Button addButton = new Button("Add Task");
        Button deleteButton=new Button("Delete Task");
        Button completeButton=new Button("Complete Task");
        Button incompleteButton=new Button("Mark Incomplete");
        Button renameButton =new Button("Rename Task");
        Button updateButton=new Button("Update Details");
        Button searchButton=new Button("Search Task");
        Button showAllTaskButton=new Button("Show All Task");
        Button filterPriorityButton=new Button("Filter by Priority");
        Button filterCategoryButton=new Button("Filter by Category");
        Button filterCompletedButton=new Button("Filter Completed");
        Button filterIncompleteButton=new Button("Filter Incomplete");
        Button sortPriorityButton = new Button("Sort by Priority");
        Button sortCompletionButton = new Button("Sort by Completion");

        addButton.setOnAction(event -> addTask());
        deleteButton.setOnAction(event -> deleteTask());
        completeButton.setOnAction(event -> completeTask());
        incompleteButton.setOnAction(event -> markIncomplete());
        renameButton.setOnAction(event -> renameTask());
        updateButton.setOnAction(event -> updateTaskDetails());
        searchButton.setOnAction(event -> searchTasks());
        showAllTaskButton.setOnAction(event -> showAllTasks());
        filterPriorityButton.setOnAction(event -> filterByPriority());
        filterCategoryButton.setOnAction(event -> filterByCategory());
        filterCompletedButton.setOnAction(event -> filterCompleted());
        filterIncompleteButton.setOnAction(event -> filterIncomplete());
        sortPriorityButton.setOnAction(event -> sortByPriority());
        sortCompletionButton.setOnAction(event -> sortByCompletion());

        taskList = new ListView<>();
        refreshTaskList();
        taskList.getSelectionModel().selectedItemProperty().addListener((observable, oldTask, selectedTask) -> {
            if (selectedTask != null) {
                taskInput.setText(selectedTask.getName());
                priorityBox.setValue(selectedTask.getPriority());
                categoryBox.setValue(selectedTask.getCategory());
            }
        });

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        mainActionRow.getChildren().addAll(addButton, deleteButton, completeButton, incompleteButton);

        editActionRow.getChildren().addAll(renameButton, updateButton);

        searchRow.getChildren().addAll(searchInput, searchButton, showAllTaskButton);

        filterRow.getChildren().addAll(
                filterPriorityButton,
                filterCategoryButton,
                filterCompletedButton,
                filterIncompleteButton
        );

        sortRow.getChildren().addAll(sortPriorityButton, sortCompletionButton);

        optionRow.getChildren().addAll(priorityBox, categoryBox);
        root.getChildren().addAll(
                title,
                taskInput,
                optionRow,
                mainActionRow,
                editActionRow,
                searchRow,
                filterRow,
                sortRow,
                taskList
        );
        Scene scene = new Scene(root, 500, 700);

        stage.setTitle("Task Manager GUI");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            try {
                fileManager.saveTasks(taskManager.getTasks());
            } catch (IOException e) {
                showError("File has an error");
            }
        });
    }


    private void refreshTaskList() {
        displayTasks(taskManager.getTasks());
    }

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
        }

        taskManager.addTask(taskName, priority, category);
        taskInput.clear();
        refreshTaskList();
    }

    private void deleteTask(){
        int taskNumber=getSelectedTaskNumber();

        if(taskNumber==-1){
            showWarning("No task selected");
            return;
        }
        else {
             taskManager.deleteTask(taskNumber);
            refreshTaskList();
        }
    }

    private void completeTask() {
         int taskNumber=getSelectedTaskNumber();
        if(taskNumber==-1){
            showWarning("No task selected");
        }
        else {
            taskManager.completeTask(taskNumber);
            refreshTaskList();
        }
    }

    private void markIncomplete(){
        int taskNumber = getSelectedTaskNumber();
        if(taskNumber==-1){
            showWarning("No task selected");
        }
        else {
            taskManager.markTaskIncomplete(taskNumber);
            refreshTaskList();
        }
    }

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
        }

        taskManager.renameTask(taskNumber,newName);
        taskInput.clear();
        refreshTaskList();
    }

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

    private void searchTasks(){
        String searchTerm=searchInput.getText().trim();
        if(searchTerm.isEmpty()){
            showWarning("Search term cannot be empty");
            return;
        }
        ArrayList<Task> searchTasklist= taskManager.searchTasks(searchTerm);
        displayTasks(searchTasklist);
    }

    private void showAllTasks(){
        searchInput.clear();
        refreshTaskList();
    }

    private void filterByPriority(){
        Priority selectedPriority  = priorityBox.getValue();
        ArrayList<Task> results= taskManager.filterByPriority(selectedPriority);

        displayTasks(results);
    }

    private void filterByCategory(){
        Category selectedCategory=categoryBox.getValue();

        ArrayList<Task> results=taskManager.filterByCategory(selectedCategory);
        displayTasks(results);
    }

    private void filterCompleted(){
        ArrayList<Task> results=taskManager.filterByCompletionStatus(true);
        displayTasks(results);
    }

    private void filterIncomplete(){
        ArrayList<Task> results=taskManager.filterByCompletionStatus(false);
        displayTasks(results);
    }

    private void sortByPriority(){
        ArrayList<Task> results= taskManager.sortByPriority();
        displayTasks(results);
    }

    private void sortByCompletion(){
        ArrayList<Task> results=taskManager.sortByCompletionStatus();
        displayTasks(results);
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
        taskList.getItems().clear();

        for (Task task : tasksToDisplay) {
            taskList.getItems().add(task);
        }
    }

    private int getSelectedTaskNumber(){
        Task selectedTask=taskList.getSelectionModel().getSelectedItem();
        if(selectedTask==null){
            return -1;
        }
        return taskManager.getTasks().indexOf(selectedTask)+1;
    }

    public static void main(String[] args) {
        launch(args);
    }
}