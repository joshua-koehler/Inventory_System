/**
 *  Outsourced class extends Part class
 */

/**
 * @author Joshua Koehler
 */

package mvc;

public class Outsourced extends Part {
    protected String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
        if(min > max)
            System.out.println("WARNING: Min:" + min + " is greater than max:" + max);
        if(stock < min)
            System.out.println("stock:" + stock + " below min:" + min);
        if(stock > max)
            System.out.println("stock:" + stock + " exceeds max:" + max);
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