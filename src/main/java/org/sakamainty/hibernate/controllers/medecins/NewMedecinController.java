package org.sakamainty.hibernate.controllers.medecins;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.sakamainty.hibernate.models.medecins.Medecin;

public class NewMedecinController {
    @FXML private ChoiceBox<String> gradeChoiceBox;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;

    @FXML
    public void initialize() {
        gradeChoiceBox.getItems().addAll("Interne", "Resident", "Practicient Hospitalier", "Chef de Clinique", "Professeur", "Medecin Generaliste", "Specialiste", "Assistant Hospitalier", "Chef de Service", "Consultant", "Medecin Liberal", "Medecin Adjoint");
        gradeChoiceBox.setValue("Interne");
    }

    @FXML
    public Medecin submitFormHandler() {
        String nom = firstNameField.getText().trim();
        String prenom = lastNameField.getText().trim();
        String grade = gradeChoiceBox.getValue();

        Medecin medecin = new Medecin();
        medecin.setNom(nom);
        medecin.setPrenom(prenom);
        medecin.setGrade(grade);

        return medecin;
    }

    @FXML
    public Medecin submitUpdateFormHandler(Medecin medecin) {
        medecin.setNom(firstNameField.getText().trim());
        medecin.setPrenom(lastNameField.getText().trim());
        medecin.setGrade(gradeChoiceBox.getValue());

        return medecin;
    }

    @FXML public void setFormHandler(Medecin medecin) {
        firstNameField.setText(medecin.getNom());
        lastNameField.setText(medecin.getPrenom());
        gradeChoiceBox.setValue(medecin.getGrade());
    }
}
