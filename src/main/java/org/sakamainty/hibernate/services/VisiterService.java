package org.sakamainty.hibernate.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sakamainty.hibernate.models.medecins.Medecin;
import org.sakamainty.hibernate.models.patients.Patient;
import org.sakamainty.hibernate.models.visites.Visiter;
import org.sakamainty.hibernate.models.visites.VisiterResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class VisiterService {
    private static final VisiterService INSTANCE = new VisiterService();
    private final HttpClient client;
    private final ObjectMapper mapper;
    private final String baseUrl = "http://localhost:8080/api/visites";

    private VisiterService() {
        client = HttpClient.newHttpClient();
        mapper = new ObjectMapper();
    }

    public static VisiterService getInstance() {return INSTANCE;}

    public VisiterResponse getAllVisites(int page, int size) throws Exception {
        String url = baseUrl + "?page=" + page + "&size=" + size;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), VisiterResponse.class);
        } else {
            throw new RuntimeException("Failed to fetch visites: HTTP " + response.statusCode());
        }
    }

    public Patient getVisiteById(Visiter visiter) throws Exception {
        String url = visiter.getLinks().get("self").getHref();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Patient.class);
        } else {
            throw new RuntimeException("Failed to fetch visite " + ": HTTP " + response.statusCode());
        }
    }

    public Medecin getMedecinByVisite(Visiter visiter) throws Exception {
        String url = visiter.getLinks().get("medecin").getHref();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Medecin.class);
        } else {
            throw new RuntimeException("Failed to fetch medecin " + ": HTTP " + response.statusCode());
        }
    }

    public Patient getPatientByVisite(Visiter visiter) throws Exception {
        String url = visiter.getLinks().get("patient").getHref();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Patient.class);
        } else {
            throw new RuntimeException("Failed to fetch medecin " + ": HTTP " + response.statusCode());
        }
    }

    public Visiter createVisiter(Visiter visiter) throws Exception {

        String jsonPayload = mapper.writeValueAsString(visiter);
        System.out.println(jsonPayload);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (response.statusCode() == 201) {
            if (response.body() == null || response.body().trim().isEmpty()) {
                return visiter;
            }
            return mapper.readValue(response.body(), Visiter.class);
        } else {
            throw new RuntimeException("Failed to create visite: HTTP " + response.statusCode() + ", Body: " + response.body());
        }
    }

    public Visiter updateVisite(Visiter visiter) throws Exception {
        String url = visiter.getLinks().get("self").getHref();
        String jsonPayload = mapper.writeValueAsString(visiter);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (response.statusCode() == 200 || response.statusCode() == 204) {
            if (response.body() != null && !response.body().trim().isEmpty()) {
                return mapper.readValue(response.body(), Visiter.class);
            }
        } else {
            throw new RuntimeException("Failed to update patient " + ": HTTP " + response.statusCode() + ", Body: " + response.body());
        }
        return visiter;
    }


    public void deleteVisiter(Visiter visiter) throws Exception {
        String url = visiter.getLinks().get("self").getHref();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 204 && response.statusCode() != 200) {
            throw new RuntimeException("Failed to delete visiter " + ": HTTP " + response.statusCode());
        }
    }
}
