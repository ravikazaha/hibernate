package org.sakamainty.hibernate.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainController {
    @FXML
    private AnchorPane mainContent;

    @FXML
    private String currentPath;

    @FXML
    private Button medecinBtn;

    @FXML
    private Button patientBtn;

    @FXML
    private Button visiteBtn;

    private final String activeStyle = "-fx-background-color: #3498db; -fx-text-fill: white;";
    private final String defaultStyle = "";


    public void initialize() {
        medecinBtn.setStyle(activeStyle);
        this.showMedecin();
    }

    @FXML
    public void handleMedecin(ActionEvent e) {
        patientBtn.setStyle(defaultStyle);
        visiteBtn.setStyle(defaultStyle);

        Button clickedButton = (Button) e.getSource();
        clickedButton.setStyle(activeStyle);

        this.showMedecin();
    }

    @FXML
    public void handlePatient(ActionEvent e) {
        medecinBtn.setStyle(defaultStyle);
        visiteBtn.setStyle(defaultStyle);

        Button clickedButton = (Button) e.getSource();
        clickedButton.setStyle(activeStyle);

        showPatient();
    }

    @FXML
    public void handleVisite(ActionEvent e) {
        medecinBtn.setStyle(defaultStyle);
        patientBtn.setStyle(defaultStyle);

        Button clickedButton = (Button) e.getSource();
        clickedButton.setStyle(activeStyle);

        showVisite();
    }

    private void showMedecin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/sakamainty/hibernate/medecin-view.fxml"));
            Parent medecinView = loader.load();
            mainContent.getChildren().setAll(medecinView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPatient() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/sakamainty/hibernate/patient-view.fxml"));
            Parent patientView = loader.load();
            mainContent.getChildren().setAll(patientView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showVisite() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/sakamainty/hibernate/visite-view.fxml"));
            Parent visiteView = loader.load();
            mainContent.getChildren().setAll(visiteView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
