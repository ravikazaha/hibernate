package org.sakamainty.hibernate.controllers.visites;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.sakamainty.hibernate.models.Link;
import org.sakamainty.hibernate.models.medecins.Medecin;
import org.sakamainty.hibernate.models.medecins.MedecinResponse;
import org.sakamainty.hibernate.models.patients.Patient;
import org.sakamainty.hibernate.models.visites.Visiter;
import org.sakamainty.hibernate.services.MedecinService;
import org.sakamainty.hibernate.services.PatientService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class NewVisiteController {
    @FXML private ComboBox<Medecin> medecinCombobox;
    @FXML private ComboBox<Patient> patientCombobox;
    @FXML private DatePicker dateField;

    @FXML
    public void initialize() throws Exception {
        configurePatientCombobox();
        configureMedecinCombobox();

        dateField.setValue(LocalDate.now());

        loadData();
    }

    private void configureMedecinCombobox() {
        medecinCombobox.setCellFactory(new Callback<ListView<Medecin>, ListCell<Medecin>>() {
            @Override
            public ListCell<Medecin> call(ListView<Medecin> medecinListView) {
                return new ListCell<Medecin>() {
                    @Override
                    protected void updateItem(Medecin medecin, boolean b) {
                        super.updateItem(medecin, b);
                        setText(b || medecin == null ? "" : medecin.getNom());
                    }
                };
            }
        });

        medecinCombobox.setConverter(new StringConverter<Medecin>() {
            @Override
            public String toString(Medecin medecin) {
                return medecin != null ? medecin.getNom() : "";
            }

            @Override
            public Medecin fromString(String string) {
                return null; // Not needed for display-only
            }
        });
    }

    private void configurePatientCombobox() {
        patientCombobox.setCellFactory(new Callback<ListView<Patient>, ListCell<Patient>>() {
            @Override
            public ListCell<Patient> call(ListView<Patient> medecinListView) {
                return new ListCell<Patient>() {
                    @Override
                    protected void updateItem(Patient patient, boolean b) {
                        super.updateItem(patient, b);
                        setText(b || patient == null ? "" : patient.getNom());
                    }
                };
            }
        });

        patientCombobox.setConverter(new StringConverter<Patient>() {
            @Override
            public String toString(Patient patient) {
                return patient != null ? patient.getNom() : "";
            }

            @Override
            public Patient fromString(String s) {
                return null;
            }
        });
    }

    private void loadData() {
        try {
            List<Medecin> medecins = MedecinService.getInstance().getAllMedecins(0, 10).getEmbedded().getMedecins();
            if (medecins != null && !medecins.isEmpty()) {
                Platform.runLater(() -> medecinCombobox.getItems().setAll(medecins));
            } else {
                System.out.println("No medecins found or list is empty");
            }

            List<Patient> patients = PatientService.getInstance().getAllPatient(0, 10).getEmbeddedPatient().getPatients();
            if (patients != null && !patients.isEmpty()) {
                Platform.runLater(() -> patientCombobox.getItems().setAll(patients));
            } else {
                System.out.println("No patient found or list is empty");
            }
        } catch (Exception e) {
            System.err.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Visiter submitNewVisiteHandler() {
        LocalDate date = dateField.getValue();
        String medecinRef = medecinCombobox.getValue().getLinks().get("self").getHref();
        String patientRef = patientCombobox.getValue().getLinks().get("self").getHref();

        System.out.println(medecinRef);
        System.out.println(patientRef);

        String dateTime = date.atTime(LocalTime.now()).toString();

        System.out.println(dateTime);

        Visiter visiter = new Visiter();
        visiter.setDate(dateTime);
        visiter.setMedecin(medecinRef);
        visiter.setPatient(patientRef);

        System.out.println(visiter.getMedecin() + " " + visiter.getPatient());

        return visiter;
    }
}
