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
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class that provides logic for the Modify Product screen of the application.
 *
 * @author Erik Nilson
 */
public class ModifyProductController implements Initializable{

    Stage stage;
    Parent scene;

    /**
     * List of original parts.
     */
    private ObservableList<Part> originalParts = FXCollections.observableArrayList();

    /**
     * List of associated parts.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * The part table view.
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
     * The associated part table view.
     */
    @FXML
    private TableView<Part> associatedPartTable;

    /**
     * The part id column for the associated parts table.
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartIdCol;

    /**
     * The part name column for the associated parts table.
     */
    @FXML
    private TableColumn<Part, String> associatedPartNameCol;

    /**
     * The part inventory column for the associated parts table.
     */
    @FXML
    private TableColumn<Part, Integer> associatedInventoryLevelCol;

    /**
     * The part price column for the associated parts table.
     */
    @FXML
    private TableColumn<Part, Double> associatedPricePerUnitCol;

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
     * The product id text field.
     */
    @FXML
    private TextField productIdText;

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

    /**
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
     * The product to modify.
     */
    private Product productToModify;

    /**
     * The selected product.
     */
    private static Product selectedProduct;

    /**
     * Gets the product that is selected by the user and places it in the part table.
     *
     * @return A selected product.
     */
    public static Product getSelectedProduct() {
        return selectedProduct;
    }

    /**
     * Adds a part to the associated parts table.
     *
     * Includes appropriate error messages when needed.
     *
     * @param event Add product button clicked.
     */
    @FXML
    void onAddProduct(ActionEvent event) {

        Part selectAssociatedPart = partTable.getSelectionModel().getSelectedItem();
        if (selectAssociatedPart == null){
            displayAlert(1);
        }
        else{
            productToModify.getAllAssociatedParts().add(selectAssociatedPart);
        }
    }

    /**
     * Cancels modifying a product and loads the main screen controller.
     *
     * @param event Cancel product button clicked.
     * @throws IOException FXMLLoader.
     */
    @FXML
    void onCancelProduct(ActionEvent event) throws IOException {

        productToModify.getAllAssociatedParts().clear();

        productToModify.getAllAssociatedParts().addAll(originalParts);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
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
     * @param event Remove associated part button clicked.
     */
    @FXML
    void onRemoveAssociatedPart(ActionEvent event) {

        Part selectedAssociatedPart = associatedPartTable.getSelectionModel().getSelectedItem();
        if (selectedAssociatedPart == null) {
            displayAlert(2);
        }
        else if (selectedAssociatedPart != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            DialogPane dialogPane1 = confirmationAlert.getDialogPane();
            dialogPane1.setStyle("-fx-font-family: serif;");
            confirmationAlert.setTitle("Warning");
            confirmationAlert.setContentText("Are you sure you want to remove the selected associated part?");
            Optional<ButtonType> removeOption = confirmationAlert.showAndWait();

            if(removeOption.isPresent() && removeOption.get() == ButtonType.OK ){
                associatedParts.remove(selectedAssociatedPart);
                associatedPartTable.setItems(associatedParts);
            }
        }
    }

    /**
     * Saves data and loads the main screen controller.
     *
     * Includes appropriate error messages when needed.
     *
     * @param event Save product button clicked.
     * @throws IOException FXMLLoader.
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
                productToModify.setName(name);
                productToModify.setStock(stock);
                productToModify.setPrice(price);
                productToModify.setMin(min);
                productToModify.setMax(max);

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
     * Searches the part list by name or ID.
     *
     * Includes appropriate error messages when needed.
     *
     * @param event Search part button clicked.
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
                DialogPane dialogPane1 = alertError.getDialogPane();
                dialogPane1.setStyle("-fx-font-family: serif;");
                alertError.setTitle("Error");
                alertError.setHeaderText("Item not found.");
                alertError.showAndWait();
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

        productToModify = MainScreenController.selectProductModify();
        associatedParts = productToModify.getAllAssociatedParts();
        originalParts.addAll(productToModify.getAllAssociatedParts());

        productIdText.setText(String.valueOf(productToModify.getId()));
        productNameText.setText(productToModify.getName());
        productInvText.setText(String.valueOf(productToModify.getStock()));
        productPriceText.setText(String.valueOf(productToModify.getPrice()));
        productMaxText.setText(String.valueOf(productToModify.getMax()));
        productMinText.setText(String.valueOf(productToModify.getMin()));

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
        associatedPartTable.setItems(productToModify.getAllAssociatedParts());
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}