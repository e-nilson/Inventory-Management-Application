package model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * Class for Products.
 *
 * @author Erik Nilson
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private String companyName;

    /**
     * The constructor for Products.
     */
    public Product(int id, String name, double price, int stock, int min, int max, String companyName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.companyName = companyName;
    }

    /**
     * Observable list associated parts for the product.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Adds a part from the associated parts list.
     *
     * @param part The part to add.
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * Gets all associated parts.
     *
     * @return a list of parts.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * Gets the product ID.
     *
     * @return the id.
     */
    public int getId() {
    return id;
}

    /**
     * Sets the product ID.
     *
     * @param id the id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the product name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name.
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the product price.
     *
     * @return the price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the product price.
     *
     * @param price the price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the product stock.
     *
     * @return the stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the product stock.
     *
     * @param stock the stock to set.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Gets the product min.
     *
     * @return the min.
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets the product min.
     *
     * @param min the min to set.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Gets the product max.
     *
     * @return the max.
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the product max.
     *
     * @param max the max to set.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Deletes a part from the associated parts list.
     *
     * @param selectedAssociatedPart The part to delete.
     *
     * @return a boolean representing the status of the part deletion.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
      if(selectedAssociatedPart != null) {
          associatedParts.remove(selectedAssociatedPart);
          return true;
      }
       return false;
    }
}