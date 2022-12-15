package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/**
 *This class is the Main class.
 *
 *<p>FUTURE ENHANCEMENT: Add a "count" column to the associated parts table (partBTableView) that will count
 * the number of each part that is associated with each product. This will make this table less "messy".</p>
 *
 * @author Lydia Strough
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stage.setTitle("Lydia Strough, C482 PA");
        stage.setScene(new Scene(root, 1200, 450));
        stage.show();
    }

    /**
    * This is the add test data method.
    * This method creates test parts and products and adds them to my Inventory and Product classes.
    */
    private static void addTestData() {

        InHouse part1 = new InHouse(1, "Brakes", 12.99, 10, 1, 20, 111);
        InHouse part2 = new InHouse(2, "Wheel", 11, 16, 1, 20, 222);
        InHouse part3 = new InHouse(3, "Seat", 15, 10, 1, 10, 333);
        Outsourced part4 = new Outsourced(4, "Rim", 56.99,15,1,20, "Super Bikes");
        Outsourced part5 = new Outsourced(5, "Handlebars", 39.99,8,1,10, "Super Bikes");
        Outsourced part6 = new Outsourced(6, "Tire", 14.99, 18, 1, 20, "Gatorskin");

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        Inventory.addPart(part6);

        Product prod1 = new Product(1000, "Giant Bike", 299.99, 5, 1, 5);
        Product prod2 = new Product(1001, "Tricycle", 99.99, 5, 1, 10);
        Product prod3 = new Product(1002, "Unicycle", 99.99, 10, 1, 20);

        Product.addProduct(prod1);
        Product.addProduct(prod2);
        Product.addProduct(prod3);
    }
    /**
    * This is the main method.
    * This is the first method that gets called when the java program is run. The addTestData method is called before launch.
    */
    public static void main(String[] args) {
        addTestData();

        launch(args);
    }
}