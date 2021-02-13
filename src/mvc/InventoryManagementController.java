package mvc;

import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

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
    private Label errorLabel;

    @FXML
    private TextField partSearch = new TextField();

    @FXML
    private TextField productSearch = new TextField();

    public InventoryManagementController(){
        part_id_col = new TableColumn<>("Part ID");
        part_name_col = new TableColumn<>("Name");
        part_inventory_col = new TableColumn<>("Inventory");
        part_price_col = new TableColumn<>("Price/Cost");

        part_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        part_name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        part_inventory_col.setCellValueFactory(new PropertyValueFactory<>("inventory"));//should be "stock" test to verify
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

    public void addPartToInventory(Part part){
        System.out.println("Part added");
        inventory.addPart(part);
        System.out.println("Current parts list: " + inventory.getAllParts());
    }

    public void updatePartsTable(){
        System.out.println("Update parts table called but commented out.");
        //partsTable.setItems(inventory.getAllParts());
    }

    public void updateProductsTable(){
        System.out.println("Update products table called but commented out.");
        //productsTable.setItems(inventory.getAllProducts());
    }

    public void exit() {
        Platform.exit();
    }

    public void navigateToAddPart(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(addPartScene);
        window.show();
    }

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
            System.out.println("I caught this: " + e);
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

    @FXML
    public void initialize(){
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

    public void deletePart(){
        ObservableList<Part> selectedParts;
        selectedParts = partsTable.getSelectionModel().getSelectedItems();

        try{
            for (Part part : selectedParts) {
                inventory.deletePart(part);
            }
        }
        catch(Exception e){
            //do nothing when last element is deleted
        }
    }

    public void navigateToAddProduct(ActionEvent event) throws IOException {
        Parent addProductParent = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene addProductScene = new Scene(addProductParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(addProductScene);
        window.show();
    }

    public void navigateToModifyProduct(ActionEvent event) throws IOException{
        Parent addProductParent = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
        Scene addProductScene = new Scene(addProductParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(addProductScene);
        window.show();
    }

    public void deleteProduct(ActionEvent event) throws IOException{
        ObservableList<Product> selectedProducts;
        selectedProducts = productsTable.getSelectionModel().getSelectedItems();

        try{
            for (Product product : selectedProducts) {
                inventory.deleteProduct(product);
            }
        }
        catch(Exception e){
            //do nothing when last element is deleted
        }
    }
}
