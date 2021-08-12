/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventoryfx;

/**
 *
 * @author Katherine Caudill
 */
public class InHouse extends Part{
    private int machineID;

    /**
     *
     * @param id
     * @param name
     * @param stock
     * @param price
     * @param min
     * @param max
     * @param machineID
     */
    public InHouse(int id, String name, int stock, double price, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**
     *
     * @param machineID
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    /**
     *
     * @return
     */
    public int getMachineID() {
        return machineID;
    }
}