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

import static mvc.InventoryManagementController.addPartToInventory;

/**
 * Facilitates the adding of parts to the inventory.
 */
public class AddPartController {
    @FXML
    private TextField id, name, inv, price, max, min, machineOrCompanyField;

    @FXML
    private Label machineOrCompanyLabel, errorLabel, successLabel;

    @FXML
    private RadioButton inHouse, outsourced;

    /**
     * This special FXML method runs on initialization of the controller.
     * The function is used here to disable the id field.
     */
    @FXML
    public void initialize(){
        id.setDisable(true);
        id.setText("Auto-Gen - Disabled");
    }

    /**
     * @param event - ActionEvent passed in from a user action
     */
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

    /**
     * @param event - ActionEvent passed in from a user action
     * @param msg - success message displayed in inventory management after redirect.
     */
    public void navigateToInventoryManagementWithSuccessMessage(ActionEvent event, String msg) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("InventoryManagement.fxml"));
        Parent inventoryManagementParent = loader.load();

        InventoryManagementController inventoryManagementController = loader.getController();

        inventoryManagementController.successLabel.setText(msg);

        Scene inventoryManagementScene = new Scene(inventoryManagementParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(inventoryManagementScene);
        window.show();
    }

    /**
     * @param event - ActionEvent passed in from a user action
     * Saves part to inventory and redirects to main screen if all user fields are valid.
     */
    public void savePart(ActionEvent event) throws IOException {
        Part part = getPart();
        if(part != null){//add to parts list
            String msg = "Part created successfully\n";
            errorLabel.setText("");
            navigateToInventoryManagementWithSuccessMessage(event, msg);
            addPartToInventory(part);
        } else{
            // invalid part
        }
    }

    /**
     * Parses user supplied fields, displaying an error message for each invalid input.
     * Returns null if there were one or more invalid input fields.
     * @return part - a Part of type InHouse or Outsourced depending on radio toggle.
     */
    public Part getPart(){
        StringBuilder sb = new StringBuilder();
        Part part = null;
        errorLabel.setText("");
        int id_=0, stock_=0, max_=0, min_=0, machineID_=0;
        String name_, companyName_;
        double price_=0.0;

        name_ = name.getText();
        if(name_.length() == 0){
            sb.append("Name required.\n");
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
            }
        }
        else if(outsourced.isSelected()){
            companyName_ = machineOrCompanyField.getText();
            if(companyName_.length() == 0){
                sb.append("Company name required.\n");
            }
            if(sb.toString().length() == 0) {// no error
                part = new Outsourced(id_, name_, price_, stock_, min_, max_, companyName_);
            }
        }
        errorLabel.setText(sb.toString());
        if(sb.toString().length() > 0){// remove success message after failure
            successLabel.setText("");
            return null;
        }
        else{
            errorLabel.setText("");
        }
        return part;
    }

    /**
     * Selects Outsourced radio toggle.
     */
    public void outsourcedSelected(){
        inHouse.setSelected(false);
        errorLabel.setText("");
        successLabel.setText("");
        machineOrCompanyLabel.setText("Company Name");
    }

    /**
     * Selects InHouse radio toggle.
     */
    public void inHouseSelected(){
        outsourced.setSelected(false);
        errorLabel.setText("");
        successLabel.setText("");
        machineOrCompanyLabel.setText("Machine ID");
    }
}
