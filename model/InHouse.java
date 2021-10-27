package model;

/**
 * Class for InHouse parts.
 *
 * @author Erik Nilson
 */
public class InHouse extends Part {

    /**
     * The Machine ID for the part.
     *
     */
    private int machineId;

    /**
     * The constructor for the InHouse Part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * The getter for the machineId
     *
     * @return the machineId
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * The setter for the machineId
     *
     * @param machineId the id to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

}