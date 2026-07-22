import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.util.Objects;

public class TaskManagerApp extends Application  {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("task-view.fxml")));
        Parent root = loader.load();


        Scene scene = new Scene(root, 500, 700);

        stage.setTitle("Task Manager GUI");
        stage.setScene(scene);

        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

