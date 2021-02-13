package mvc;

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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

import static mvc.InventoryManagementController.inventory;

public class AddProductController {
    @FXML
    private TextField id, name, inv, price, max, min, partSearch;

    @FXML
    private Label errorLabel, successLabel;

    @FXML
    private static TableColumn<Part, String> part_id_col, part_name_col, part_inventory_col, part_price_col;

    @FXML
    private static TableColumn<Part, String> associated_part_id_col, associated_part_name_col, associated_part_inventory_col, associated_part_price_col;

    @FXML
    private TableView<Part> partsTable = new TableView<>();

    @FXML
    private TableView<Part> associatedPartsTable = new TableView<>();

    @FXML
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    public AddProductController(){
        part_id_col = new TableColumn<>("Part ID");
        part_name_col = new TableColumn<>("Name");
        part_inventory_col = new TableColumn<>("Inventory");
        part_price_col = new TableColumn<>("Price/Cost");

        part_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        part_name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        part_inventory_col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        part_price_col.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.getColumns().setAll(part_id_col, part_name_col, part_inventory_col, part_price_col);

        associated_part_id_col = new TableColumn<>("Part ID");
        associated_part_name_col = new TableColumn<>("Name");
        associated_part_inventory_col = new TableColumn<>("Inventory");
        associated_part_price_col = new TableColumn<>("Price/Cost");

        associated_part_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        associated_part_name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        associated_part_inventory_col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associated_part_price_col.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTable.getColumns().setAll(associated_part_id_col, associated_part_name_col, associated_part_inventory_col, associated_part_price_col);
    }

    public void navigateToInventoryManagement(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("InventoryManagement.fxml"));
        Parent inventoryManagementParent = loader.load();

        InventoryManagementController inventoryManagementController = loader.getController();

        Scene inventoryManagementScene = new Scene(inventoryManagementParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(inventoryManagementScene);
        window.show();
    }

    public void saveProduct() throws IOException {
        Product product = getProduct();
        if(product != null){//add to products list
            String msg = successLabel.getText();
            successLabel.setText(msg + "Product created successfully\n");
            errorLabel.setText("");
            saveProduct(product);
        } else{
            // invalid product
            successLabel.setText("");
        }
    }

    public Product getProduct(){
        StringBuilder sb = new StringBuilder();
        Product product = null;
        errorLabel.setText("");
        int id_=0, stock_=0, max_=0, min_=0;
        String name_;
        double price_=0.0;

        name_ = name.getText();
        try {
            stock_ = Integer.parseInt(inv.getText());
            if(stock_ < 0) throw new NumberFormatException();
        }
        catch(NumberFormatException e) {
            sb.append("Stock must be a positive integer.\n");
        }
        try {
            price_ = Double.parseDouble(price.getText());
            if(price_ < 0) throw new NumberFormatException();
        }
        catch(NumberFormatException e) {
            sb.append("Price must be a positive decimal value.\n");
        }
        try {
            max_ = Integer.parseInt(max.getText());
            if(max_ < 0) throw new NumberFormatException();
        }
        catch(NumberFormatException e) {
            sb.append("Max must be a positive integer.\n");
        }
        try {
            min_ = Integer.parseInt(min.getText());
            if(min_ < 0) throw new NumberFormatException();
        }
        catch(NumberFormatException e) {
            sb.append("Min must be a positive integer.\n");
        }
        try{
            if(stock_ > max_){
                sb.append("Inventory cannot exceed max.\n");
            }
            if(stock_ < min_){
                sb.append("Inventory cannot be less than min.\n");
            }
            if(min_ > max_){
                sb.append("Min cannot be less than max.\n");
            }
        }
        catch(NullPointerException e){
            //do nothing - something earlier failed and is already handled.
        }
        catch(Exception e){
            System.out.println("Exception caught " + e);
        }
        errorLabel.setText(sb.toString());
        if(sb.toString().length() > 0){ // some failure occurred
            successLabel.setText("");// remove success message after failure
        }
        else {
            product = new Product(id_, name_, price_, stock_, min_, max_);
            for (Part part : associatedPartsList) {
                product.addAssociatedPart(part);
            }
        }
        return product;
    }

    public void saveProduct(Product product){
        inventory.addProduct(product);
    }

    public void addPart(){
        ObservableList<Part> selectedParts;
        selectedParts = partsTable.getSelectionModel().getSelectedItems();
        if(selectedParts.isEmpty()){
            errorLabel.setText("Please select a part to modify");
            return;
        }

        Part partToAdd = null;
        try {
            for (Part part : selectedParts) {
                partToAdd = part;
            }
        }
        catch(Exception e){
            System.out.println("Exception caught: " + e);
        }

        associatedPartsList.add(partToAdd);
    }

    public void removePart(){
        ObservableList<Part> selectedParts;
        selectedParts = associatedPartsTable.getSelectionModel().getSelectedItems();
        if(selectedParts.isEmpty()){
            errorLabel.setText("Please select a part to modify");
            return;
        }

        Part partToRemove = null;
        try {
            for (Part part : selectedParts) {
                partToRemove = part;
            }
        }
        catch(Exception e){
            System.out.println("Exception caught: " + e);
        }

        associatedPartsList.remove(partToRemove);
    }

    @FXML
    public void initialize() {
        id.setDisable(true);
        id.setText("Auto-Gen - Disabled");

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


        // 1. Initialize associated parts list
        FilteredList<Part> filteredAssociatedData = new FilteredList<>(associatedPartsList, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        // don't need a listener for this because no search

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Part> sortedAssociatedData = new SortedList<>(filteredAssociatedData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedAssociatedData.comparatorProperty().bind(associatedPartsTable.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        associatedPartsTable.setItems(sortedAssociatedData);
    }

}
