package controller;

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
 * Controller class that provides logic for the Main screen of the application.
 *
 * @author Erik Nilson
 */
public class MainScreenController implements Initializable {
    Stage stage;
    Parent scene;

    /**
     * The part search text field.
     */
    @FXML
    private TextField partSearchText;

    /**
     * The part table view.
     */
    @FXML
    private TableView<Part> partsTable;

    /**
     * The part id column for the parts table.
     */
    @FXML
    private TableColumn<Part, Integer> partsIdCol;

    /**
     * The part name column for the parts table.
     */
    @FXML
    private TableColumn<Part, String> partsNameCol;

    /**
     * The part inventory column for the parts table.
     */
    @FXML
    private TableColumn<Part, Integer> partsInventoryLevelCol;

    /**
     * The part price column for the parts table.
     */
    @FXML
    private TableColumn<Part, Double> partsPricePerUnitCol;

    /**
     * The product search text field.
     */
    @FXML
    private TextField productSearchText;

    /**
     * The product table view.
     */
    @FXML
    private TableView<Product> productsTable;

    /**
     * The product id column for the parts table.
     */
    @FXML
    private TableColumn<Product, Integer> productIdCol;

    /**
     * The product name column for the parts table.
     */
    @FXML
    private TableColumn<Product, String> productNameCol;

    /**
     * The product inventory column for the parts table.
     */
    @FXML
    private TableColumn<Product, Integer> productInventoryLevelCol;

    /**
     * The product price column for the parts table.
     */
    @FXML
    private TableColumn<Product, Double> productPricePerUnitCol;

    /**
     * Part that is selected by the user.
     */
    private static Part selectedPart;

    /**
     * Product that is selected by the user.
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
     * Gets the part to modify that is selected by the user and places it in the part table.
     *
     * @return A selected Part.
     */
    public static Part selectPartModify() {
        return selectedPart;
    }

    /**
     * Gets the product to modify that is selected by the user and places it in the product table.
     *
     * @return A Selected Product.
     */
    public static Product selectProductModify() {
        return selectedProduct;
    }

    /**
     * Loads the add part screen from the main screen.
     *
     * @param event Add part button clicked.
     * @throws IOException FXMLLoader.
     */
    @FXML
    void onAddParts(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartScreen.fxml"));
        scene.setStyle(("-fx-font-family: 'serif';"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Loads the add product screen from the main screen.
     *
     * @param event Add product button clicked.
     * @throws IOException FXMLLoader.
     */
    @FXML
    void onAddProducts(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductScreen.fxml"));
        scene.setStyle(("-fx-font-family: 'serif';"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Deletes a part from the main screen.
     *
     * If no part is selected, an error window is displayed.
     *
     * @param event Delete part button clicked.
     */
    @FXML
    void onDeleteParts(ActionEvent event) {

        selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            displayAlert(2);
        } else {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            DialogPane dialogPane1 = confirmationAlert.getDialogPane();
            dialogPane1.setStyle("-fx-font-family: serif;");
            confirmationAlert.setTitle("Warning");
            confirmationAlert.setContentText("Are you sure you want to remove the selected part?");
            Optional<ButtonType> removeOption = confirmationAlert.showAndWait();

            if (removeOption.isPresent() && removeOption.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        }
    }

    /**
     * Deletes a product from the main screen.
     *
     * If no product is selected, an error window is displayed.
     *
     * @param event Delete product button clicked.
     */
    @FXML
    void onDeleteProducts(ActionEvent event) {

        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null)
            displayAlert(2);
        else {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            DialogPane dialogPane1 = confirmationAlert.getDialogPane();
            dialogPane1.setStyle("-fx-font-family: serif;");
            confirmationAlert.setTitle("Warning");
            confirmationAlert.setContentText("Are you sure you want to remove the selected product?");
            Optional<ButtonType> removeOption = confirmationAlert.showAndWait();

            if (removeOption.isPresent() && removeOption.get() == ButtonType.OK) {
                ObservableList<Part> originalParts = selectedProduct.getAllAssociatedParts();
                if (originalParts.size() >= 1) {
                    displayAlert(4);
                } else {
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        }
    }

    /**
     * Exits the program and closes the main screen.
     *
     * @param event Exit button clicked.
     */
    @FXML
    void onExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Loads the modify part screen from the main screen.
     *
     * If no part is selected, an error window is displayed.
     *
     * @param event Modify parts button clicked.
     * @throws IOException FXMLLoader.
     */
    @FXML
    void onModifyParts(ActionEvent event) throws IOException {

        selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(3);
        }
        else {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyPartScreen.fxml"));
            scene.setStyle(("-fx-font-family: 'serif';"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Loads the modify product screen from the main screen.
     *
     * If no product is selected, an error window is displayed.
     *
     * @param event Modify products button clicked.
     * @throws IOException FXMLLoader.
     */
    @FXML
    void onModifyProducts (ActionEvent event) throws IOException {

        selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            displayAlert(3);
        } else {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyProductScreen.fxml"));
            scene.setStyle(("-fx-font-family: 'serif';"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Searches the part list by name or ID
     *
     * If no part is found, an error window is displayed
     *
     * @param event Search parts button clicked.
     */
    @FXML
    public void onSearchParts (ActionEvent event){

        try {
            int partId = Integer.parseInt(partSearchText.getText());
            Part partFound = Inventory.lookupPart(partId);
            partsTable.requestFocus();
            partsTable.getSelectionModel().select(partFound);

        if (partFound == null)
                displayAlert(1);
            }
        catch (Exception e) {
            String searchString = partSearchText.getText();
            ObservableList<Part> partFound = Inventory.lookupPart(searchString);
            partsTable.setItems(partFound);

        if (partFound.size() == 0){
            displayAlert(1);
            }
        }
    }

    /**
     * Searches the product list by name or ID.
     *
     * If no product is found, an error window is displayed.
     *
     * @param event Search products button clicked.
     */
    @FXML
    public void onSearchProducts (ActionEvent event){

        try {
            int productId = Integer.parseInt(productSearchText.getText());
            Product foundProduct = Inventory.lookupProduct(productId);
            productsTable.requestFocus();
            productsTable.getSelectionModel().select(foundProduct);

            if (foundProduct == null)
                displayAlert(1);
            }
        catch (Exception e) {
            String searchString = productSearchText.getText();
            ObservableList<Product> foundProduct = Inventory.lookupProduct(searchString);
            productsTable.setItems(foundProduct);

            if (foundProduct.size() == 0){
                displayAlert(1);
            }
        }
    }

    /**
     * Various alert and error messages.
     *
     * @param alertType Message type selector.
     */
    private void displayAlert (int alertType){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                DialogPane dialogPane1 = alert.getDialogPane();
                dialogPane1.setStyle("-fx-font-family: serif;");
                alertError.setTitle("Information");
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
                DialogPane dialogPane3 = alert.getDialogPane();
                dialogPane3.setStyle("-fx-font-family: serif;");
                alert.setTitle("Information");
                alert.setHeaderText("Please select an item to modify.");
                alert.showAndWait();
                break;
            case 4:
                DialogPane dialogPane4 = alert.getDialogPane();
                dialogPane4.setStyle("-fx-font-family: serif;");
                alert.setTitle("Error");
                alert.setHeaderText("Cannot delete a product with associated parts.");
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
    public void initialize (URL url, ResourceBundle rb){

        /**
         * Sets columns and rows with data for parts.
         */
        partsTable.setItems(Inventory.getAllParts());
        partsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        /**
         * Sets columns and rows with data for products.
         */
        productsTable.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
