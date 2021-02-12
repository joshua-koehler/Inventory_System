package mvc;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import mvc.model.Inventory;
import mvc.model.Part;

import java.io.IOException;

public class InventoryManagementController {
    @FXML
    private TableColumn part_id_col, part_name_col, part_inventory_col, part_price_col,
                        prod_id_col, prod_name_col, prod_inventory_col, prod_price_col;

    @FXML
    private TableView partsTable, productsTable;

    @FXML
    private static final Inventory inventory = new Inventory();

    public InventoryManagementController(){
        //partsTable.setItems(inventory.getAllParts());
       // productsTable.setItems(inventory.getAllProducts());
        /*
        part_id_col.setCellValueFactory(data ->
                new SimpleStringProperty(data.getId()));
        part_name_col.setCellValueFactory(data ->
                new SimpleStringProperty(data.getName()));
        part_inventory_col.setCellValueFactory(data ->
                new SimpleStringProperty(data.getStock()));
        part_price_col.setCellValueFactory(data ->
                new SimpleStringProperty(data.getPrice()));
                */
    }

    public void addPartToInventory(Part part){
        System.out.println("Part added");
        inventory.addPart(part);
        System.out.println(inventory.getAllParts());
    }

    public void updatePartsTable(){
        partsTable.setItems(inventory.getAllParts());
    }

    public void updateProductsTable(){
        productsTable.setItems(inventory.getAllProducts());
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

    public void navigateToAddProduct(ActionEvent event) throws IOException {
        Parent addProductParent = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene addProductScene = new Scene(addProductParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(addProductScene);
        window.show();
    }
}
