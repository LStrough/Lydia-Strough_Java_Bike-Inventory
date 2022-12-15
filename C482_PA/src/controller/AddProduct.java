package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
 * This is the add product controller.
 *
 *<p>This class allows the user to add a product to the program Inventory. The user gives the product
 * a name, inventory level, price, max, min, and associated parts (if they wish). </p>
 *
 * @author Lydia Strough
 */
public class AddProduct implements Initializable {

    Stage stage;
    Parent scene;

    /**
     * This is the associated parts list.
     * */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * This is the all Parts table.
     * */
    public TableView<Part> partATableView;
    /**
     * This is the all parts table Part ID column.
     * */
    public TableColumn<Part, Integer> partIdACol;
    /**
     * This is the all parts table Part Name column.
     * */
    public TableColumn<Part, String> partNameACol;
    /**
     * This is the all parts table Inventory Level column.
     * */
    public TableColumn<Part, Integer> partInvACol;
    /**
     * This is the all parts table Price column.
     * */
    public TableColumn<Part, Double> partPriceACol;
    /**
     * This is the associated parts table.
     * */
    public TableView<Part> partBTableView;
    /**
     * This is the associated parts table Part ID column.
     * */
    public TableColumn<Part, Integer> partIdBCol;
    /**
     * This is the associated parts table Part Name column.
     * */
    public TableColumn<Part, String> partNameBCol;
    /**
     * This is the associated parts table Inventory Level column.
     * */
    public TableColumn<Part, Integer> partInvBCol;
    /**
     * This is the associated parts table Price column.
     * */
    public TableColumn<Part, Double> partPriceBCol;

    /**
     * This is the product id.
     * */
    public TextField productIdTxt;
    /**
     * This is the product name.
     * */
    public TextField productNameTxt;
    /**
     * This is the product inventory level.
     * */
    public TextField productInvTxt;
    /**
     * This is the product price.
     * */
    public TextField productPriceTxt;
    /**
     * This is the product max.
     * */
    public TextField productMaxTxt;
    /**
     * This is the product min.
     * */
    public TextField productMinTxt;
    /**
     * This is the search part.
     * */
    public TextField searchPart;

    /**
     * This is the Search Part method.
     *
     * <p>This method populates the partATableView with part results based on part Id or part name.</p>
     * <p> partATableView is set to all Parts list. text is gathered from search bar and converted into a string.
     * partId is assigned the text. lookupPart method is called. partATableView highlights a result or returns null.
     * </p>
     * <p>If null is returned, the text is assigned to partName and the lookupPart method is called again using partName.
     * If a match (or similar results) is found, the table filters based on the text. If no match is found, an error message:
     * "No item was found." pops up in a dialog box.</p>
     *
     * @param actionEvent text is entered into search bar, then enter is pressed
     * */
    public void onActionSearchPart(ActionEvent actionEvent) {
        try{
            partATableView.setItems(Inventory.getAllParts());
            int partId = Integer.parseInt(searchPart.getText());
            Part part = Inventory.lookupPart(partId);
            partATableView.getSelectionModel().select(part);
            partATableView.scrollTo(part);
            partATableView.requestFocus();
        }catch (NumberFormatException e){
            String partName = searchPart.getText();
            ObservableList<Part> parts = Inventory.lookupPart(partName);
            partATableView.setItems(parts);
        }
        if(!Inventory.partFound) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No item was found.");
            alert.showAndWait();
        }
    }

    /**
     * This is the Display Main method.
     *
     * <p>A confirmation dialog box populates: "All changes will be forgotten, do you wish to continue?".
     *  If the user hits the OK button, the scene shifts to the Main Menu. If cancel is selected, the user
     *  stays in the add product page.</p>
     *
     * @param actionEvent cancel button is pushed
     * */
    public void onActionDisplayMain(ActionEvent actionEvent) throws IOException {
        System.out.println("Cancel Button Clicked!");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("All changes will be forgotten, do you wish to continue?");
        alert.showAndWait();

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * This is the Remove Part from Product method.
     *
     * <p>User selects a row in partBTableView, this row is assigned to selected part.
     * The user then pushes the remove associated part button. A confirmation dialog box then populates
     * with the following message: "You have chosen to remove a part from your product, do you wish to continue?"
     *  If the user selects the OK button, the selected part is then removed from the associated parts list.
     *  As well as partBTableView.</p>
     *
     *  <p>If the user fails to select a row, before pushing the remove associated part button,
     *  an error dialog box populates with the following message: "Item not found."</p>
     *
     * @param actionEvent remove associated part button is pushed
     * */
    public void onActionRemovePartFromProduct(ActionEvent actionEvent) {
        System.out.println("Remove Associated Part Button Clicked!");

        Part selectedPart = (Part) partBTableView.getSelectionModel().getSelectedItem();

        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Item not found.");
            alert.showAndWait();

            return;
        }

        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setContentText("You have chosen to remove a part from your product, do you wish to continue?");
        alert1.showAndWait();

        Optional<ButtonType> result = alert1.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            associatedParts.remove(selectedPart);
        }
    }

    /**
     *This is the Save Product method.
     *
     * <p>Observable List, allProducts, is created and calls the getAllProducts method from the Inventory class.
     * product id is assigned 0 (by program initially, as it is a disabled text field), name, price, stock, max,
     * and min all grab text from their designated text fields.</p>
     *
     * <p>If the user inputted text into each field, they are each checked for exceptions. If an exception is found,
     * warning dialog boxes are called with a specific message associated with each.</p>
     *
     * <p>If max is less than min, or min is a negative number, a number format exception is called with the following message:
     * "Exception: Min must be less than Max. Please enter a valid value for each text field."</p>
     *
     * <p>If stock is not between max or min, a number format exception is called with the following message:
     *  "Exception: Inventory Level must be between Min and Max. Please enter a valid value for each text field."</p>
     *
     *  <p>If the text fields are empty or contain anything other than their designated field type, then the
     *  following message populates: "Exception: e.getMessage(). Please enter a valid value for each text field."
     *  (e.getMessage() is replaced with the incorrect field text)</p>
     *
     * <p>If no exceptions are called, product id is assigned with the size of the all products list + 1000.</p>
     *
     * <p>A new product object is then created and assigned each value (id, name, price, stock, min, and max). If any data
     *  is in the partBTableView, it is then added to the new products associated parts list.</p>
     *
     *<p>The addProduct method is then called from the Inventory class with the newProduct object as a parameter.</p>
     *
     * <p>The scene is then shifted back to the Main Menu form.</p>
     *
     * @param actionEvent the Save button is clicked
     * */
    public void onActionSaveProduct(ActionEvent actionEvent) throws IOException {
        System.out.println("Save Button Clicked!");

        try{
            ObservableList<Product> allProducts = Inventory.getAllProducts();

            int id = 0;
            String name = productNameTxt.getText();
            double price = Double.parseDouble(productPriceTxt.getText());
            int stock = Integer.parseInt(productInvTxt.getText());
            int max = Integer.parseInt(productMaxTxt.getText());
            int min = Integer.parseInt(productMinTxt.getText());

            if(!(max > min) || !(min >= 1)) {
                throw new NumberFormatException("Min must be less than Max");
            }
            if( (stock > max) || (stock < min)) {
                throw new NumberFormatException("Inventory Level must be between Min and Max");
            }
            for(int i = 0; i <= allProducts.size(); ++i) {
                id = i + 1000;
            }

            Product newProduct = new Product(id, name, price, stock, min, max);

            for(Part part : associatedParts) {
                newProduct.addAssociatedPart(part);
            }

            Inventory.addProduct(newProduct);

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Exception: " + e.getMessage() + ". Please enter a valid value for each text field.");
            alert.showAndWait();
        }
    }

    /**
     * This is the Add Part to Product method.
     *
     * <p>selectedPart object is assigned whatever row the user selects in partATableView.
     *  The selectedPart is then added to associatedParts.</p>
     *
     *  <p>If no row is selected, when the add button is clicked, an Error dialog box
     *  populates with the following message: "Item not found."</p>
     *
     * @param actionEvent Add button is clicked
     * */
    public void onActionAddPartToProduct(ActionEvent actionEvent) {
        System.out.println("Add Button Clicked!");

        Part selectedPart = (Part) partATableView.getSelectionModel().getSelectedItem();

        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Item not found.");
            alert.showAndWait();

            return;
        }
        associatedParts.add(selectedPart);
    }

    /**
     * This is the initialize method.
     *
     * <p>This is the first method to run when the AddProduct page is pulled up.</p>
     *
     * <p>partATableView calls getAllParts from the Inventory class and assigns all parts to its rows.</p>
     *
     * <p>partIdACol is assigned with the Part class id value.</p>
     * <p>partNameACol is assigned with the Part class name value.</p>
     * <p>partInvACol is assigned with the Part class stock value.</p>
     * <p>partPriceACol is assigned with the Part class price value.</p>
     *
     * <p>partBTableView then assigns its rows to the associated parts that will be associated with your product</p>
     *
     * <p>partIdBCol is assigned with the Part class id value.</p>
     * <p>partNameBCol is assigned with the Part class name value.</p>
     * <p>partInvBCol is assigned with the Part class stock value.</p>
     * <p>partPriceBCol is assigned with the Part class price value.</p>
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Add Product: I am initialized!");

        partATableView.setItems(Inventory.getAllParts());

        partIdACol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameACol.setCellValueFactory(new PropertyValueFactory<>("name"));;
        partInvACol.setCellValueFactory(new PropertyValueFactory<>("stock"));;
        partPriceACol.setCellValueFactory(new PropertyValueFactory<>("price"));;

        partBTableView.setItems(associatedParts);

        partIdBCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameBCol.setCellValueFactory(new PropertyValueFactory<>("name"));;
        partInvBCol.setCellValueFactory(new PropertyValueFactory<>("stock"));;
        partPriceBCol.setCellValueFactory(new PropertyValueFactory<>("price"));;
    }
}
