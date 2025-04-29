package org.sakamainty.hibernate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Optional;

public class PatientController {
    @FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, String> nomColumn;
    @FXML private TableColumn<Patient, String> prenomColumn;
    @FXML private TableColumn<Patient, String> sexeColumn;
    @FXML private TableColumn<Patient, String> adresseColumn;
    @FXML private ContextMenu listContextMenu;
    @FXML private Button newPatientBtn;
    @FXML private VBox patientContent;


    private ObservableList<Patient> patientList = FXCollections.observableArrayList(
            new Patient("Charlot", "Vincent", "Homme", "Vohipeno"),
            new Patient("Mamiratra", "Vincentine", "Femme", "Vohipeno"),
            new Patient("Max", "Ricardo", "Homme", "Vohipeno")
    );

    @FXML public void initialize() {
        listContextMenu = new ContextMenu();
        MenuItem updateMenuItem = new MenuItem("Modifier");
        MenuItem deleteMenuItem = new MenuItem("Suprimer");
        MenuItem viewMenuItem = new MenuItem("Voir");

        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Patient supprimer avec succés");
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
                System.out.println("Update Medecin");
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

    @FXML public void showNewPatientDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(patientContent.getScene().getWindow());
        dialog.setTitle("Ajouter un nouvel patient");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("new-patient-view.fxml"));

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
            System.out.println("Okay button pressed");
        }
    }
}
