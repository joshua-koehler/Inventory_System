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
import javafx.collections.transformation.FilteredList;

import java.util.function.Predicate;

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
     * @param partId
     * @return Part matching partId
     */
    public Part lookupPart(int partId){
        for(Part part : allParts){
            if(part.getId() == partId){
                return part;
            }
        }
        return null;
    }
    /**
     * @param searchTerm
     * @return returns a list of all parts containing the search term
     */
    public ObservableList lookupPart(String searchTerm) {
        Predicate<Part> predicate = new Predicate<Part>() {
            @Override
            public boolean test(Part part) {
                return part.getName().contains(searchTerm);
            }
        };
        return new FilteredList<Part>(allParts, predicate);
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

    /**
     * @param productId
     * @return Product matching productId
     */
    public Product lookupProduct(int productId){
        for(Product product : allProducts){
            if(product.getId() == productId){
                return product;
            }
        }
        return null;
    }
    /**
     * @param searchTerm
     * @return returns a list of all products containing the search term
     */
    public ObservableList lookupProduct(String searchTerm) {
        Predicate<Product> predicate = new Predicate<Product>() {
            @Override
            public boolean test(Product product) {
                return product.getName().contains(searchTerm);
            }
        };
        return new FilteredList<Product>(allProducts, predicate);
    }
}
