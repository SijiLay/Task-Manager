package com.codewithsiji.taskmanagerapi.task;


import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
// makes and sends HTTP request
public class TaskApiClient {
    public static void main(String[] args) throws Exception {
        TaskApiClient client = new TaskApiClient();

        List<Task> tasks = client.getTasks();

        for (Task task : tasks) {
            System.out.println(task.getName());
        }
    }

    private final ObjectMapper objectMapper;


    private final HttpClient httpClient;

     public TaskApiClient() {
        this.httpClient =HttpClient.newHttpClient() ;
         this.objectMapper = new ObjectMapper();
     }

    //This method gets tasks from API, converts from JSON to Task, and returns them
    public List<Task> getTasks() throws IOException, InterruptedException {
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

        TypeReference<List<Task>> typeReference =
                new TypeReference<List<Task>>() {};

        List<Task> tasks = objectMapper.readValue(body, typeReference); //reads JSON and turns into a Java objects

        return tasks;
    }

}
