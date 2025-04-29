package org.sakamainty.hibernate;

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

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class VisiteController {
    @FXML
    private TableView<Visite> visiteTable;
    @FXML private TableColumn<Visite, String> medecinColumn;
    @FXML private TableColumn<Visite, String> patientColumn;
    @FXML private TableColumn<Visite, LocalDate> dateColumn;
    @FXML private ContextMenu listContextMenu;
    @FXML private Button newVisiteBtn;
    @FXML private VBox visiteContent;

    private ObservableList<Visite> visiteList = FXCollections.observableArrayList(
            new Visite(new Patient("Charlot", "Vincent", "Homme", "Vohipeno"),new Medecin("Charlot", "Vincent", "Cardiologue"), LocalDate.now()),
            new Visite(new Patient("Mamiratra", "Vincentine", "Femme", "Vohipeno"), new Medecin("Mamiratra", "Vincentine", "Physiologue"), LocalDate.now()),
            new Visite(new Patient("Max", "Ricardo", "Homme", "Vohipeno"),new Medecin("Max", "Ricardo", "Pediatrologue"), LocalDate.now())
    );

    @FXML
    public void initialize() {
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

        medecinColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedecin().getNom()));
        patientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getNom()));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        visiteTable.setItems(visiteList);

        visiteTable.setRowFactory(tv -> {
            TableRow<Visite> row = new TableRow<>();

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

    @FXML public void showNewVisiteDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(visiteContent.getScene().getWindow());
        dialog.setTitle("Ajouter un nouvel visite");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("new-visite-view.fxml"));

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
