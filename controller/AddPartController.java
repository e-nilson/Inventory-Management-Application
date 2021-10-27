package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;

/**
 * Controller class that provides logic for the Add Part screen of the application.
 *
 * @author Erik Nilson
 */
public class AddPartController {
    Stage stage;
    Parent scene;

    /**
     * The outsourced radio button.
     */
    @FXML
    private RadioButton outsourcedRadioButton;

    /**
     * The inhouse radio button.
     */
    @FXML
    private RadioButton inhouseRadioButton;

    /**
     * The part type label.
     */
    @FXML
    private Label partTypeLabel;

    /**
     * The part name text field.
     */
    @FXML
    private TextField partNameText;

    /**
     * The part inventory text field.
     */
    @FXML
    private TextField partInvText;

    /**
     * The part price text field.
     */
    @FXML
    private TextField partPriceText;

    /**
     * The part min text field.
     */
    @FXML
    private TextField partMinText;

    /**
     * The part max text field.
     */
    @FXML
    private TextField partMaxText;

    /**
     * The part machine id text field.
     */
    @FXML
    private TextField partMachineIdText;

    /**
     * Cancels adding a part and loads the main screen controller.
     *
     * @param event Cancel button clicked.
     * @throws IOException FXMLLoader.
     */
    @FXML
    void onCancelPart(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        scene.setStyle(("-fx-font-family: 'serif';"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Sets the Machine ID and Company Label to "Company Name".
     *
     * @param event Outsourced radio button clicked.
     */
    @FXML
    public void onOutsourcedRadioButton (ActionEvent event){
        partTypeLabel.setText("Company Name");
    }

    /**
     * Sets the Machine ID and Company Label to "Machine ID".
     *
     * @param event InHouse radio button clicked.
     */
    @FXML
    public void onInhouseRadioButton (ActionEvent event){
        partTypeLabel.setText("Machine ID");
    }

    /**
     * Saves a new part to the inventory and loads the main screen controller.
     *
     * Includes appropriate error messages when needed.
     *
     * @param event Save button clicked.
     * @throws IOException FXMLLoader.
     */
    @FXML
    void onSavePart(ActionEvent event) throws IOException {
        try {
            int id = 0;
            for (Part part : Inventory.getAllParts()) {
                if (part.getId() > id)
                    id = (part.getId());
                id = ++id;
            }

            String name = partNameText.getText();
            int stock = Integer.parseInt(partInvText.getText());
            double price = Double.parseDouble(partPriceText.getText());
            int min = Integer.parseInt(partMinText.getText());
            int max = Integer.parseInt(partMaxText.getText());
            int machineId;
            String companyName;

            boolean partAdded = false;

            if (name.isEmpty()) {
                displayAlert(1);
            } else if ((min < 0) || (min > max)) {
                displayAlert(3);
            } else if (!((min <= stock) && (max >= stock))) {
                displayAlert(2);
            } else {

                try {
                    if (inhouseRadioButton.isSelected()) {
                        machineId = Integer.parseInt(partMachineIdText.getText());
                        InHouse newInHousePartAdd = new InHouse(id, name, price, stock, min, max, machineId);
                        Inventory.addPart(newInHousePartAdd);
                        partAdded = true;
                    }
                } catch (Exception e) {
                    displayAlert(5);
                }

                if (outsourcedRadioButton.isSelected()) {
                    companyName = partMachineIdText.getText();
                    Outsourced newOutsourcedPartAdd = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(newOutsourcedPartAdd);
                    partAdded = true;
                }

                if (partAdded) {
                    onCancelPart(event);
                }
            }
        } catch (Exception e) {
            displayAlert(4);
        }
    }

    /**
     * Various alert and error messages.
     *
     * @param alertType Message type selector.
     */
    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                DialogPane dialogPane1 = alert.getDialogPane();
                dialogPane1.setStyle("-fx-font-family: serif;");
                alert.setTitle("Error");
                alert.setHeaderText("Error: Part Addition");
                alert.setContentText("Cannot have blank name field");
                alert.showAndWait();
                break;
            case 2:
                DialogPane dialogPane2 = alert.getDialogPane();
                dialogPane2.setStyle("-fx-font-family: serif;");
                alert.setTitle("Error");
                alert.setHeaderText("Error: Invalid Inventory number");
                alert.setContentText("Inventory must be between min and max values");
                alert.showAndWait();
                break;
            case 3:
                DialogPane dialogPane3 = alert.getDialogPane();
                dialogPane3.setStyle("-fx-font-family: serif;");
                alert.setTitle("Error");
                alert.setHeaderText("Error: Min/Max Value");
                alert.setContentText("Min value must be greater than 0 and less than max value");
                alert.showAndWait();
                break;
            case 4:
                DialogPane dialogPane4 = alert.getDialogPane();
                dialogPane4.setStyle("-fx-font-family: serif;");
                alert.setTitle("Error");
                alert.setHeaderText("Error: Blank fields");
                alert.setContentText("Cannot have blank or invalid fields");
                alert.showAndWait();
                break;
            case 5:
                DialogPane dialogPane5 = alert.getDialogPane();
                dialogPane5.setStyle("-fx-font-family: serif;");
                alert.setTitle("Error");
                alert.setHeaderText("Error: Machine ID");
                alert.setContentText("Invalid entry. Must be integers only");
                alert.showAndWait();
                break;
        }
    }
}
