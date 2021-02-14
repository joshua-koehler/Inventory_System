/**
 *  InHouse class - extends supplied Part class
 */

/**
 * @author Joshua Koehler
 */

package mvc;

/**
 * InHouse class extends Part class with additional MachineID integer field.
 */
public class InHouse extends Part {
    protected int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
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
