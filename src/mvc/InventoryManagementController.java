package mvc;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private TableView<Part> partsTable = new TableView<Part>();
    private TableView<Product> productsTable = new TableView<Product>();

    @FXML
    private static final Inventory inventory = new Inventory();

    public InventoryManagementController(){
        Part part1 = new InHouse(123, "part1", 23.23, 13, 1, 10, 1131);
        inventory.addPart(part1);

        part_id_col = new TableColumn<>("Part ID");
        part_name_col = new TableColumn<>("Name");
        part_inventory_col = new TableColumn<>("Inventory");
        part_price_col = new TableColumn<>("Price/Cost");

        part_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        part_name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        part_inventory_col.setCellValueFactory(new PropertyValueFactory<>("inventory"));
        part_price_col.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.getColumns().setAll(part_id_col, part_name_col, part_inventory_col, part_price_col);

        // productsTable.setItems(inventory.getAllProducts());
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
