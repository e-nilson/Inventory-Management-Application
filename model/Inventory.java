package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class that holds the entire inventory of parts and products for the application.
 *
 * Includes methods used throughout the application.
 *
 * @author Erik Nilson
 */
public class Inventory {

    /**
     * Observable list for all the parts.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * Observable list for all the products.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a part to the inventory.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a product to the inventory.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Looks up part by ID from the list.
     */
    public static Part lookupPart(int partId) {
        Part partFound = null;

        for (Part part : allParts) {
            if (part.getId() == partId) {
                partFound = part;
                return partFound;
            }
        }
        return null;
    }

    /**
     * Looks up product by ID from the list.
     */
    public static Product lookupProduct(int productId) {
        Product productFound = null;

        for (Product product : allProducts) {
            if (product.getId() == productId) {
                productFound = product;
                return productFound;
            }
        }
        return null;
    }

    /**
     * Looks up part by Name from the list.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partFound = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                partFound.add(part);
            }
        }
        return partFound;
    }

    /**
     * Looks up product by Name from the list.
     */
    public static ObservableList<Product> lookupProduct (String productName) {
        ObservableList<Product> productFound = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                productFound.add(product);
            }
        }
        return productFound;
    }

    /**
     * Updates a part from the list.
     */
    // Updates a part from the list
    public void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /*
    // Updates a products from the list
    public void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

     */

    /**
     * Deletes a part from the list.
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Deletes a product from the list.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Swaps a part to modify.
     */
    public static boolean swapPartModify(Part selectedPart){
        if(allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets all parts from the list.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Gets all products from the list.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
