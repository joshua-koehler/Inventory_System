/**
 * Inventory class stores parts and products for CRUD
 */

/**
 *
 * @author Joshua Koehler
 */

package mvc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Stores all part and product data and facilitates CRUD operations.
 */
public class Inventory {
    public static int partId = 0;
    public static int productId = 0;

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * @param part to add
     */
    public void addPart(Part part){
        part.setId(partId++); //auto-increment
        this.allParts.addAll(part);
    }

    /**
     * @param index in allParts list
     * @param updatedPart to replace part at current index
     */
    public void updatePart(int index, Part updatedPart){
        allParts.set(index, updatedPart);
    }

    /**
     * @param partToDelete
     */
    public void deletePart(Part partToDelete) {
        allParts.remove(partToDelete);
    }

    /**
     * @return all Parts in Inventory
     */
    public ObservableList<Part> getAllParts(){
        return allParts;
    }


    /**
     * @param product to add
     */
    public void addProduct(Product product){
        product.setId(productId++); //auto increment
        allProducts.add(product);
    }
    /**
     * @param index in allProducts list
     * @param updatedProduct to replace product at current index
     */
    public void updateProduct(int index, Product updatedProduct){
        allProducts.remove(index);
        allProducts.add(index, updatedProduct);
    }

    /**
     * @param productToDelete
     */
    public boolean deleteProduct(Product productToDelete) {
        if(productToDelete.getAllAssociatedParts().size() > 0){
            return false; // cannot delete product with associated parts
        }
        allProducts.remove(productToDelete);
        return true;
    }

    /**
     * @return all Products in Inventory
     */
    public ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}
