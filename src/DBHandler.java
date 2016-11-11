import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by mark on 2016-11-06.
 */
public class DBHandler {

    // Query for customer search table
    private static final String CUSTOMER_WITH_DENTIST = "select c.cid, c.fname, c.lname, c.phone_Num, c.birthday, c.email, " +
            "c.address, d.fname, d.lname from Customer c, Attends a, Dentist d " +
            "where c.cid = \'a.cid\' and a.did = \'d.did\' and ";


    public Connection getConnection() {
        Connection conn = null;
        try {
            Properties connectionProps = new Properties();
            connectionProps.put("user", "ora_m3f0b");
            connectionProps.put("password", "a37504140");
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:ug", connectionProps);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    /* ------------------------------------------------------------------------------------------------------------------------------- //
    ----------------------------------------------- Login Methods -----------------------------------------------------------------
     */

    // Queries database for user's username and password
    // Returns 1 if user is dentist
    //         2 if user is hygienist
    //         3 if user is receptionist
    //        -1 if user's information is not found
    // login type can only be one of: d, r, or h
    public int queryLoginInfo(String username, String pw) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("select * from login_details where username = " + "\'" + username + "\'");
        ResultSet rs = stmt.getResultSet();
        //result set has tuples
        if (rs.next()) {
            LoginInformation login = new LoginInformation(rs.getString("username"), rs.getString("hashpass"), rs.getString("salt"), rs.getString("type"));
            // Checks if hashpass = hash(pw + salt)
            if (!login.getHashPass()
                    .equals(BCrypt.hashpw(pw, login.getSalt()))) {
                return -1; // password does not match
            }
            switch (login.getType()) {
                case "d":
                    return 1;
                case "h":
                    return 2;
                case "r":
                    return 3;
            }
        }
        return -1; // empty result set => username not found
    }


    /* ------------------------------------------------------------------------------------------------------------------------------- //
    ----------------------------------------------- Customer Methods -----------------------------------------------------------------
     */

    // The default view that an employee will have
    // Creates a list of customer objects that user must iterate through to handle
    public List<Customer> customerViewDefaultTable() throws SQLException {
        List<Customer> list = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("select cid, fname, lname, phone_Num, birthday, email, address, d.fname as dname, d.lname as dlname " +
                     "from Customer, Dentist d");
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            Customer c = new Customer(
                    rs.getInt("cid"),
                    rs.getString("fname"),
                    rs.getString("lname"),
                    rs.getInt("phone_Num"),
                    rs.getDate("birthday"),
                    rs.getString("email"),
                    rs.getString("address"));
            c.setDentist(rs.getString("dname") + rs.getString("dlname"));
            list.add(c);
        }
        return list;
    }

    // Filters customers by cid
    // Shows only those with cid = parameter cid
    // Creates a list of customer objects that user must iterate through to handle
    public List<Customer> customerSearchByCID(int cid) throws SQLException {
        List<Customer> list = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute(CUSTOMER_WITH_DENTIST + "cid = \'" + cid + "\'");
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            Customer c = new Customer(
                    rs.getInt(0),
                    rs.getString(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getDate(4),
                    rs.getString(5),
                    rs.getString(6));
            c.setDentist(rs.getString(7) + rs.getString(8));
            list.add(c);
        }
        return list;
    }

    // Filters customers by fname
    // Shows only those with fname = parameter fname
    // Creates a list of customer objects that user must iterate through to handle
    public List<Customer> customerSearchByName(int fname) throws SQLException {
        List<Customer> list = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute(CUSTOMER_WITH_DENTIST + "fname = \'" + fname + "\'");
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            Customer c = new Customer(
                    rs.getInt(0),
                    rs.getString(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getDate(4),
                    rs.getString(5),
                    rs.getString(6));
            c.setDentist(rs.getString(7) + rs.getString(8));
            list.add(c);
        }
        return list;
    }

    // Filters customers by lname
    // Shows only those with lname = parameter lname
    // Creates a list of customer objects that user must iterate through to handle
    public List<Customer> customerSearchByLastName(int lname) throws SQLException {
        List<Customer> list = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute(CUSTOMER_WITH_DENTIST + "lname = \'" + lname + "\'");
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            Customer c = new Customer(
                    rs.getInt(0),
                    rs.getString(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getDate(4),
                    rs.getString(5),
                    rs.getString(6));
            c.setDentist(rs.getString(7) + rs.getString(8));
            list.add(c);
        }
        return list;
    }

    // Finds specific appointment records for a customer
    public List<Appointment> getUpcomingCustomerAppointments(int cid) throws SQLException {
        String query = "select a.num as num, a.type as type, a.fromTime as fromTime, a.toTime as toTime " +
                       "from Customer c, Appointment a where " +
                       "c.cid = \'" + cid + "\' and a.cid = \'" + cid + "\' and CURRENT_TIMESTAMP <= a.fromTime";
        List<Appointment> list = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute(query);
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            Appointment a = new Appointment(
                    rs.getInt("num"),
                    rs.getString("type"),
                    rs.getTimestamp("fromTime"),
                    rs.getTimestamp("toTime"));
            list.add(a);
        }
        return list;
    }

    // Finds past appointment records for a customer
    public List<Appointment> getPastCustomerAppointments(int cid) throws SQLException {
        String query = "select a.num as num, a.type as type, a.fromTime as fromTime, a.toTime as toTime" +
                       " from Customer c, Appointment a where " +
                       "c.cid = \'" + cid + "\' and a.cid = \'" + cid + "\' and CURRENT_TIMESTAMP > a.fromTime";
        List<Appointment> list = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute(query);
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            Appointment a = new Appointment(
                    rs.getInt("num"),
                    rs.getString("type"),
                    rs.getTimestamp("fromTime"),
                    rs.getTimestamp("toTime"));
            list.add(a);
        }
        return list;
    }

    public void addCustomer(int cid, String fname, String lname, long phoneNum, Date dob, String email, String address) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO customer VALUES (" + cid + ", \'" + fname + "\', \'" + lname + "\', " + phoneNum + ", \'" + dob + "\', \'" + email + ", \'" + address + "\')");
    }

    public void removeCustomer(int eid) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM CUSTOMER WHERE eid = \'" + eid + "\'");
    }


    /* ------------------------------------------------------------------------------------------------------------------------------- //
   ----------------------------------------------- Employee Methods -----------------------------------------------------------------
    */

    // Registers an employee into the system, letting them have access to the application
    // Employee credentials are username, pw
    // type can only be one of: d, r, or h
    public void registerEmployee(String username, String pw, String type) throws SQLException {
        String salt = BCrypt.gensalt();
        String hashpass = BCrypt.hashpw(pw, salt);
        String query = "insert into login_details values (\'" + username + "\', " + "\'" + hashpass + "\', " + "\'" + salt + "\', " + "\'" + type + "\')";
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.executeUpdate();
    }

    public void addEmployee(int eid, String fname, String lname, int salary, int age, String sex, String dob, String phoneNum) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO EMPLOYEE VALUES (\'" + eid + "\', \'" + salary + "\', \'" + age + "\', \'" + sex + "\', \'" + dob + "\', \'" + phoneNum + "\')");
    }

    public void removeEmployee(int eid) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM EMPLOYEE WHERE eid = \'" + eid + "\')");
    }

    /* ------------------------------------------------------------------------------------------------------------------------------- //
    ----------------------------------------------- Supervisor Methods -----------------------------------------------------------------
    */

    // Get what a customer owes
    // cid is customer's cid
    // we do not use first or last name since results must be unique to one customer only
    public List<Bill> getCustomerBills(int cid) throws SQLException {
        String query = "select c.fname as Name, c.lname as Surname, b.type as type, b.dueDate as Due, " +
                       "b.amountPaid as Payment, (b.amountOwes - b.amountPaid) as Balance" +
                       " from Customer c, Bill b where b.cid = \'" + cid + "\' and c.cid = \'" + cid + "\'";
        List<Bill> list = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute(query);
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            Bill b = new Bill(
                    rs.getString("Name"),
                    rs.getString("Surname"),
                    rs.getString("type"),
                    rs.getDate("Due"),
                    rs.getBigDecimal("Payment"),
                    rs.getBigDecimal("Balance")
                    );
            list.add(b);
        }
        return list;
    }

    // Get a customer's yearly payments
    // cid is customer's cid
    // we do not use first or last name since results must be unique to one customer only
    public List<Bill> getCustomerYearlyPayments(int cid) throws SQLException {
        String query = "select c.fname as Name, c.lname as Surname, b.type as type, b.dueDate as Due, " +
                       "SUM(b.amountPaid) as Payment " +
                       "from Customer c, Bill b, Appointment a where a.cid = \'" + cid + " and b.cid = \'" + cid + "\' " +
                       "and c.cid = \'" + cid + "\' " +
                       "group by cid";
        List<Bill> list = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute(query);
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            Bill b = new Bill(
                    rs.getString("Name"),
                    rs.getString("Surname"),
                    rs.getString("type"),
                    rs.getDate("Due"),
                    rs.getBigDecimal("Payment")
            );
            list.add(b);
        }
        return list;
    }

    // Get a customer's unpaid bills
    // cid is customer's cid
    // we do not use first or last name since results must be unique to one customer only
    public List<Bill> getCustomerUnpaidBills(int cid) throws SQLException {
        String query = "select c.fname as Name, c.lname as Surname, b.type as type, b.dueDate as Due, " +
                "b.amountPaid as Payment, (b.amountOwes - b.amountPaid) as Balance" +
                " from Customer c, Bill b where b.cid = \'" + cid + "\' and c.cid = \'" + cid + "\' and b.isPaid = 0";
        List<Bill> list = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute(query);
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            Bill b = new Bill(
                    rs.getString("Name"),
                    rs.getString("Surname"),
                    rs.getString("type"),
                    rs.getDate("Due"),
                    rs.getBigDecimal("Payment"),
                    rs.getBigDecimal("Balance")
            );
            list.add(b);
        }
        return list;
    }


    /* ------------------------------------------------------------------------------------------------------------------------------- //
    ----------------------------------------------- Appointment Methods -----------------------------------------------------------------
    */

    public void addAppointment(int num, String type, Timestamp fromTime, Timestamp toTime, int rid, int cid) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO appointment VALUES (\'" + num + "\', \'" + type + "\', \'" + fromTime + "\', \'" + toTime + "\', \'" + rid + "\', \'" + cid + "\')");
    }

    public void removeAppointment(int num) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM APPOINTMENT WHERE num = \'" + num + "\'");
    }


    /* ------------------------------------------------------------------------------------------------------------------------------- //
    ----------------------------------------------- Bill Methods -----------------------------------------------------------------
    */

    public void addBill(int bid, String type, BigDecimal amountPaid, BigDecimal amountOwes,
                        Date dueDate, int isPaid, int cid) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO bill VALUES (\'" + bid + "\', \'" + type +
                           "\', \'" + amountPaid + "\', \'" + amountOwes + "\', \'" + dueDate + "\', \'" + isPaid + "\', \'" +
                           cid + "\')");
    }



    /* ------------------------------------------------------------------------------------------------------------------------------- //
    ----------------------------------------------- Medicine Methods -----------------------------------------------------------------
    */


}
