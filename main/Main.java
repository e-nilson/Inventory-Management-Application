package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/**
 * The Inventory Management Application is used for the management of parts and products.
 *
 * Being able to copy and/or duplicate a part or product would be a nice future enhancement of the application.
 * This has the potential to increase efficiency in creating new parts or products.
 *
 * My logic error is described in in the modify part controller, line 139.
 *
 *
 * JavaDoc is located in folder the javadoc within the C482 folder. C482/javadoc.
 *
 * @author Erik Nilson
 */
public class Main extends Application {

    /**
     * The start method that loads the initial screen.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        root.setStyle("-fx-font-family: 'serif';");
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 800, 450));
        primaryStage.show();

    }

    /**
     * The main method launches the application with sample data.
     */
    public static void main(String[] args) {
        /**
         * Sample parts for the application.
         */
        InHouse inHousePart1 = new InHouse(1, "Brakes", 15.00, 10, 1, 10, 1);
        InHouse inHousePart2 = new InHouse(2, "Wheel", 11.00, 16, 1, 10, 2);
        InHouse inHousePart3 = new InHouse(3, "Seat", 15.00, 10, 1, 10, 3);
        Outsourced outsourcedPart4 = new Outsourced(4, "Rim", 56.99, 15, 1, 20, "Super Bikes");

        Inventory.addPart(inHousePart1);
        Inventory.addPart(inHousePart2);
        Inventory.addPart(inHousePart3);
        Inventory.addPart(outsourcedPart4);

        /**
         * Sample products for the application.
         */
        Product product1 = new Product(1000, "Giant Bike", 299.99, 5, 1, 20, "Giant");
        Product product2 = new Product(1001, "Tricycle", 99.99, 3, 1, 20, "Trek");
        product1.addAssociatedPart(inHousePart1);
        product2.addAssociatedPart(outsourcedPart4);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);

        launch(args);
    }
}
