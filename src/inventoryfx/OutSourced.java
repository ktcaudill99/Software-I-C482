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
public class OutSourced extends Part{
    private String companyName;

    /**
     *
     * @param id
     * @param name
     * @param stock
     * @param price
     * @param min
     * @param max
     * @param companyName
     */
    public OutSourced(int id, String name, int stock, double price, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     *
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     *
     * @return
     */
    public String getCompanyName() {
        return companyName;
    }
}

