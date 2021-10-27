package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class that provides logic for the Add Product screen of the application.
 *
 * @author Erik Nilson
 */
public class AddProductController implements Initializable{

    Stage stage;
    Parent scene;

    /**
     * List of associated parts.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * The part search text field.
     */
    @FXML
    private TextField partSearchText;

    /**
     * The product name text field.
     */
    @FXML
    private TextField productNameText;

    /**
     * The product inventory text field.
     */
    @FXML
    private TextField productInvText;

    /**
     * The product price text field.
     */
    @FXML
    private TextField productPriceText;

    /*
     * The product min text field.
     */
    @FXML
    private TextField productMinText;

    /**
     * The product max text field.
     */
    @FXML
    private TextField productMaxText;

    /**
     * The parts table view.
     */
    @FXML
    private TableView<Part> partTable;

    /**
     * The part id column for the parts table.
     */
    @FXML
    private TableColumn<Part, Integer> partIdCol;

    /**
     * The part name column for the parts table.
     */
    @FXML
    private TableColumn<Part, String> partNameCol;

    /**
     * The part inventory column for the parts table.
     */
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelCol;

    /**
     * The part price column for the parts table.
     */
    @FXML
    private TableColumn<Part, Double> partPricePerUnitCol;

    /**
     * The associated parts table.
     */
    @FXML
    private TableView<Part> associatedPartTable;

    /**
     * The associated part id column for the parts table.
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartIdCol;

    /**
     * The associated part name column for the parts table.
     */
    @FXML
    private TableColumn<Part, String> associatedPartNameCol;

    /*
     * The associated part inventory column for the parts table.
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartInventoryLevelCol;

    /**
     * The associated part price column for the parts table.
     */
    @FXML
    private TableColumn<Part, Double> associatedPartPricePerUnitCol;

    /**
     * Adds a part to a product.
     *
     * @param event Add button clicked.
     */
    @FXML
    void onAddPart(ActionEvent event) {
        Part selectPart = partTable.getSelectionModel().getSelectedItem();

        if (selectPart == null) {
            displayAlert(1);
        }
        else {
            associatedParts.add(selectPart);
            associatedPartTable.setItems(associatedParts);
        }
    }

    /**
     * Cancels adding a product and loads the main screen controller.
     *
     * @param event Cancel button clicked.
     * @throws IOException FXMLLoader
     */
    @FXML
    void onCancelProduct(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        scene.setStyle(("-fx-font-family: 'serif';"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Removes an associated part from a product.
     *
     * Includes appropriate error messages when needed.
     *
     * @param event Remove associated parts button clicked.
     */
    @FXML
    void onRemoveAssociatedPart(ActionEvent event) {

        Part selectedAssociatedPart = associatedPartTable.getSelectionModel().getSelectedItem();

        if (selectedAssociatedPart == null)
            displayAlert(2);

        associatedParts.remove(selectedAssociatedPart);
        associatedPartTable.setItems(associatedParts);
    }

    /**
     * Saves data and loads the main screen controller.
     *
     * Includes appropriate error messages when needed.
     *
     * @param event Save button clicked.
     * @throws IOException FXMLLoader
     */
    @FXML
    void onSaveProduct(ActionEvent event) throws IOException {

        try {
            int id = 0;
            for (Product product : Inventory.getAllProducts()) {
                if (product.getId() > id)
                    id = (product.getId());
                id = ++id;
            }

            String name = productNameText.getText();
            int stock = Integer.parseInt(productInvText.getText());
            double price = Double.parseDouble(productPriceText.getText());
            int min = Integer.parseInt(productMinText.getText());
            int max = Integer.parseInt(productMaxText.getText());
            String companyName = null;

            if (name.isEmpty()) {
                displayAlert(1);
            } else if ((min < 0) || (min > max)) {
                displayAlert(3);
            } else if (!((min <= stock) && (max >= stock))) {
                displayAlert(4);
            } else {
                Product newProduct = new Product(id, name, price, stock, min, max, companyName);
                for (Part part: associatedParts){
                    newProduct.addAssociatedPart(part);
                }

                Inventory.addProduct(newProduct);

                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                scene.setStyle(("-fx-font-family: 'serif';"));
                stage.setScene(new Scene(scene));
                stage.show();
                }

        } catch (Exception e) {
            displayAlert(4);
        }
    }

    /**
     * Searches parts by name or ID in the add product controller.
     *
     * Includes appropriate error messages when needed.
     *
     * @param event Search button clicked.
     */
    @FXML
    public void onSearchPart(ActionEvent event) {

        try {
            int partId = Integer.parseInt(partSearchText.getText());
            Part foundPart = Inventory.lookupPart(partId);
            partTable.requestFocus();
            partTable.getSelectionModel().select(foundPart);

            if (foundPart == null)
                displayAlert(1);
            }
        catch (Exception e) {
            String searchString = partSearchText.getText();
            ObservableList<Part> foundPart = Inventory.lookupPart(searchString);
            partTable.setItems(foundPart);

            if (foundPart.size() == 0) {
                displayAlert(1);
            }
        }
    }

    /**
     * Various alert and error messages.
     *
     * @param alertType Message type selector.
     */
    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                DialogPane dialogPane1 = alert.getDialogPane();
                dialogPane1.setStyle("-fx-font-family: serif;");
                alert.setTitle("Information");
                alert.setHeaderText("Item not found.");
                alert.showAndWait();
                break;
            case 2:
                DialogPane dialogPane2 = alertError.getDialogPane();
                dialogPane2.setStyle("-fx-font-family: serif;");
                alertError.setTitle("Error");
                alertError.setHeaderText("Please select an item to delete.");
                alertError.showAndWait();
                break;
            case 3:
                DialogPane dialogPane3 = alertError.getDialogPane();
                dialogPane3.setStyle("-fx-font-family: serif;");
                alertError.setTitle("Error");
                alertError.setHeaderText("Min value must be greater than 0 and less than max value");
                alertError.showAndWait();
                break;
            case 4:
                DialogPane dialogPane4 = alertError.getDialogPane();
                dialogPane4.setStyle("-fx-font-family: serif;");
                alertError.setTitle("Error");
                alertError.setHeaderText("Inventory must be between min and max values");
                alertError.showAndWait();
                break;
            case 5:
                DialogPane dialogPane5 = alert.getDialogPane();
                dialogPane5.setStyle("-fx-font-family: serif;");
                alert.setTitle("Information");
                alert.setHeaderText("Please select an item to add.");
                alert.showAndWait();
                break;
        }
    }

    /**
     * Initializes the controller and populates the tables.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /**
         * Sets columns and rows with data for parts.
         */
        partTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        /**
         * Sets columns and rows with data for associated parts.
         */
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
