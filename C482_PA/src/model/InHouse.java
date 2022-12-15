package model;

/**
 *This class is for In House Parts.
 *
 * @author Lydia Strough
 */
public class InHouse extends Part{
    /**
     * This is the In House Part Machine ID.
     * */
    private int machineId;

    /**
     * This is the In house part constructor.
     *
     * @param id part id
     * @param name part name
     * @param price part price
     * @param stock part inventory level
     * @param min min
     * @param max max
     * @param machineId In house part machine ID
     * */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @param machineId the machine ID to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * @return the machineId
     */
    public int getMachineId() {
        return machineId;
    }
}
