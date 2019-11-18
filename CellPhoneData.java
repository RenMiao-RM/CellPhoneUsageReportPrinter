public class CellPhoneData implements DbDataObject{
    private String employeeId;
    private String employeeName;
    private String purchaseDate;
    private String model;

    public CellPhoneData(String employeeId, String employeeName, String purchaseDate, String model) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.purchaseDate = purchaseDate;
        this.model = model;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getModel() {
        return model;
    }

}
