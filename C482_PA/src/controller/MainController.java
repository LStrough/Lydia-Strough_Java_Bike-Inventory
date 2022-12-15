package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.*;

/**
 * This is the Main Menu Form controller.
 *
 * @author Lydia Strough
 */
public class MainController implements Initializable {

    Stage stage;
    Parent scene;

    /**
     * This is the all Parts table.
     * */
    public TableView<Part> partTableView;
    /**
     * This is the part id column.
     * */
    public TableColumn<Part,Integer> partIdCol;
    /**
     * This is the part name column.
     * */
    public TableColumn<Part, String> partNameCol;
    /**
     * This is the part inventory level column.
     * */
    public TableColumn<Part, Integer> partInvCol;
    /**
     * This is the part price column.
     * */
    public TableColumn<Part, Double> partPriceCol;
    /**
     * This is the all Products table.
     * */
    public TableView<Product> productTableView;
    /**
     * This is the product id column.
     * */
    public TableColumn<Product, Integer> productIdCol;
    /**
     * This is the product name column.
     * */
    public TableColumn<Product, String> productNameCol;
    /**
     * This is the product inventory level column.
     * */
    public TableColumn<Product, Integer> productInvCol;
    /**
     * This is the product price column.
     * */
    public TableColumn<Product, Double> productPriceCol;

    /**
     * This is the search part text field.
     * */
    public TextField searchPart;
    /**
     * This is the search product text field.
     * */
    public TextField searchProduct;

    /**
     * This is the Search Part method.
     *
     * <p>This method populates the partTableView with part results based on part Id or part name.</p>
     * <p> partTableView is set to all Parts list. text is gathered from search bar and converted into a string.
     * partId is assigned the text. lookupPart method is called. partTableView highlights a result or returns null.
     * </p>
     * <p>If null is returned, the text is assigned to partName and the lookupPart method is called again using partName.
     * If a match (or similar results) is found, the table filters based on the text. If no match is found, an error message:
     * "No item was found." pops up in a dialog box.</p>
     *
     * @param actionEvent text is entered into search part bar, then enter is pressed
     * */
    public void onActionSearchPart(ActionEvent actionEvent) {
        try{
            partTableView.setItems(Inventory.getAllParts());
            int partId = Integer.parseInt(searchPart.getText());
            Part part = Inventory.lookupPart(partId);
            partTableView.getSelectionModel().select(part);
            partTableView.scrollTo(part);
            partTableView.requestFocus();
        }catch (NumberFormatException e){
            String partName = searchPart.getText();
            ObservableList<Part> parts = Inventory.lookupPart(partName);
            partTableView.setItems(parts);
        }
        if(!Inventory.partFound) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No item was found.");
            alert.showAndWait();
        }
    }

    /**
     * This is the Search Product method.
     *
     * <p>This method populates the productTableView with product results based on product Id or product name.</p>
     * <p> productTableView is set to all Products list. text is gathered from search bar and converted into a string.
     * productId is assigned the text. lookupProduct method is called. productTableView highlights a result or returns null.
     * </p>
     * <p>If null is returned, the text is assigned to productName and the lookupProduct method is called again using productName.
     * If a match (or similar results) is found, the table filters based on the text. If no match is found, an error message:
     * "No item was found." pops up in a dialog box.</p>
     *
     * @param actionEvent text is entered into search product bar, then enter is pressed
     * */
    public void onActionSearchProduct(ActionEvent actionEvent) {
        try{
            productTableView.setItems(Inventory.getAllProducts());
            int productId = Integer.parseInt(searchProduct.getText());
            Product product = Inventory.lookupProduct(productId);
            productTableView.getSelectionModel().select(product);
            productTableView.scrollTo(product);
            productTableView.requestFocus();
        }catch (NumberFormatException e){
            String productName = searchProduct.getText();
            ObservableList<Product> products = Inventory.lookupProduct(productName);
            productTableView.setItems(products);
        }
        if(!Inventory.productFound) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No item was found.");
            alert.showAndWait();
        }
    }

    /**
     * This is the Add Part method.
     *
     * <p>The scene is shifted from the Main Menu form to the Add Part form</p>
     *
     * @param actionEvent the add part button is clicked
     * */
    public void onActionAddPart(ActionEvent actionEvent) throws IOException {
        System.out.println("Add Part Button Clicked!");

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This is the Modify Part method.
     *
     * <p>The scene is shifted from the Main Menu form to the Modify Part form</p>
     *
     * @param actionEvent the Modify part button is clicked
     * */
    public void onActionModifyPart(ActionEvent actionEvent) throws IOException {
        System.out.println("Modify Part Button Clicked!");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
        Parent scene = loader.load();

        ModifyPart modPartController = loader.getController();

        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        modPartController.modifyPart(selectedPart);

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This is the Delete Part method.
     *
     * <p>The user selects a row in the partTableView and it is assigned to selectedPart.</p>
     *
     * <p>A confirmation dialog box pops up with the following message: The selected Part will be
     * deleted, do you wish to continue?</p>
     *
     * <p>If the user selects the OK button, the deletePart method is called from the Inventory class, with
     * the selectedPart used as the parameter.</p>
     *
     * @param actionEvent the delete part button is clicked
     * */
    public void onActionDeletePart(ActionEvent actionEvent) throws IOException{
        System.out.println("Delete Part Button Clicked!");

        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("The selected Part will be deleted, do you wish to continue?");
        alert.showAndWait();

        Optional<ButtonType> result = alert.showAndWait();

        if ((result.isPresent() && result.get() == ButtonType.OK)) {
            Inventory.deletePart(selectedPart);
            System.out.println("Item successfully deleted!");
        }
    }

    /**
     * This is the Add Product method.
     *
     * <p>The scene is shifted from the Main Menu form to the Add Product form</p>
     *
     * @param actionEvent the Add Product button is clicked
     * */
    public void onActionAddProduct(ActionEvent actionEvent) throws IOException {
        System.out.println("Add Product Button Clicked!");

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This is the Modify Product method.
     *
     * <p>The scene is shifted from the Main Menu form to the Modify Product form</p>
     *
     * <p>The user selects a row in the productTableView and it is assigned to selectedProduct.</p>
     *
     * @param actionEvent the Modify Product button is clicked
     * */
    public void onActionModifyProduct(ActionEvent actionEvent) throws IOException {
        System.out.println("Modify Product Button Clicked!");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
        Parent scene = loader.load();

        ModifyProduct modProductController = loader.getController();

        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

        modProductController.modifyProduct(selectedProduct);

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This is the Delete Product method.
     *
     * <p>The user selects a row in the product table view and it is assigned to selectedProduct.</p>
     *
     * <p>The code checks for an exception: it checks to see if the selected products' associated parts list is empty.
     * If the list is not empty, the catch block runs.</p>
     *
     * <p>In the catch block, an error dialog box pops up and the following message displays: "This product contains
     * "associated parts", and cannot be deleted."</p>
     *
     * <p>If the catch block is not run, a confirmation dialog box is then called and the following message loads: "The selected Product
     * will be deleted, do you wish to continue?" If the user selects the Okay button, the deleteProduct method
     * is called from the Inventory class and the selectedProduct object is used as the parameter.</p>
     *
     * @param actionEvent the delete product button clicked
     * */
    public void onActionDeleteProduct(ActionEvent actionEvent) {
        System.out.println("Delete Product Button Clicked!");

        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

        try {
            if(!selectedProduct.getAllAssociatedParts().isEmpty()) {
                throw new Exception();
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("The selected Product will be deleted, do you wish to continue?");
            alert.showAndWait();

            Optional<ButtonType> result = alert.showAndWait();

            if ((result.isPresent() && result.get() == ButtonType.OK)) {
                Inventory.deleteProduct(selectedProduct);
                System.out.println("Item successfully deleted!");
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("This product contains \"associated parts\", and cannot be deleted.");
            alert.showAndWait();
        }
    }

    /**
     * This is the Exit method.
     *
     * <p>A confirmation dialog box populates: "Do you wish to Exit the program?".
     *  If the user hits the OK button, the program ends. If cancel is selected, the user
     *  stays in the Main Menu form page.</p>
     *
     * @param actionEvent exit button is pushed
     * */
    public void onActionExitMain(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Do you wish to Exit the program?");
        alert.showAndWait();

        Optional<ButtonType> result = alert.showAndWait();

        if ((result.isPresent() && result.get() == ButtonType.OK)) {
            System.exit(0);
        }
    }

    /**
     * This is the initialize method.
     *
     * <p>This is the first method to run when the Main Menu Controller page is pulled up.</p>
     *
     * <p>partTableView calls getAllParts from the Inventory class and assigns all parts to its rows.</p>
     *
     * <p>partIdCol is assigned with the Part class id value.</p>
     * <p>partNameCol is assigned with the Part class name value.</p>
     * <p>partInvCol is assigned with the Part class stock value.</p>
     * <p>partPriceCol is assigned with the Part class price value.</p>
     *
     * <p>productTableView calls getAllProducts from the Inventory class and assigns all products to its rows.</p>
     *
     * <p>productIdCol is assigned with the Product class id value.</p>
     * <p>productNameCol is assigned with the Product class name value.</p>
     * <p>productInvCol is assigned with the Product class stock value.</p>
     * <p>productPriceCol is assigned with the Product class price value.</p>
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Main: I am initialized!");

        partTableView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));;
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));;
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));;

        productTableView.setItems(Inventory.getAllProducts());

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));;
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));;
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));;
    }
}
