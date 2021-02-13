package mvc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static mvc.InventoryManagementController.inventory;

public class ModifyPartController {
    @FXML
    private TextField id, name, inv, price, max, min, machineOrCompanyField;

    @FXML
    private Label machineOrCompanyLabel, errorLabel, successLabel;

    @FXML
    private RadioButton inHouse, outsourced;

    private Part partToModify;
    private int index;

    public void setPartToModify(int index, Part partToModify) {
        this.partToModify = partToModify;
        this.index = index;
        populateFields();
    }

    public void populateFields(){
        id.setText(String.valueOf(partToModify.getId()));
        id.setDisable(true);

        name.setText(String.valueOf(partToModify.getName()));
        inv.setText(String.valueOf(partToModify.getStock()));
        price.setText(String.valueOf(partToModify.getPrice()));
        max.setText(String.valueOf(partToModify.getMax()));
        min.setText(String.valueOf(partToModify.getMin()));

        if(partToModify.getClass() == InHouse.class){
            InHouse inHousePart = (InHouse) partToModify;
            machineOrCompanyField.setText(String.valueOf(inHousePart.getMachineId()));
            inHouseSelected();
        }
        else if(partToModify.getClass() == Outsourced.class){
            Outsourced outsourcedPart = (Outsourced) partToModify;
            machineOrCompanyField.setText(String.valueOf(outsourcedPart.getCompanyName()));
            outsourcedSelected();
        }
        else {
            // part of neither Outsourced nor InHouse type - fail silently
        }
    }

    public void modifyPartAndNavigateToInventoryManagement(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("InventoryManagement.fxml"));
        Parent inventoryManagementParent = loader.load();

        InventoryManagementController inventoryManagementController = loader.getController();

        partToModify = getPart();
        if(partToModify == null) return; //error message will be displayed
        inventory.updatePart(index, partToModify);
        inventoryManagementController.errorLabel.setText("");
        inventoryManagementController.successLabel.setText("Part modified successfully");

        Scene inventoryManagementScene = new Scene(inventoryManagementParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(inventoryManagementScene);
        window.show();
    }

    public void navigateToInventoryManagement(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("InventoryManagement.fxml"));
        Parent inventoryManagementParent = loader.load();

        InventoryManagementController inventoryManagementController = loader.getController();

        inventoryManagementController.successLabel.setText("");
        inventoryManagementController.errorLabel.setText("");

        Scene inventoryManagementScene = new Scene(inventoryManagementParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(inventoryManagementScene);
        window.show();
    }

    public Part getPart(){
        StringBuilder sb = new StringBuilder();
        Part part = null;
        errorLabel.setText("");
        int id_=0, stock_=0, max_=0, min_=0, machineID_=0;
        String name_, companyName_;
        double price_=0.0;

        name_ = name.getText();
        try {
            id_ = partToModify.getId();
        }
        catch(Exception e) {
            System.out.println("Exception caught: " + e);
        }
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
            //do nothing - something earlier failed and will log out.
        }
        catch(Exception e) {
            System.out.println("Exception caught " + e);
        }
        if(inHouse.isSelected()){
            try {
                machineID_ = Integer.parseInt(machineOrCompanyField.getText());
                if(machineID_ < 0) throw new NumberFormatException();
            }
            catch(NumberFormatException e) {
                sb.append("Machine ID must be a positive integer.\n");
            }
            if(sb.toString().length() == 0) {// no error
                part = new InHouse(id_, name_, price_, stock_, min_, max_, machineID_);
                part.setId(id_);//override autogenerate
            }
        }
        else if(outsourced.isSelected()){
            companyName_ = machineOrCompanyField.getText();
            if(sb.toString().length() == 0) {// no error
                part = new Outsourced(id_, name_, price_, stock_, min_, max_, companyName_);
                part.setId(id_);//override autogenerate
            }
        }
        errorLabel.setText(sb.toString());
        if(sb.toString().length() > 0){// remove success message after failure
            successLabel.setText("");
        }
        inventory.partId--;//reset autogenerate increment
        return part;
    }

    public void outsourcedSelected(){
        inHouse.setSelected(false);
        outsourced.setSelected(true);
        errorLabel.setText("");
        successLabel.setText("");
        machineOrCompanyLabel.setText("Company Name");
    }

    public void inHouseSelected(){
        outsourced.setSelected(false);
        inHouse.setSelected(true);
        errorLabel.setText("");
        successLabel.setText("");
        machineOrCompanyLabel.setText("Machine ID");
    }
}
