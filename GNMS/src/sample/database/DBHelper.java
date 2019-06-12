package sample.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {
    private static final String JDBC_Driver = "com.mysql.cj.jdbc.Driver";
    private static final String DB_Url = "jdbc:mysql://localhost:3306/gnms";
    private static final String DB_User = "user";
    private static final String DB_Pass = "pass";
    private static volatile DBHelper instance;
    private static Connection connection = null;

    private DBHelper() {
        createDataBase();
    }

    public static DBHelper getInstance() {
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null) {
                    return instance = new DBHelper();
                }
            }
        }
        return instance;
    }

    private void dbConnect() {
        try {
            Class.forName(JDBC_Driver);
            connection = DriverManager.getConnection(DB_Url, DB_User, DB_Pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dbDisconnect() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createDataBase() {
        String createDataBase = "create database if not exists gnms;";
        String createUserTable = "create table if not exists customer (id int auto_increment, fname varchar(20) not null, lname varchar(30) not null, phone varchar(11), charge int default 0, ticket_hour int not null, primary key(id));";
        String createDeviceTable = "create table if not exists device (id int auto_increment, devicetype varchar(10) not null, primary key(id));";
        String createPlayTable = "create table if not exists play(id int auto_increment, start_hour time not null, finish_hour time not null, income int not null,date_played date not null, uid int, did int, foreign key (uid) references customer(id), foreign key (did) references device(id), primary key(id));";
        dbConnect();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(createDataBase);
            statement.execute(createUserTable);
            statement.execute(createDeviceTable);
            statement.execute(createPlayTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //dbDisconnect();
    }

    private ResultSet executeQuery(String query) {
        dbConnect();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }/* finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
        //dbDisconnect();
        return resultSet;
    }

    private void executeUpdate(String query) {
        dbConnect();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //dbDisconnect();
    }

    public void addCustomer(Customer customer) {
        System.out.println(customer.getFname()
                + "\n" +
                customer.getLname()
                + "\n" +
                customer.getPhone()
                + "\n" +
                customer.getCharge()
                + "\n" +
                customer.getTicket_hour());
        if (customer.getCharge() == 0 && customer.getPhone().equals("")) {
            String query = "insert into customer (fname, lname, ticket_hour) values ("
                    + "'" + customer.getFname() + "'" +
                    ","
                    + "'" + customer.getLname() + "'" +
                    "," +
                    customer.getTicket_hour() +
                    ");";
            executeUpdate(query);
        } else if (customer.getCharge() == 0) {
            String query = "insert into customer (fname, lname, phone, ticket_hour) values ("
                    + "'" + customer.getFname() + "'" +
                    ","
                    + "'" + customer.getLname() + "'" +
                    ","
                    + "'" + customer.getPhone() + "'" +
                    ","
                    + customer.getTicket_hour() +
                    ");";
            executeUpdate(query);
        } else if (customer.getPhone().equals("")) {
            String query = "insert into customer (fname, lname, charge, ticket_hour) values ("
                    + "'" + customer.getFname() + "'" +
                    ","
                    + "'" + customer.getLname() + "'" +
                    ","
                    + "'" + customer.getCharge() + "'" +
                    ","
                    + customer.getTicket_hour() +
                    ");";
            executeUpdate(query);
        } else {
            String query = "insert into customer (fname, lname, phone, charge, ticket_hour) values ("
                    + "'" + customer.getFname() + "'" +
                    ","
                    + "'" + customer.getLname() + "'" +
                    ","
                    + "'" + customer.getPhone() + "'" +
                    "," +
                    customer.getCharge() +
                    "," +
                    customer.getTicket_hour() +
                    ");";
            executeUpdate(query);
        }
    }

    public void delCustomer(int customerId) {
        String query = "delete from customer where id = " + customerId + ";";
        executeUpdate(query);
    }

    public void addDevice(Device device) {
        String query = "insert into device (devicetype) value(" + "'" + device.getDeviceType() + "'" + ");";
        executeUpdate(query);
    }

    public void delDevice(int id) {
        String query = "delete from device where id = " + id + ";";
        executeUpdate(query);
    }

    public ObservableList<Device> getDevices() {
        String query = "select * from device;";
        ResultSet resultSet = executeQuery(query);
        ObservableList<Device> observableList = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                Device device = new Device(resultSet.getInt("id"), resultSet.getString("devicetype"));
                observableList.add(device);
                //System.out.println(resultSet.getString("devicetype"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return observableList;
    }

    public List<Customer> getCustomers(int state, String firstName, String lastName, String phone) {
        Customer customer = null;
        ResultSet resultSet = null;
        List<Customer> customers = new ArrayList<Customer>();
        String query;
//        switch (params.get(0)) {
//            case "123":
//                System.out.println(params.get(0) + params.get(1) + params.get(2) + params.get(3));
//                query = "select * from customer where fname = " +
//                        "'" + params.get(1) + "'"
//                        + "and lname = " +
//                        "'" + params.get(2) + "'"
//                        + "and phone = " +
//                        "'" + params.get(3) + "'"
//                        + ";";
//                resultSet = executeQuery(query);
//                break;
//            case "12":
//                System.out.println(params.get(0) + params.get(1) + params.get(2));
//                query = "select * from customer where fname = " +
//                        "'" + params.get(1) + "'"
//                        + "and lname = " +
//                        "'" + params.get(2) + "'"
//                        + ";";
//                resultSet = executeQuery(query);
//                break;
//            case "23":
//                System.out.println(params.get(0) + params.get(1) + params.get(2));
//                query = "select * from customer where lname = " +
//                        "'" + params.get(1) + "'"
//                        + "and phone = " +
//                        "'" + params.get(2) + "'"
//                        + ";";
//                resultSet = executeQuery(query);
//                break;
//            case "13":
//                System.out.println(params.get(0) + params.get(1) + params.get(2));
//                query = "select * from customer where fname = " +
//                        "'" + params.get(1) + "'"
//                        + "and phone = " +
//                        "'" + params.get(2) + "'"
//                        + ";";
//                resultSet = executeQuery(query);
//                break;
//            case "1":
//                System.out.println(params.get(0) + params.get(1));
//                query = "select * from customer where fname = " +
//                        "'" + params.get(1) + "'"
//                        + ";";
//                resultSet = executeQuery(query);
//                break;
//            case "2":
//                System.out.println(params.get(0) + params.get(1));
//                query = "select * from customer where lname = " +
//                        "'" + params.get(1) + "'"
//                        + ";";
//                resultSet = executeQuery(query);
//                break;
//            case "3":
//                System.out.println(params.get(0) + params.get(1));
//                query = "select * from customer where phone = " +
//                        "'" + params.get(1) + "'"
//                        + ";";
//                resultSet = executeQuery(query);
//                break;
//            default:
//                break;
//        }

//        if (firstName.isEmpty()) {
//            query = "select * from customer where lname = " +
//                    "'" + lastName + "'"
//                    + "and phone = " +
//                    "'" + phone + "'"
//                    + ";";
//            resultSet = executeQuery(query);
//        } else if (lastName.isEmpty()) {
//            query = "select * from customer where fname = " +
//                    "'" + firstName + "'"
//                    + "and phone = " +
//                    "'" + phone + "'"
//                    + ";";
//            resultSet = executeQuery(query);
//        } else if (phone.isEmpty()) {
//            query = "select * from customer where fname = " +
//                    "'" + firstName + "'"
//                    + "and lname = " +
//                    "'" + lastName + "'"
//                    + ";";
//            resultSet = executeQuery(query);
//
//        } else if (firstName.isEmpty() && lastName.isEmpty()) {
//            query = "select * from customer where phone = " +
//                    "'" + phone + "'"
//                    + ";";
//            resultSet = executeQuery(query);
//        } else if (firstName.isEmpty() && phone.isEmpty()) {
//            query = "select * from customer where lname = " +
//                    "'" + lastName + "'"
//                    + ";";
//            resultSet = executeQuery(query);
//        } else if (lastName.isEmpty() && phone.isEmpty()) {
//            query = "select * from customer where fname = " +
//                    "'" + firstName + "'"
//                    + ";";
//            resultSet = executeQuery(query);
//        } else {
//            query = "select * from customer where fname = " +
//                    "'" + firstName + "'"
//                    + "and lname = " +
//                    "'" + lastName + "'"
//                    + "and phone = " +
//                    "'" + phone + "'"
//                    + ";";
//            resultSet = executeQuery(query);
//        }

        switch (state) {
            case 123:
                query = "select * from customer where fname = " +
                        "'" + firstName + "'"
                        + "and lname = " +
                        "'" + lastName + "'"
                        + "and phone = " +
                        "'" + phone + "'"
                        + ";";
                resultSet = executeQuery(query);
                break;
            case 12:
                query = "select * from customer where fname = " +
                        "'" + firstName + "'"
                        + "and lname = " +
                        "'" + lastName + "'"
                        + ";";
                resultSet = executeQuery(query);
                break;
            case 23:
                query = "select * from customer where lname = " +
                        "'" + lastName + "'"
                        + "and phone = " +
                        "'" + phone + "'"
                        + ";";
                resultSet = executeQuery(query);
                break;
            case 13:
                query = "select * from customer where fname = " +
                        "'" + firstName + "'"
                        + "and phone = " +
                        "'" + phone + "'"
                        + ";";
                resultSet = executeQuery(query);
                break;
            case 1:
                query = "select * from customer where fname = " +
                        "'" + firstName + "'"
                        + ";";
                resultSet = executeQuery(query);
                break;
            case 2:
                query = "select * from customer where lname = " +
                        "'" + lastName + "'"
                        + ";";
                resultSet = executeQuery(query);
                break;
            case 3:
                query = "select * from customer where phone = " +
                        "'" + phone + "'"
                        + ";";
                resultSet = executeQuery(query);
                break;
            default:
                query = "select * from customer;";
                resultSet = executeQuery(query);
                break;
        }
        try {
            while (true) {
                if (!resultSet.next()) break;
                customer = new Customer();
                customer.setId(resultSet.getInt("id"));
                customer.setFname(resultSet.getString("fname"));
                customer.setLname(resultSet.getString("lname"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setCharge(resultSet.getLong("charge"));
                customer.setTicket_hour(resultSet.getInt("ticket_hour"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public Customer getCustomer(int id) {
        String query = "select * from customer where id = " + id + ";";
        ResultSet resultSet = executeQuery(query);
        Customer customer = new Customer();
        try {
            if (resultSet.next()) {
                customer.setId(resultSet.getInt("id"));
                customer.setFname(resultSet.getString("fname"));
                customer.setLname(resultSet.getString("lname"));
                customer.setCharge(resultSet.getLong("charge"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public void play(Play play) {
        String query = "";
        if (play.getUid() == -1)
            query = "insert into play (start_hour, finish_hour, income, date_played, did) values ("
                    + "'" + play.getStart_hour() + "'" + ","
                    + "'" + play.getFinish_hour() + "'" + "," +
                    play.getIncome() + ","
                    + "'" + play.getDate_played() + "'" + "," +
                    play.getDid() +
                    ");";
        else query = "insert into play (start_hour, finish_hour, income, date_played, uid, did) values ("
                + "'" + play.getStart_hour() + "'" + ","
                + "'" + play.getFinish_hour() + "'" + "," +
                play.getIncome() + ","
                + "'" + play.getDate_played() + "'" + "," +
                play.getUid() + "," +
                play.getDid() +
                ");";
        executeUpdate(query);
    }

    public int updateCharge(int id, int income) {
        String selectQuery = "select charge from customer where id =  " + id + ";";
        int newCharge;
        try {
            ResultSet rs = executeQuery(selectQuery);
            rs.next();
            newCharge = rs.getInt("charge") - income;
            String updateQuery = "update customer set charge = " + newCharge + " where id = " + id + ";";
            executeUpdate(updateQuery);
            return newCharge;
        } catch (SQLException e) {
            e.printStackTrace();
            return income;
        }
    }

    public void updateTicketTime(int id, int time) {
        String query = "update customer set ticket_hour = " + time + " where id = " + id + ";";
        executeUpdate(query);
    }

    public void editCustomer(int id, int state, String newPhone, Integer newCharge) {
        String query;
        switch (state) {
            case 1:
                query = "update customer set phone = '" + newPhone + "', charge = " + newCharge + " where id = " + id + ";";
                executeUpdate(query);
                break;
            case 2:
                query = "update customer set phone = '" + newPhone + "' where id = " + id + ";";
                executeUpdate(query);
                break;
            case 3:
                query = "update customer set charge = " + newCharge + " where id = " + id + ";";
                executeUpdate(query);
                break;
        }

    }

    public List<IncomeTable> calculateIncome(String tag, String start, String finish, List<Device> devices, int customerId, String customername) {
        try {
            String query = "";
            String whereQuery = " where ";
            StringBuilder whereQueryBuilder = new StringBuilder();
            String mainQuery = "select play.date_played, play.start_hour, play.finish_hour," +
                    "play.income, customer.fname, customer.lname, device.devicetype\n" +
                    "from (play join customer on play.uid = customer.id)" +
                    "join device on play.did = device.id";
            String whereStart = " play.date_played > " + "'" + start + "' ";
            String whereFinish = " play.date_played < " + "'" + finish + "' ";
            StringBuilder whereDevice = new StringBuilder("( device.id = ");
            if (!devices.isEmpty()) {
                for (int i = 0; i < devices.size(); i++) {
                    if (i < devices.size() - 1)
                        whereDevice.append(" ").append(devices.get(i).getDeviceId()).append(" or device.id = ");
                    else whereDevice.append(" ").append(devices.get(i).getDeviceId()).append(" )");
                }
            }
            String whereCustomerId = " customer.id = " + customerId + " ";
            String whereCustomerName = " (customer.fname like '" + customername + "'" + " or customer.lname like '" + customername + "') ";

            //start-finish
            if (tag.charAt(0) == '1' && tag.charAt(1) == '1')
                whereQueryBuilder.append(whereStart).append("and").append(whereFinish);
            else if (tag.charAt(0) == '1' && tag.charAt(1) == '0')
                whereQueryBuilder.append(whereStart);
            else if (tag.charAt(0) == '0' && tag.charAt(1) == '1')
                whereQueryBuilder.append(whereFinish);
            //device
            if ((tag.charAt(0) == '0' && tag.charAt(1) == '0') && tag.charAt(2) == '1' && !devices.isEmpty())
                whereQueryBuilder.append(whereDevice);
            else if (tag.charAt(2) == '1' && !devices.isEmpty())
                whereQueryBuilder.append("and").append(whereDevice);
            //id
            if (tag.charAt(0) == '0' && tag.charAt(1) == '0' && tag.charAt(2) == '0' && tag.charAt(3) == '1')
                whereQueryBuilder.append(whereCustomerId);
            if (tag.charAt(3) == '1')
                whereQueryBuilder.append("and").append(whereCustomerId);
            //name
            if (tag.charAt(0) == '0' && tag.charAt(1) == '0' && tag.charAt(2) == '0' && tag.charAt(3) == '0' && tag.charAt(4) == '1')
                whereQueryBuilder.append(whereCustomerName);
            if (tag.charAt(4) == '1')
                whereQueryBuilder.append("and").append(whereCustomerName);
            whereQuery = " where " + whereQueryBuilder + ";";
            if (tag.equals("00000") || (tag.equals("00100") && devices.isEmpty()))
                query = mainQuery;
            else query = mainQuery + whereQuery;
            System.out.println(query);
            ResultSet rs = executeQuery(query);
            List<IncomeTable> data = new ArrayList<>();
            while (rs.next()) {
                data.add(
                        new IncomeTable(
                                rs.getString("play.date_played"),
                                rs.getString("device.devicetype"),
                                rs.getString("play.start_hour"),
                                rs.getString("play.finish_hour"),
                                rs.getString("customer.fname"),
                                rs.getString("customer.lname"),
                                rs.getInt("play.income")));

            }
            return data;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }
}
