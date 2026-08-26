import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class TaskApiClient {


    private final ObjectMapper objectMapper;


    private final HttpClient httpClient;

    public TaskApiClient() {
        this.httpClient =HttpClient.newHttpClient() ;
        this.objectMapper = new ObjectMapper();
    }

    //This method gets tasks from API, converts from JSON to Task, and returns them
    public List<ApiTask> getTasks() throws IOException, InterruptedException {
        String endPoint = "http://localhost:8080/tasks"; // where client is calling

        HttpRequest httpRequest = HttpRequest.newBuilder() //creates HTTP request
                .uri(URI.create(endPoint)) //sending to the address(endPoint)
                .GET()
                .build();

        HttpResponse<String> httpResponse = //send the request, and return the API response body as a String
                httpClient.send(
                        httpRequest,
                        HttpResponse.BodyHandlers.ofString()
                );

        String body = httpResponse.body(); // saves the response

        TypeReference<List<ApiTask>> typeReference =
                new TypeReference<List<ApiTask>>() {};

        List<ApiTask> tasks = objectMapper.readValue(body, typeReference); //reads JSON and turns into a Java objects

        return tasks;
    }

    public ApiTask addTask(ApiTaskRequest apiTaskRequest) throws IOException, InterruptedException {
        String endPoint = "http://localhost:8080/tasks";

        String jsonBody = objectMapper.writeValueAsString(apiTaskRequest); //turns Java Object into JSON

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(endPoint))
                .header("Content-Type", "application/json") //Tells server its sending a JSON body since we are adding new data to database
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        String body = httpResponse.body();

        ApiTask apiTask = objectMapper.readValue(body, ApiTask.class);
        return apiTask;
    }

    public ApiTask deleteTask(int id) throws IOException, InterruptedException {
        String endPoint = "http://localhost:8080/tasks/" + id;

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(endPoint))
                .DELETE()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        String body = httpResponse.body();
        ApiTask apiTask = objectMapper.readValue(body, ApiTask.class);
        return apiTask;
    }

    public ApiTask completeTask(int id) throws IOException, InterruptedException {
        String endPoint = "http://localhost:8080/tasks/" + id+"/complete";

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(endPoint))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        String body = httpResponse.body();
        ApiTask apiTask = objectMapper.readValue(body, ApiTask.class);
        return apiTask;
    }

    public ApiTask markTaskIncomplete(int id) throws IOException, InterruptedException {
        String endPoint = "http://localhost:8080/tasks/" +id+"/incomplete";

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(endPoint))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        String body = httpResponse.body();
        ApiTask apiTask = objectMapper.readValue(body, ApiTask.class);
        return apiTask;
    }

    public ApiTask updateTask(int id, ApiTaskRequest request) throws IOException, InterruptedException {
        String endPoint = "http://localhost:8080/tasks/" + id;

        String jsonBody = objectMapper.writeValueAsString(request);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(endPoint))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        String body = httpResponse.body();
        ApiTask apiTask = objectMapper.readValue(body, ApiTask.class);
        return apiTask;
    }
}
