package sample.model;

public class Customer {
    private int id;
    private String fname = "";
    private String lname = "";
    private String phone = "";
    private long charge = 0;
    private int ticket_hour = 0;

    //constructors
    public Customer(int id, String fname, String lname, String phone, long charge, int ticket_hour) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.charge = charge;
        this.ticket_hour = ticket_hour;
    }

    public Customer(String fname, String lname, String phone, long charge, int ticket_hour) {
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.charge = charge;
        this.ticket_hour = ticket_hour;
    }

    public Customer(String fname, String lname, long charge, int ticket_hour) {
        this.fname = fname;
        this.lname = lname;
        this.charge = charge;
        this.ticket_hour = ticket_hour;
    }

    public Customer(String fname, String lname, String phone, int ticket_hour) {
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.ticket_hour = ticket_hour;
    }


    public Customer(String fname, String lname, int ticket_hour) {
        this.fname = fname;
        this.lname = lname;
        this.ticket_hour = ticket_hour;
    }

    public Customer() {

    }

    //getter
    public int getId() { return id; }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getPhone() {
        return phone;
    }

    public long getCharge() { return charge; }

    public int getTicket_hour() {
        return ticket_hour;
    }

    //setter
    public void setId(int id) { this.id = id; }

    public void setFname(String fname) { this.fname = fname; }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCharge(long charge) {
        this.charge = charge;
    }

    public void setTicket_hour(int ticket_hour) {
        this.ticket_hour = ticket_hour;
    }
}
