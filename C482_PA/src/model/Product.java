package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 * This is the Product class.
 *This class is for manipulating products and their associated parts.
 *
 * @author Lydia Strough
 */
public class Product extends Inventory{

    /**
     * This is the Product associated parts list.
     * */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * This is the product id.
     * */
    private int id;
    /**
     * This is the product name.
     * */
    private String name;
    /**
     * This is the product price.
     * */
    private double price;
    /**
     * This is the product inventory level.
     * */
    private int stock;
    /**
     * This is the product min.
     * */
    private int min;
    /**
     * This is the product max.
     * */
    private int max;

    /**
     * This is the Product constructor.
     *
     * @param id product id
     * @param name product name
     * @param price product price
     * @param stock product inventory level
     * @param min min
     * @param max max
     * */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() { return name;}

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * This is the add associated part method.
     * This method adds a part to the associated parts list.
     * @param part the part being added the associated parts list
     */
    public void addAssociatedPart(Part part) { associatedParts.add(part);}

    /**
     * This is the delete associated part method.
     * This method deletes the selected part from the associated parts list.
     * @param selectedAssociatedPart the associated part in question
     * @return the removal of associated part
     * @return if part was selected, then "No item was found"
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if(!(selectedAssociatedPart == null)) {
           return associatedParts.remove(selectedAssociatedPart);
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("No item was found.");
        alert.showAndWait();
        return false;
    }

    /**
     * @return the associated parts list
     */
    public ObservableList<Part> getAllAssociatedParts() { return associatedParts; }
}
