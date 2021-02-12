/**
 * Inventory class stores parts and products for CRUD
 */

/**
 *
 * @author Joshua Koehler
 */

package mvc;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.function.Predicate;

public class Inventory {
    protected ObservableList<Part> allParts;
    protected ObservableList<Product> allProducts;

    public Inventory(){
        this.allParts = new SimpleListProperty<Part>(FXCollections.observableArrayList());
        this.allProducts = new SimpleListProperty<Product>(FXCollections.observableArrayList());
    }
    /**
     * @param part to add
     */
    public void addPart(Part part){
        this.allParts.add(part);
    }

    /**
     * @param partId
     * lookup part by partId
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
     * @param index in allParts list
     * @param updatedPart to replace part at current index
     */
    public void updatePart(int index, Part updatedPart){
        allParts.remove(index);
        allParts.add(index, updatedPart);
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
        allProducts.add(product);
    }

    /**
     * @param productId
     * lookup product by productId
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
    public void deleteProduct(Product productToDelete) {
        if(productToDelete.getAllAssociatedParts().size() > 0){
            System.out.println("Cannot delete product with associated parts");
            return;
        }
        allProducts.remove(productToDelete);
    }

    /**
     * @return all Products in Inventory
     */
    public ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}
