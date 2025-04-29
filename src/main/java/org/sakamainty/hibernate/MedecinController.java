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

public class MedecinController {
    @FXML private TableView<Medecin> medecinTable;
    @FXML private TableColumn<Medecin, String> nomColumn;
    @FXML private TableColumn<Medecin, String> prenomColumn;
    @FXML private TableColumn<Medecin, String> gradeColumn;
    @FXML private ContextMenu listContextMenu;
    @FXML private Button newMedecinBtn;
    @FXML private VBox medecinContent;


    private ObservableList<Medecin> medecinList = FXCollections.observableArrayList(
            new Medecin("Charlot", "Vincent", "Cardiologue"),
            new Medecin("Mamiratra", "Vincentine", "Physiologue"),
            new Medecin("Max", "Ricardo", "Pediatrologue")
    );

    @FXML public void initialize() {
        listContextMenu = new ContextMenu();
        MenuItem updateMenuItem = new MenuItem("Modifier");
        MenuItem deleteMenuItem = new MenuItem("Suprimer");
        MenuItem viewMenuItem = new MenuItem("Voir");

        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Medecin supprimer avec succés");
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
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

        medecinTable.setItems(medecinList);

        medecinTable.setRowFactory(tv -> {
            TableRow<Medecin> row = new TableRow<>();

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

    @FXML public void showNewMedecinDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(medecinContent.getScene().getWindow());
        dialog.setTitle("Ajouter un nouvel medecin");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("new-medecin.fxml"));

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
