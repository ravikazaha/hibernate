package org.sakamainty.hibernate;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class NewMedecinController {
    public ChoiceBox sexeChoiceBox;
    @FXML private ChoiceBox<String> gradeChoiceBox;

    @FXML
    public void initialize() {
        gradeChoiceBox.getItems().addAll("Cardiologue", "Physiologue", "Pédiatre", "Radiologue");
        gradeChoiceBox.setValue("Cardiologue");
    }
}
