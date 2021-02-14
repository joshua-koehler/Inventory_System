package mvc;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * Controller for main page, manages data via instance of mvc.Inventory class.
 */
public class InventoryManagementController {
    @FXML
    private static TableColumn<Part, String> part_id_col, part_name_col, part_inventory_col, part_price_col;

    @FXML
    private static TableColumn<Product, String> prod_id_col, prod_name_col, prod_inventory_col, prod_price_col;

    @FXML
    public TableView<Part> partsTable = new TableView<>();

    @FXML
    public TableView<Product> productsTable = new TableView<>();

    @FXML
    public static final Inventory inventory = new Inventory();

    @FXML
    public Label errorLabel, successLabel;

    @FXML
    private TextField partSearch = new TextField();

    @FXML
    private TextField productSearch = new TextField();

    /**
     * Constructor - used to set up partsTable and productsTable to display data.
     */
    public InventoryManagementController(){
        part_id_col = new TableColumn<>("Part ID");
        part_name_col = new TableColumn<>("Name");
        part_inventory_col = new TableColumn<>("Inventory");
        part_price_col = new TableColumn<>("Price/Cost");

        part_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        part_name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        part_inventory_col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        part_price_col.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.getColumns().setAll(part_id_col, part_name_col, part_inventory_col, part_price_col);

        prod_id_col = new TableColumn<>("Part ID");
        prod_name_col = new TableColumn<>("Name");
        prod_inventory_col = new TableColumn<>("Inventory");
        prod_price_col = new TableColumn<>("Price/Cost");

        prod_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        prod_name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        prod_inventory_col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prod_price_col.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.getColumns().setAll(prod_id_col, prod_name_col, prod_inventory_col, prod_price_col);
    }

    /**
     * @param part
     */
    public static void addPartToInventory(Part part){
        inventory.addPart(part);
    }

    /**
     * Exits the program.
     */
    public void exit() {
        Platform.exit();
    }

    /**
     * Redirects to AddPart.fxml view.
     * @param event
     * @throws IOException
     */
    public void navigateToAddPart(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(addPartScene);
        window.show();
    }

    /**
     * Redirects to ModifyPart.fxml view.
     * @param event
     * @throws IOException
     */
    @FXML
    public void navigateToModifyPart(ActionEvent event) throws IOException {
        ObservableList<Part> selectedParts, allParts;
        allParts = partsTable.getItems();
        selectedParts = partsTable.getSelectionModel().getSelectedItems();
        if(selectedParts.isEmpty()){
            errorLabel.setText("Please select a part to modify");
            return;
        }

        Part partToModify = null;
        try {
            for (Part part : selectedParts) {
                partToModify = part;
            }
        }
        catch(Exception e){
            System.out.println("Exception caught: " + e);
        }
        int index = allParts.indexOf(partToModify);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyPart.fxml"));
        Parent addPartParent = loader.load();

        ModifyPartController modifyPartController = loader.getController();
        modifyPartController.setPartToModify(index, partToModify);

        Scene addPartScene = new Scene(addPartParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(addPartScene);
        window.show();
    }

    /**
     * Special Java FXML method.
     * Used here to set table placeholders and to set up filtered searching for parts and products Tables.
     */
    @FXML
    public void initialize(){
        partsTable.setPlaceholder(new Label("No parts found."));
        productsTable.setPlaceholder(new Label("No products found."));

        // 1. Initialize parts list
        FilteredList<Part> filteredData = new FilteredList<>(inventory.getAllParts(), p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        partSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name field in your object with filter.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(myObject.getName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;// Filter matches first name.

                } else if (String.valueOf(myObject.getId()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }

                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Part> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(partsTable.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        partsTable.setItems(sortedData);


        // 1. Initialize product list
        FilteredList<Product> filteredProductData = new FilteredList<>(inventory.getAllProducts(), p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        productSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredProductData.setPredicate(myObject -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name field in your object with filter.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(myObject.getName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;// Filter matches first name.

                } else if (String.valueOf(myObject.getId()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }

                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Product> sortedProductData = new SortedList<>(filteredProductData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedProductData.comparatorProperty().bind(productsTable.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        productsTable.setItems(sortedProductData);
    }


    /**
     * Displays dialog to confirm operation on an object.
     * @param objectName
     * @param operationName
     * @return userConfirmed boolean - true if user confirmed
     */
    public static boolean userConfirm(String objectName, String operationName){
        String title = operationName + " " + objectName;
        String header = "This operation will " + operationName.toLowerCase() + " the selected " + objectName.toLowerCase();
        String question = "Is that your intention?";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(question);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
            // ... user chose OK
        } else {
            return false;
            // ... user chose CANCEL or closed the dialog
        }
    }

    /**
     * Deletes selected part on user confirm.
     */
    public void deletePart(){
        errorLabel.setText("");
        successLabel.setText("");
        ObservableList<Part> selectedParts;
        selectedParts = partsTable.getSelectionModel().getSelectedItems();

        if(selectedParts.isEmpty()){
            errorLabel.setText("Please select a part to delete.");
            return;
        }

        String objectName = "Part";
        String operationName = "Delete";
        if(!userConfirm(objectName, operationName)) return; // user cancels delete

        try{
            for (Part part : selectedParts) {
                inventory.deletePart(part);
            }
        }
        catch(Exception e){
            //do nothing when last element is deleted
        }
    }

    /**
     * Navigates to AddProduct.fxml on ActionEvent.
     * @param event
     * @throws IOException
     */
    public void navigateToAddProduct(ActionEvent event) throws IOException {
        Parent addProductParent = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene addProductScene = new Scene(addProductParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(addProductScene);
        window.show();
    }

    /**
     * Navigates to ModifyProduct.fxml on ActionEvent, sending selected product to ModifyProductController.
     * @param event
     * @throws IOException
     */
    public void navigateToModifyProduct(ActionEvent event) throws IOException{
        ObservableList<Product> selectedProducts, allProducts;
        selectedProducts = productsTable.getSelectionModel().getSelectedItems();
        if(selectedProducts.isEmpty()){
            successLabel.setText("");
            errorLabel.setText("Please select a product to modify");
            return;
        }

        Product productToModify = null;
        try {
            for (Product product : selectedProducts) {
                productToModify = product;
            }
        }
        catch(Exception e){
            System.out.println("Exception caught: " + e);
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyProduct.fxml"));
        Parent addProductParent = loader.load();

        ModifyProductController modifyProductController = loader.getController();
        modifyProductController.setProductToModify(productToModify);

        Scene addProductScene = new Scene(addProductParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(addProductScene);
        window.show();
    }

    /**
     * Deletes selected product upon user confirmation.
     * @param event
     * @throws IOException
     */
    public void deleteProduct(ActionEvent event) throws IOException{
        errorLabel.setText("");
        successLabel.setText("");
        ObservableList<Product> selectedProducts;
        selectedProducts = productsTable.getSelectionModel().getSelectedItems();

        if(selectedProducts.isEmpty()){
            errorLabel.setText("Please select a product to delete.");
            return;
        }

        String objectName = "Product";
        String operationName = "Delete";
        if(!userConfirm(objectName, operationName)) return; // user cancels delete

        boolean isSuccessful = false;
        try{
            for (Product product : selectedProducts) {
                isSuccessful = inventory.deleteProduct(product);
            }
        }
        catch(Exception e){
            //do nothing when last element is deleted
        }
        if(!isSuccessful){
            errorLabel.setText("Cannot delete a product with associated parts.");
            successLabel.setText("");
        }
        else {
            errorLabel.setText("");
            successLabel.setText("Deleted successfully.");
        }
    }
}