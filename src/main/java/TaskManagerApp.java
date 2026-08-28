import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.nio.file.Files;
import java.nio.file.Path;

public class TaskManagerApp extends Application  {

    private Process apiProcess;

    @Override
    public void start(Stage stage) throws Exception {

        startApi();
        waitForApi();

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("task-view.fxml")));
        Parent root = loader.load();


        Scene scene = new Scene(root, 700, 700);

        stage.setTitle("Task Manager GUI");
        stage.setScene(scene);

        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

    private void startApi() throws Exception {

        Path apiJar = Path.of(
                "taskmanager-api",
                "target",
                "taskmanager-api-0.0.1-SNAPSHOT.jar"
        );

        // If we're running the packaged application,
        // the API JAR will be beside the desktop JAR inside the app folder.
        if (!Files.exists(apiJar)) {
            Path appDirectory = Path.of(
                    TaskManagerApp.class
                            .getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .toURI()
            ).getParent();

            apiJar = appDirectory.resolve(
                    "taskmanager-api-0.0.1-SNAPSHOT.jar"
            );
        }

        apiJar = apiJar.toAbsolutePath().normalize();

        Path javaExecutable = Path.of(
                System.getProperty("java.home"),
                "bin",
                "java.exe"
        );
        Path appDataDirectory = Path.of(
                System.getenv("LOCALAPPDATA"),
                "TaskManager"
        );

        Files.createDirectories(appDataDirectory);
        ProcessBuilder processBuilder = new ProcessBuilder(
                javaExecutable.toString(),
                "-jar",
                apiJar.toString()
        );
        processBuilder.directory(appDataDirectory.toFile());
        processBuilder.inheritIO();

        apiProcess = processBuilder.start();
    }

    @Override
    public void stop(){
        if(apiProcess != null && apiProcess.isAlive()){
            apiProcess.destroy();
        }
    }

    private void waitForApi() throws InterruptedException {
        int attempts = 0;

        while (attempts < 60) {
            try {
                HttpClient client = HttpClient.newHttpClient();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/tasks"))
                        .GET()
                        .build();

                HttpResponse<String> response =
                        client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    return;
                }

            } catch (Exception e) {
                // API is probably still starting
            }

            Thread.sleep(500);
            attempts++;
        }

        throw new RuntimeException("API failed to start.");
    }

}

