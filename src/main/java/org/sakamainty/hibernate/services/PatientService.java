package org.sakamainty.hibernate.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sakamainty.hibernate.models.patients.Patient;
import org.sakamainty.hibernate.models.patients.PatientResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class PatientService {
    private static final PatientService INSTANCE = new PatientService();
    private final HttpClient client;
    private final ObjectMapper mapper;
    private final String baseUrl = "http://localhost:8080/api/patients";

    private PatientService() {
        client = HttpClient.newHttpClient();
        mapper = new ObjectMapper();
    }

    public static PatientService getInstance() {return INSTANCE;}

    public PatientResponse getAllPatient(int page, int size) throws Exception {
        String url = baseUrl + "?page=" + page + "&size=" + size;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), PatientResponse.class);
        } else {
            throw new RuntimeException("Failed to fetch medecins: HTTP " + response.statusCode());
        }
    }

    public Patient getPatientById(Patient patient) throws Exception {
        String url = patient.getLinks().get("self").getHref();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Patient.class);
        } else {
            throw new RuntimeException("Failed to fetch patient " + ": HTTP " + response.statusCode());
        }
    }

    public Patient createPatient(Patient patient) throws Exception {
        String jsonPayload = mapper.writeValueAsString(patient);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (response.statusCode() == 201) {
            if (response.body() == null || response.body().trim().isEmpty()) {
                return patient;
            }
            return mapper.readValue(response.body(), Patient.class);
        } else {
            throw new RuntimeException("Failed to create patient: HTTP " + response.statusCode() + ", Body: " + response.body());
        }
    }

    public Patient updatePatient(Patient patient) throws Exception {
        String url = patient.getLinks().get("self").getHref();
        String jsonPayload = mapper.writeValueAsString(patient);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (response.statusCode() == 200 || response.statusCode() == 204) {
            if (response.body() != null && !response.body().trim().isEmpty()) {
                return mapper.readValue(response.body(), Patient.class);
            }
        } else {
            throw new RuntimeException("Failed to update patient " + ": HTTP " + response.statusCode() + ", Body: " + response.body());
        }
        return patient;
    }


    public void deletePatient(Patient patient) throws Exception {
        String url = patient.getLinks().get("self").getHref();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 204 && response.statusCode() != 200) {
            throw new RuntimeException("Failed to delete patient " + ": HTTP " + response.statusCode());
        }
    }

    public PatientResponse searchAny(String value) throws Exception {
        String url = baseUrl + "/search/any?q="+value;

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), PatientResponse.class);
        } else {
            throw new RuntimeException("Failed to fetch medecins: HTTP " + response.statusCode());
        }
    }
}
