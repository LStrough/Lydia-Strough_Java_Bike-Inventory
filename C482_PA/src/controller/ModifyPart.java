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
 * This is the modify part controller.
 *
 *<p>This class allows the user to modify a selected part. The user can manipulate
 * the part name, inventory level, price, max, and min. </p>
 *
 * @author Lydia Strough
 */
public class ModifyPart implements Initializable {


    Stage stage;
    Parent scene;
    /**
     * This is the part being modified.
     * */
    Part partToModify = null;

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
    public void onActionOutsourcedRbtn(ActionEvent actionEvent) {
        machineOrCompanyLbl.setText("Company Name");
    }

    public void modifyPart(Part selectedPart) {
        partToModify = selectedPart;

        partIdTxt.setText(String.valueOf(partToModify.getId()));
        partNameTxt.setText(partToModify.getName());
        partInvTxt.setText(String.valueOf(partToModify.getStock()));
        partPriceTxt.setText(String.valueOf(partToModify.getPrice()));
        partMaxTxt.setText(String.valueOf(partToModify.getMax()));
        partMinTxt.setText(String.valueOf(partToModify.getMin()));

        if (selectedPart instanceof InHouse) {
            machineOrCompanyLbl.setText("Machine ID");
            machineOrCompanyTxt.setText(String.valueOf(((InHouse)partToModify).getMachineId()));
            inHouseRBtn.setSelected(true);
        }
        else {
            machineOrCompanyLbl.setText("Company Name");
            machineOrCompanyTxt.setText(((Outsourced) partToModify).getCompanyName());
            outsourcedRBtn.setSelected(true);
        }
    }

    /**
     * This is the Display Main method.
     *
     * <p>A confirmation dialog box populates: "All changes will be forgotten, do you wish to continue?".
     *  If the user hits the OK button, the scene shifts to the Main Menu. If cancel is selected, the user
     *  stays in the modify part page.</p>
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
     * <p> Part id, name, price, stock, max, and min are assigned values from the partToModify object.</p>
     *
     * <p>The text fields are each checked for exceptions. If an exception is found, warning dialog boxes are
     * called with a specific message associated with each.</p>
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
     * <p>An index variable is created and assigned with the partToModify's index. </p>
     *
     * <p>If the InHouse radio button is selected, then the machine id is assigned its associated value.
     * The updatePart method is called from the Inventory class and a new InHouse part and its associated
     * values are used as the parameters.</p>
     *
     * <p>If the Outsourced radio button is selected, then the company name is assigned its associated value.
     * The updatePart method is called from the Inventory class and a new Outsourced part and its associated
     * values are used as the parameters.</p>
     *
     * <p>The scene is then shifted back to the Main Menu form.</p>
     *
     * @param actionEvent the Save button is clicked
     * */
    public void onActionSavePart(ActionEvent actionEvent) throws IOException {
        System.out.println("Save Part Button Clicked!");

        try{
            int id = partToModify.getId();
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

            int index = Inventory.getAllParts().indexOf(partToModify);

            if(inHouseRBtn.isSelected()) {
                int machineId = Integer.parseInt(machineOrCompanyTxt.getText());

                Inventory.updatePart(index, new InHouse(id, name, price, stock, min, max, machineId));
            }
            else {
                String companyName = machineOrCompanyTxt.getText();

                Inventory.updatePart(index, new Outsourced(id, name, price, stock, min, max, companyName));
            }

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Exception: " + e.getMessage() + ". Please enter a valid value for each Text Field!");
            alert.showAndWait();
        }
    }

    /**
     * This is the initialize method.
     *
     * This is the first method called when the Modify Part page is run.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Modify Part: I am initialized!");
    }
}
