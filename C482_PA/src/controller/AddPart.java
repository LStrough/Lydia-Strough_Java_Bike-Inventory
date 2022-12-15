package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the add part controller.
 *
 *<p>This class allows the user to add a part to the program Inventory. The user gives the part
 * a name, inventory level, price, max, and min. </p>
 *
 * @author Lydia Strough
 */
public class AddPart implements Initializable {

    Stage stage;
    Parent scene;

    /**
     * This is the part id text field.
     * */
    public TextField partIdTxt;
    /**
     * This is the part name text field.
     * */
    public TextField partNameTxt;
    /**
     * This is the part inventory level text field.
     * */
    public TextField partInvTxt;
    /**
     * This is the part price text field.
     * */
    public TextField partPriceTxt;
    /**
     * This is the part max text field.
     * */
    public TextField partMaxTxt;
    /**
     * This is the InHouse part machine ID text field or the Outsourced part company name text field.
     * */
    public TextField machineOrCompanyTxt;
    /**
     * This is the part min text field.
     * */
    public TextField partMinTxt;

    /**
     * This is the In House part or the Outsourced part label.
     * */
    public Label machineOrCompanyLbl;

    /**
     * This is the In House part radio button.
     * */
    public RadioButton inHouseRBtn;
    /**
     * This is the Outsourced part radio button.
     * */
    public RadioButton outsourcedRBtn;
    /**
     * This is the In House part or Outsourced part toggle group.
     * */
    public ToggleGroup InOutTG;

    /**
     *This is the InHouse Radio Button method.
     *
     * The machineOrCompanyLbl Label text is set to "Machine ID".
     *
     * @param actionEvent InHouse Radio Button is selected.
     * */
    public void onActionInHouseRBtn(ActionEvent actionEvent) {
       machineOrCompanyLbl.setText("Machine ID");
    }

    /**
     *This is the Outsourced Radio Button method.
     *
     * The machineOrCompanyLbl Label text is set to "Company Name".
     *
     * @param actionEvent Outsourced Radio Button is selected.
     * */
    public void onActionOutsourcedRBtn(ActionEvent actionEvent) {
        machineOrCompanyLbl.setText("Company Name");
    }


    /**
     * This is the Display Main method.
     *
     * <p>A confirmation dialog box populates: "All changes will be forgotten, do you wish to continue?".
     *  If the user hits the OK button, the scene shifts to the Main Menu. If cancel is selected, the user
     *  stays in the add part page.</p>
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
     *This is the Save part method.
     *
     * <p>Observable List, allparts, is created and calls the getAllParts method from the Inventory class.
     * part id is assigned 0 (by program initially, as it is a disabled text field), name, price, stock, max,
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
     * <p>If no exceptions are called, part id is assigned with the size of the all parts list + 1.</p>
     *
     * <p>If the InHouse radio button is selected, then the machine id is assigned its associated value.
     * The addPart method is called from the Inventory class and a new InHouse part and its associated
     * values are used as the parameters.</p>
     *
     * <p>If the Outsourced radio button is selected, then the company name is assigned its associated value.
     * The addPart method is called from the Inventory class and a new Outsourced part and its associated
     * values are used as the parameters.</p>
     *
     * <p>The scene is then shifted back to the Main Menu form.</p>
     *
     * @param actionEvent the Save button is clicked
     * */
    public void onActionSavePart(ActionEvent actionEvent) throws IOException {
        System.out.println("Save Part Button Clicked!");

        try {
            ObservableList<Part> allParts = Inventory.getAllParts();

            int id = 0;
            String name = partNameTxt.getText();
            double price = Double.parseDouble(partPriceTxt.getText());
            int stock = Integer.parseInt(partInvTxt.getText());
            int max = Integer.parseInt(partMaxTxt.getText());
            int min = Integer.parseInt(partMinTxt.getText());

            if(!(max > min) || !(min >= 1)) {
                throw new NumberFormatException("Min must be less than Max");
            }
            if( (stock > max) || (stock < min)) {
                throw new NumberFormatException("Inventory Level must be between Min and Max");
            }
            for(int i = 0; i <= allParts.size(); ++i) {
                id = i + 1;
            }
            if(inHouseRBtn.isSelected()) {
                int machineId = Integer.parseInt(machineOrCompanyTxt.getText());

                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
            }
            else {
                String companyName = machineOrCompanyTxt.getText();

                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
            }

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Exception: " + e.getMessage() + ". Please enter a valid value for each Text Field!");
            alert.showAndWait();
        }
    }

    /**
     * This is the initialize method.
     *
     * This is the first method called when the Add Part page is run.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Add Part: I am initialized!");
    }
}
