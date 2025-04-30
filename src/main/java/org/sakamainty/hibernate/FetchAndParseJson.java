package org.sakamainty.hibernate;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FetchAndParseJson {
    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts/1"))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                // Parse JSON using Jackson
                ObjectMapper mapper = new ObjectMapper();
                Post post = mapper.readValue(response.body(), Post.class);
                System.out.println("Post Title: " + post.getTitle());
            } else {
                System.out.println("Error: HTTP " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // POJO to map JSON
    public static class Post {
        private int id;
        private String title;
        private String body;
        private int userId;

        // Getters and setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getBody() { return body; }
        public void setBody(String body) { this.body = body; }
        public int getUserId() { return userId; }
        public void setUserId(int userId) { this.userId = userId; }
    }
}
