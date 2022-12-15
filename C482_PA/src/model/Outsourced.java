package model;

/**
 *This class is for Outsourced Parts.
 *
 * @author Lydia Strough
 */
public class Outsourced extends Part{
    /**
     * This is the Outsourced part company name.
     * */
    private String companyName;

    /**
     * This is the Outsourced part constructor.
     *
     * @param id part id
     * @param name part name
     * @param price part price
     * @param stock part inventory level
     * @param min min
     * @param max max
     * @param companyName outsourced part company name
     * */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;

    }

    /**
     * @param companyName the company name to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }
}
