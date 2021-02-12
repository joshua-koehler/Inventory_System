package mvc;

import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class InventoryManagementController {
    @FXML
    private static TableColumn<Part, String> part_id_col, part_name_col, part_inventory_col, part_price_col,
                        prod_id_col, prod_name_col, prod_inventory_col, prod_price_col;

    @FXML
    public TableView<Part> partsTable = new TableView<>();
    public TableView<Product> productsTable = new TableView<>();

    @FXML
    public static final Inventory inventory = new Inventory();

    @FXML
    private Label errorLabel;

    public InventoryManagementController(){
        part_id_col = new TableColumn<>("Part ID");
        part_name_col = new TableColumn<>("Name");
        part_inventory_col = new TableColumn<>("Inventory");
        part_price_col = new TableColumn<>("Price/Cost");

        part_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        part_name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        part_inventory_col.setCellValueFactory(new PropertyValueFactory<>("inventory"));
        part_price_col.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.getColumns().setAll(part_id_col, part_name_col, part_inventory_col, part_price_col);

        partsTable.setItems(inventory.getAllParts());
        productsTable.setItems(inventory.getAllProducts());
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

    public void navigateToModifyPart(ActionEvent event) throws IOException {
        ObservableList<Part> selectedParts, allParts;
        allParts = partsTable.getItems();
        selectedParts = partsTable.getSelectionModel().getSelectedItems();
        if(selectedParts == null){
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

    public void deletePart(){
        ObservableList<Part> selectedParts, allParts;
        allParts = partsTable.getItems();
        selectedParts = partsTable.getSelectionModel().getSelectedItems();

        try {
            for (Part part : selectedParts) {
                allParts.remove(part);
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
        // TODO
    }

    public void deleteProduct(ActionEvent event) throws IOException{
        // TODO
    }
}
