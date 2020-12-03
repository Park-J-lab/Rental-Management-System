package ReportUtils;

/*
* This class is used to record the information of an apartment
* */

public class Apartment {
    private final String complexID; // the id of the complex it belongs to
    private final String aptID; // the id of the apartment
    private final double area; // the area (square meter)
    private final double rent; // the rent per month
    private final double fine; // the penalty tacked on to the rent that is paid after the grace period
    private final String leasedOrNot;

    public Apartment(String complexID, String aptID, double area,
                     double rent, double fine, String leasedOrNot) {
        this.complexID = complexID;
        this.aptID = aptID;
        this.area = area;
        this.rent = rent;
        this.fine = fine;
        this.leasedOrNot = leasedOrNot;
    }

    // return complex ID
    public String getComplexID() { return complexID; }

    // return apartment ID
    public String getAptID() { return aptID; }

    // return lease status
    public String getLeasedOrNot() { return leasedOrNot; }

    // return area
    public double getArea() { return area; }

    // return monthly rent
    public double getRent() { return rent; }

    // return fine for punishment
    public double getFine() { return fine; }
}
