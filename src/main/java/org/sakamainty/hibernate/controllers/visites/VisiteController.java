package org.sakamainty.hibernate.controllers.visites;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.sakamainty.hibernate.Visite;
import org.sakamainty.hibernate.models.medecins.Medecin;
import org.sakamainty.hibernate.models.medecins.MedecinResponse;
import org.sakamainty.hibernate.models.patients.Patient;
import org.sakamainty.hibernate.models.visites.Visiter;
import org.sakamainty.hibernate.services.MedecinService;
import org.sakamainty.hibernate.services.VisiterService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class VisiteController {
    @FXML
    private TableView<Visiter> visiteTable;
    @FXML private TableColumn<Visiter, String> medecinColumn;
    @FXML private TableColumn<Visiter, String> patientColumn;
    @FXML private TableColumn<Visiter, String> dateColumn;
    @FXML private ContextMenu listContextMenu;
    @FXML private Button newVisiteBtn;
    @FXML private VBox visiteContent;

    private ObservableList<Visiter> visiteList = FXCollections.observableArrayList(
            VisiterService.getInstance().getAllVisites(0, 10).getEmbeddedVisiter().getVisiters()
    );

    public VisiteController() throws Exception {
    }

    @FXML
    public void initialize() {
        listContextMenu = new ContextMenu();
        MenuItem updateMenuItem = new MenuItem("Modifier");
        MenuItem deleteMenuItem = new MenuItem("Suprimer");
        MenuItem viewMenuItem = new MenuItem("Voir");

        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Visiter visiter = visiteTable.getSelectionModel().getSelectedItem();
                try {
                    VisiterService.getInstance().deleteVisiter(visiter);
                    visiteList.remove(visiter);
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
                System.out.println("Update Medecin");
            }
        });

        listContextMenu.getItems().addAll(viewMenuItem, updateMenuItem, deleteMenuItem);

        medecinColumn.setCellValueFactory(cellData -> {
            Visiter visiter = cellData.getValue();
            try {
                Medecin medecin = VisiterService.getInstance().getMedecinByVisite(visiter);
                return new SimpleStringProperty(medecin.getNom());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } );

        patientColumn.setCellValueFactory(cellData -> {
            Visiter visiter = cellData.getValue();
            try {
                Patient patient = VisiterService.getInstance().getPatientByVisite(visiter);
                return new SimpleStringProperty(patient.getNom());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } );
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        visiteTable.setItems(visiteList);

        visiteTable.setRowFactory(tv -> {
            TableRow<Visiter> row = new TableRow<>();

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

    @FXML public void showNewVisiteDialog() throws Exception {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(visiteContent.getScene().getWindow());
        dialog.setTitle("Ajouter un nouvel visite");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/sakamainty/hibernate/new-visite-view.fxml"));

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
            NewVisiteController newVisiteController = fxmlLoader.getController();

            Visiter visiter = newVisiteController.submitNewVisiteHandler();

            Visiter newVisiter = VisiterService.getInstance().createVisiter(visiter);
            visiteList.add(newVisiter);
        }
    }

    @FXML public void showUpdateVisiteDialog() throws Exception {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(visiteContent.getScene().getWindow());
        dialog.setTitle("Mettre à jour visite");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/sakamainty/hibernate/new-visite-view.fxml"));

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
            NewVisiteController newVisiteController = fxmlLoader.getController();

            Visiter visiter = newVisiteController.submitNewVisiteHandler();

            Visiter newVisiter = VisiterService.getInstance().createVisiter(visiter);
            visiteList.add(newVisiter);
        }
    }
}
