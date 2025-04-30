package org.sakamainty.hibernate.lib;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class FetchApiAsync {
    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/medecins?page=0&size=10")).GET().build();

        CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        responseFuture.thenAccept(response -> {
            if (response.statusCode() == 200) {
                System.out.println("Reponse: " + response.body());
            } else {
                System.out.println("Error: HTTP " + response.statusCode());
            }
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });

        responseFuture.join();
    }
}
