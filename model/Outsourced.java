package model;

/**
 * Class for Outsourced parts.
 *
 * @author Erik Nilson
 */
public class Outsourced extends Part {

    /**
     * The Company Name for the part.
     *
     */
    private String companyName;

    /**
     * The constructor for the Outsourced Part.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * The getter for the companyName
     *
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * The setter for the companyName
     *
     * @param companyName the id to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
