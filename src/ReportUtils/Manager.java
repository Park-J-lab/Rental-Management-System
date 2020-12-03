package ReportUtils;

public class Manager {
    private final String managerID; // manager ID
    private final String managerName; // manager name
    private final String password; // password
    private final String complexID; // the ID of the complex he or she manages
    private final String phoneNum; // phone number
    private final double wage; // wage per month

    public Manager(String managerID, String complexID, String password, String managerName,
                   String phoneNum, double wage) {
        this.managerID = managerID;
        this.password = password;
        this.managerName = managerName;
        this.complexID = complexID;
        this.phoneNum = phoneNum;
        this.wage = wage;

    }

    // return manager ID
    public String getManagerID() {
        return managerID;
    }

    // return complex ID
    public String getComplexID() {
        return complexID;
    }

    // return password
    public String getPassword() {
        return password;
    }

    // return manager name
    public String getManagerName() {
        return managerName;
    }

    // return phone number
    public String getPhoneNum() {
        return phoneNum;
    }

    // return monthly wage
    public double getWage() {
        return wage;
    }
}
