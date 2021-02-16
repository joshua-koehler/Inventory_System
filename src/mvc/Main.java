
package mvc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class bootstraps JavaFX application.
 */

/**
 * FUTURE ENHANCEMENT
 * Currently there are separate views and controllers for modify and add operations for both part and product.
 * It is possible to reduce the code by merging these into a single view for each.
 * This could be accomplished through creating an abstract view and/or controller and implementing this.
 * Alternatively, a simple dynamic modification of the DOM via a single controller would be sufficient and more elegant.
 */

/**
 * Main class extends Javafx Application class to enable running the GUI.
 */
public class Main extends Application {

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned, and after the system is ready for the application to begin running.
     * Used here to load the main page.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("InventoryManagement.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 1200, 500));
        primaryStage.show();
    }

    /**
     * Javadoc files located in resources/Javadoc_files
     */
    /**
     * Launch application.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
