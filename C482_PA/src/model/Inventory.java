package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.Locale;


/**
 *This is the Inventory class.
 * This class is for manipulating parts and products (Inventory).
 *
 * @author Lydia Strough
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static boolean partFound;
    public static boolean productFound;

    /**
     * This is the add part method.
     * This method adds a new part to the all Parts list.
     * @param newPart the new part in question
     */
    public static void addPart(Part newPart) { allParts.add(newPart);}

    /**
     * This is the add product method.
     * This method adds a new product to the all Products list.
     *
     * @param newProduct the new product in question
     */
    public static void addProduct(Product newProduct) { allProducts.add(newProduct);}

    /**
     * This is the look up part method.
     * This method searches the all parts list for a specific part.
     *
     * @param partId the ID in question
     * @return the part associated with the part Id
     * @return no part if there is no match
     */
    public static Part lookupPart(int partId) {
        partFound = false;

        for(Part part : allParts) {
            if(part.getId() == partId) {
                partFound = true;
                return part;
            }
        }
        return null;
    }

    /**
     * This is the look up product method.
     * This method searches the all products list for a specific product.
     *
     * @param productId the ID in question
     * @return the product associated with the product Id
     * @return no product if there is no match
     */
    public static Product lookupProduct(int productId) {
        productFound = false;

        for(Product product : allProducts) {
            if(product.getId() == productId) {
                productFound = true;
                return product;
            }
        }
        return null;
    }

    /**
     * This is the look up part method.
     * This method searches the all parts list for a specific part or a filtered list of parts.
     *
     * @param partName the name in question
     * @return the part(s) associated with the part name
     * @return no part if there is no match
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        partFound = false;

        for(Part part : allParts) {
            if(part.getName().toLowerCase().contains(partName.toLowerCase())){
                filteredParts.add(part);
            }
        }
        if(filteredParts.isEmpty()) {
            return allParts;
        }
        partFound = true;
        return filteredParts;
    }

    /**
     * This is the look up product method.
     * This method searches the all products list for a specific product or a filtered list of products.
     *
     * @param productName the name in question
     * @return the product(s) associated with the product name
     * @return no product if there is no match
     */
      public static ObservableList<Product> lookupProduct(String productName) {
          ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
          productFound = false;

          for (Product product : allProducts) {
              if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                  filteredProducts.add(product);
              }
          }
          if(filteredProducts.isEmpty()) {
              return allProducts;
          }
          productFound = true;
          return filteredProducts;
      }

    /**
     * This is the update part method.
     * This method updates a part with the specified index.
     *
     * @param index the index
     * @param selectedPart the part in question
     */
    public static void updatePart(int index, Part selectedPart) { allParts.set(index, selectedPart);}

    /**
     * This is the update product method.
     * This method updates a product with the specified index.
     *
     * @param index the index
     * @param newProduct the product in question
     */
    public static void updateProduct(int index, Product newProduct) { allProducts.set(index, newProduct);}

    /**
     * This is the delete part method.
     * This method removes the selected part from the all parts list.
     *
     * @param selectedPart the part in question
     * @return the removal of the selected part from the all parts list
     */
    public static boolean deletePart(Part selectedPart) { return allParts.remove(selectedPart);}

    /**
     * This is the delete product method.
     * This method removes the selected product from the all products list.
     *
     * @param selectedProduct the product in question
     * @return the removal of the selected product from the all products list
     */
    public static boolean deleteProduct(Product selectedProduct) { return allProducts.remove(selectedProduct);}

    /**
     * @return the all parts list
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return the all products list
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
