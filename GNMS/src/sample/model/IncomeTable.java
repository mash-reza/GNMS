package sample.model;

public class IncomeTable {
    private String date;
    private String deviceName;
    private String startTime;
    private String finishTime;
    private String customerFName;
    private String customerLName;
    private int income;

    public IncomeTable() {
    }

    public IncomeTable(String date, String deviceName, String startTime, String finishTime, String customerFName, String customerLName, int income) {
        this.date = date;
        this.deviceName = deviceName;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.customerFName = customerFName;
        this.customerLName = customerLName;
        this.income = income;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getCustomerFName() {
        return customerFName;
    }

    public void setCustomerFName(String customerFName) {
        this.customerFName = customerFName;
    }

    public String getCustomerLName() {
        return customerLName;
    }

    public void setCustomerLName(String customerLName) {
        this.customerLName = customerLName;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }
}
