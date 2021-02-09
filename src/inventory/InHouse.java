/**
 *  InHouse class - extends supplied Part class
 */

/**
 * @author Joshua Koehler
 */

package inventory;

public class InHouse extends Part {
    protected int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
        if(min > max)
            System.out.println("WARNING: Min of " + min + " is greater than max of " + max);
        if(stock < min)
            System.out.println("stock:" + stock + " below min:" + min);
        if(stock > max)
            System.out.println("stock:" + stock + " exceeds max:" + max);
    }

    /**
     * @return machine id
     */
    public int getMachineId() {
        return this.machineId;
    }

    /**
     * @param id of machine
     */
    public void setMachineId(int id){
        this.machineId = id;
    }
}
