import java.util.Date;

public class CellPhoneUsageData implements DbDataObject{
    private String employeeId;
    private Date date;
    private int totalMinute;
    private double totalData;

    public CellPhoneUsageData(String employeeId, Date date, int totalMinute, double totalData) {
        this.employeeId = employeeId;
        this.date = date;
        this.totalMinute = totalMinute;
        this.totalData = totalData;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Date getDate() {
        return date;
    }

    public int getTotalMinute() {
        return totalMinute;
    }

    public double getTotalData() {
        return totalData;
    }
}
