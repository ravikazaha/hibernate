package org.sakamainty.hibernate.controllers.patients;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import org.sakamainty.hibernate.models.medecins.Medecin;
import org.sakamainty.hibernate.models.patients.Patient;
import org.sakamainty.hibernate.services.PatientService;

import java.io.IOException;
import java.util.Optional;

public class PatientController {
    @FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, String> nomColumn;
    @FXML private TableColumn<Patient, String> prenomColumn;
    @FXML private TableColumn<Patient, String> sexeColumn;
    @FXML private TableColumn<Patient, String> adresseColumn;
    @FXML private TextField searchAnyPatient;
    @FXML private ContextMenu listContextMenu;
    @FXML private Button newPatientBtn;
    @FXML private VBox patientContent;


    private final ObservableList<Patient> patientList = FXCollections.observableArrayList(
            PatientService.getInstance().getAllPatient(0, 15).getEmbeddedPatient().getPatients()
    );

    public PatientController() throws Exception {
    }

    @FXML
    public void handleSearchAnyPatient(KeyEvent keyEvent) throws Exception {
        patientList.setAll(PatientService.getInstance().searchAny(searchAnyPatient.getText().trim()).getEmbeddedPatient().getPatients());
    }

    @FXML public void initialize() {
        listContextMenu = new ContextMenu();
        MenuItem updateMenuItem = new MenuItem("Modifier");
        MenuItem deleteMenuItem = new MenuItem("Suprimer");
        MenuItem viewMenuItem = new MenuItem("Voir");

        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Patient patient = patientTable.getSelectionModel().getSelectedItem();
                    PatientService.getInstance().deletePatient(patient);
                    patientList.remove(patient);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        viewMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("voir details");
            }
        });

        updateMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showUpdatePatientDialog(patientTable.getSelectionModel().getSelectedItem());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        listContextMenu.getItems().addAll(viewMenuItem, updateMenuItem, deleteMenuItem);

        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        sexeColumn.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));

        patientTable.setItems(patientList);

        patientTable.setRowFactory(tv -> {
            TableRow<Patient> row = new TableRow<>();

            row.emptyProperty().addListener((obs, wasEmpty, isNotEmpty) -> {
                if (!isNotEmpty) {
                    row.setContextMenu(listContextMenu);
                } else {
                    row.setContextMenu(null);
                }
            });

            return row;
        });
    }

    @FXML public void showNewPatientDialog() throws Exception {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(patientContent.getScene().getWindow());
        dialog.setTitle("Ajouter un nouvel patient");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/sakamainty/hibernate/new-patient-view.fxml"));

        try {
            DialogPane dialogPane = fxmlLoader.load();
            dialog.setDialogPane(dialogPane);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            NewPatientController newPatientController = fxmlLoader.getController();
            Patient patient = newPatientController.submitNewPatientFormHandler();

            Patient newPatient = PatientService.getInstance().createPatient(patient);
            patientList.add(newPatient);
        }
    }

    @FXML public void showUpdatePatientDialog(Patient patient) throws Exception {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(patientContent.getScene().getWindow());
        dialog.setTitle("Mettre à jour patient");
        dialog.setHeaderText("Mettre à jour le patient");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/sakamainty/hibernate/new-patient-view.fxml"));
        NewPatientController newPatientController;

        try {
            DialogPane dialogPane = fxmlLoader.load();
            newPatientController = fxmlLoader.getController();
            newPatientController.setFormHandler(patient);
            dialog.setDialogPane(dialogPane);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Patient updatePatient = newPatientController.submitUpdatePatientFormHandler(patient);

            Patient updatedPatient = PatientService.getInstance().updatePatient(patient);

            int index = patientList.indexOf(updatePatient);

            if (index != -1) {
                patientList.set(index, updatedPatient);
            } else {
                System.out.println("Patient not found in the list.");
            }
        }
    }
}
