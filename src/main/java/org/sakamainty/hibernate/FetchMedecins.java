package org.sakamainty.hibernate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sakamainty.hibernate.models.Medecin;
import org.sakamainty.hibernate.models.MedecinResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class FetchMedecins {
    private static final FetchMedecins INSTANCE = new FetchMedecins();
    private final HttpClient client;
    private final ObjectMapper mapper;
    private final String baseUrl = "http://localhost:8080/api/medecins";

    private FetchMedecins() {
        this.client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        this.mapper = new ObjectMapper();
    }

    public static FetchMedecins getInstance() {
        return INSTANCE;
    }

    public MedecinResponse getAllMedecins(int page, int size) throws Exception {
        String url = baseUrl + "?page=" + page + "&size=" + size;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), MedecinResponse.class);
        } else {
            throw new RuntimeException("Failed to fetch medecins: HTTP " + response.statusCode());
        }
    }

    public Medecin getMedecinById(int id) throws Exception {
        String url = baseUrl + "/" + id;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Medecin.class);
        } else {
            throw new RuntimeException("Failed to fetch medecin with ID " + id + ": HTTP " + response.statusCode());
        }
    }

    public Medecin createMedecin(Medecin medecin) throws Exception {
        String jsonPayload = mapper.writeValueAsString(medecin);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (response.statusCode() == 201) {
            if (response.body() == null || response.body().trim().isEmpty()) {
                return medecin;
            }
            return mapper.readValue(response.body(), Medecin.class);
        } else {
            throw new RuntimeException("Failed to create medecin: HTTP " + response.statusCode() + ", Body: " + response.body());
        }
    }

    public Medecin updateMedecin(Medecin medecin) throws Exception {
        String url = medecin.getLinks().get("self").getHref();
        String jsonPayload = mapper.writeValueAsString(medecin);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (response.statusCode() == 200 || response.statusCode() == 204) {
            if (response.statusCode() == 200 && response.body() != null && !response.body().trim().isEmpty()) {
                return mapper.readValue(response.body(), Medecin.class);
            }
        } else {
            throw new RuntimeException("Failed to update medecin " + ": HTTP " + response.statusCode() + ", Body: " + response.body());
        }
        return medecin;
    }

    public void deleteMedecin(Medecin medecin) throws Exception {
        String url = medecin.getLinks().get("self").getHref();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 204 && response.statusCode() != 200) {
            throw new RuntimeException("Failed to delete medecin " + ": HTTP " + response.statusCode());
        }
    }
}