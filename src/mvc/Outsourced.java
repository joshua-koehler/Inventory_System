/**
 *  Outsourced class extends Part class
 */

/**
 * @author Joshua Koehler
 */

package mvc;

/**
 * Outsourced class extends Part class and adds a companyName class variable with a getter and setter method.
 */
public class Outsourced extends Part {
    protected String companyName;

    /**
     * Constructor sets Part class variables along with companyName.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return company name
     */
    public String getCompanyName() {
        return this.companyName;
    }

    /**
     * @param name of company
     */
    public void setCompanyName(String name){
        this.companyName = name;
    }
}