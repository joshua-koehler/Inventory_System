package mvc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("InventoryManagement.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 1200, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        /*
        Part part1 = new InHouse(1, "part1", 23.23, 13, 1, 10, 1131);
        Part part2 = new Outsourced(2, "part2", 104.44, 4, 2, 12, "Outsource Co.");
        Part part3 = new Outsourced(3, "part3", 102.22, 2, 2, 12, "Outsource Co.");
        Product prod3 = new Product(3, "prod1", 103.33, 3, 3, 13);
        Product prod4 = new Product(5, "prod2", 105.55, 5, 5, 15);

        Inventory inventory = new Inventory();
        inventory.addPart(part1);
        inventory.addPart(part2);
        inventory.addPart(part3);

        inventory.addProduct(prod3);
        inventory.addProduct(prod4);

         */

        launch(args);
    }
}
