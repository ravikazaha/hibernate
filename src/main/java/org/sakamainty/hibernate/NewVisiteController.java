package org.sakamainty.hibernate;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

public class NewVisiteController {
    @FXML private ComboBox<String> medecinCombobox;
    @FXML private ComboBox<String> patientCombobox;

    @FXML
    public void initialize() {
        medecinCombobox.getItems().addAll("Charlot", "Mamiratra", "Max");
        medecinCombobox.setValue("Charlot");
        patientCombobox.getItems().addAll("Charlot", "Mamiratra", "Max");
        patientCombobox.setValue("Charlot");
    }
}
