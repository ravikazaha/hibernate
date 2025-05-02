package org.sakamainty.hibernate.controllers.patients;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import org.sakamainty.hibernate.models.patients.Patient;

import java.util.Objects;

public class NewPatientController {
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private ToggleGroup sexeToggleGroup;
    @FXML private RadioButton hommeRadioButton = new RadioButton("Homme");
    @FXML private RadioButton femmeRadioButton = new RadioButton("Femme");
    @FXML private TextField adresseField;

    public void initialize() {
        hommeRadioButton.setToggleGroup(sexeToggleGroup);
        hommeRadioButton.setSelected(true);
        femmeRadioButton.setToggleGroup(sexeToggleGroup);
    }

    @FXML
    public Patient submitNewPatientFormHandler() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String sexe = selectedSexe();
        String adresse = adresseField.getText();

        System.out.println(sexeToggleGroup.getSelectedToggle().getProperties());

        Patient patient = new Patient();
        patient.setNom(firstName);
        patient.setPrenom(lastName);
        patient.setSexe(sexe);
        patient.setAdresse(adresse);

        return patient;
    }

    @FXML Patient submitUpdatePatientFormHandler(Patient patient) {
        patient.setNom(firstNameField.getText());
        patient.setPrenom(lastNameField.getText());
        patient.setAdresse(adresseField.getText());
        patient.setSexe(selectedSexe());

        return patient;
    }

    @FXML public void setFormHandler(Patient patient) {
        firstNameField.setText(patient.getNom());
        lastNameField.setText(patient.getPrenom());
        String sexe = patient.getSexe();

        if (Objects.equals(sexe, "H") || Objects.equals(sexe, "Homme")) {
            hommeRadioButton.setSelected(true);
        } else {
            femmeRadioButton.setSelected(true);
        }
        adresseField.setText(patient.getAdresse());
    }

    private String selectedSexe() {
        Toggle selectedToggle = sexeToggleGroup.getSelectedToggle();
        if (selectedToggle != null) {
            RadioButton selectedRadioButton = (RadioButton) selectedToggle;
            String text = selectedRadioButton.getText();
            return text.equals("Homme") ? "H" : "F";
        }
        return null;
    }
}
