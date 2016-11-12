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
        String query = "select * from Customer";
        List<Customer> list = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Customer c = new Customer(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getLong(4),
                    rs.getDate(5),
                    rs.getString(6),
                    rs.getString(7));
            list.add(c);
        }
        conn.close();
        return list;
    }

    // Filters customers by cid
    // Shows only those with cid = parameter cid
    // Creates a list of customer objects that user must iterate through to handle
    public ArrayList<Customer> customerSearchByCID(int cid) throws SQLException {
        String query = "select c.cid, c.fname, c.lname, c.phone_Num, c.dob, c.email, " +
                "c.address from Customer c where c.cid = \'" + cid + "\'";
        ArrayList<Customer> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Customer c = new Customer(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getLong(4),
                    rs.getDate(5),
                    rs.getString(6),
                    rs.getString(7));
            list.add(c);
        }
        conn.close();
        return list;
    }

    // Filters customers by fname
    // Shows only those with fname = parameter fname
    // Creates a list of customer objects that user must iterate through to handle
    public List<Customer> customerSearchByFirstName(String fname) throws SQLException {
        String query = "select c.cid AS CustomerID, c.fname AS FirstName, c.lname AS LastName, c.phone_Num AS PhoneNumber, c.dob AS DateOfBirth, c.email AS Email, " +
                       "c.address AS Address from Customer c WHERE c.fname LIKE \'%" + fname + "%\'";
        ArrayList<Customer> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Customer c = new Customer(
                    rs.getInt("CustomerID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getLong("PhoneNumber"),
                    rs.getDate("DateOfBirth"),
                    rs.getString("Email"),
                    rs.getString("Address"));
            list.add(c);
        }
        conn.close();
        return list;
    }

    // Filters customers by lname
    // Shows only those with lname = parameter lname
    // Creates a list of customer objects that user must iterate through to handle
    public List<Customer> customerSearchByLastName(String lname) throws SQLException {
        String query = "select * from customer where lname LIKE \'%" + lname + "%\'";
        ArrayList<Customer> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Customer c = new Customer(
                    rs.getInt("cid"),
                    rs.getString("fname"),
                    rs.getString("lname"),
                    rs.getLong("phone_Num"),
                    rs.getDate("dob"),
                    rs.getString("email"),
                    rs.getString("address"));
            list.add(c);
        }
        conn.close();
        return list;
    }

    // Finds specific appointment records for a customer
    public List<Appointment> getUpcomingCustomerAppointments(int cid) throws SQLException {
        String query = "select app.num as \"num\", app.type as \"type\", app.from_Time as \"from\", app.to_Time as \"until\"" +
                " from Customer cus, Appointment app where " +
                "cus.cid = app.cid and app.cid = ? and CURRENT_TIMESTAMP <= app.from_Time";
        List<Appointment> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, cid);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointment a = new Appointment(
                    rs.getInt("num"),
                    rs.getString("type"),
                    rs.getTimestamp("from"),
                    rs.getTimestamp("until"));
            list.add(a);
        }
        conn.close();
        return list;
    }

    // Finds specific appointment records for all customers
    public List<Appointment> getUpcomingCustomerAppointments() throws SQLException {
        String query = "select * from customer c, appointment a where a.cid = c.cid and CURRENT_TIMESTAMP <= a.from_Time";
        List<Appointment> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointment a = new Appointment(
                    rs.getInt("num"),
                    rs.getTimestamp("from_Time"),
                    rs.getTimestamp("to_Time"),
                    rs.getString("type"),
                    rs.getInt("cid"),
                    rs.getString("fname"),
                    rs.getString("lname")
                    );
            list.add(a);
        }
        conn.close();
        return list;
    }

    // Finds past appointment records for a customer
    public List<Appointment> getPastCustomerAppointments(int cid) throws SQLException {
        String query = "select app.num as \"num\", app.type as \"type\", app.from_Time as \"from\", app.to_Time as \"until\"" +
                       " from Customer cus, Appointment app where " +
                       "cus.cid = app.cid and app.cid = ? and CURRENT_TIMESTAMP > app.from_Time";
        List<Appointment> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, cid);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointment a = new Appointment(
                    rs.getInt("num"),
                    rs.getString("type"),
                    rs.getTimestamp("from"),
                    rs.getTimestamp("until"));
            list.add(a);
        }
        conn.close();
        return list;
    }

    public void addCustomer(int cid, String fname, String lname, long phoneNum, Date dob, String email, String address) throws SQLException {
        Connection conn = getConnection();
        String query = "insert into customer(cid, fname, lname, phone_Num, dob, email, address) values(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, cid);
        ps.setString(2, fname);
        ps.setString(3, lname);
        ps.setLong(4, phoneNum);
        ps.setDate(5, dob);
        ps.setString(6, email);
        ps.setString(7, address);
        ps.executeUpdate();
    }

    public void removeCustomer(int eid) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM CUSTOMER WHERE eid = \'" + eid + "\'");
        conn.close();
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
        conn.close();
    }

    public void addEmployee(int eid, String fname, String lname, int salary, int age, String sex, String dob, long phoneNum) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO EMPLOYEE VALUES (\'" + eid + "\', \'" + salary + "\', \'" + age + "\', \'" + sex + "\', \'" + dob + "\', \'" + phoneNum + "\')");
        conn.close();
    }

    public void updateEmployee(int eid, String fname, String lname, int salary, int age, String sex, String dob, long phoneNum) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE appointment SET fname = \'" + fname + "\', lname = \'" + lname + "\', salary = \'" + salary + "\', age = \'" + age + "\', sex = \'" + sex + "\', dob = \'" + dob + "\', phoneNum = \'" + phoneNum + "\' WHERE eid = \'" + eid + "\'");
    }

    public void removeEmployee(int eid) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM EMPLOYEE WHERE eid = \'" + eid + "\')");
        conn.close();
    }

    public List<Employee> employeeSearchByFirstName(String fname) throws SQLException {
        String query = "select e.eid AS EmployeeID, e.fname AS FirstName, e.lname AS LastName, e.salary AS Salary, e.age AS Age, e.sex AS Sex, " +
                       "e.dob AS DateOfBirth, phoneNum AS PhoneNumber from Employee e WHERE e.fname LIKE \'%?%\'";
        ArrayList<Employee> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, fname);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Employee e = new Employee(
                    rs.getInt("EmployeeID"),
                    rs.getInt("Salary"),
                    rs.getInt("Age"),
                    rs.getString("Sex"),
                    rs.getString("DateOfBirth"),
                    rs.getLong("PhoneNumber"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"));
            list.add(e);
        }
        conn.close();
        return list;
    }

    public List<Employee> employeeSearchByLastName(String lname) throws SQLException {
        String query = "select e.eid AS EmployeeID, e.fname AS FirstName, e.lname AS LastName, e.salary AS Salary, e.age AS Age, e.sex AS Sex, " +
                       "e.dob AS DateOfBirth, phoneNum AS PhoneNumber from Employee e WHERE e.fname LIKE \'%?%\'";
        ArrayList<Employee> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, lname);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Employee e = new Employee(
                    rs.getInt("EmployeeID"),
                    rs.getInt("Salary"),
                    rs.getInt("Age"),
                    rs.getString("Sex"),
                    rs.getString("DateOfBirth"),
                    rs.getLong("PhoneNumber"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"));
            list.add(e);
        }
        conn.close();
        return list;
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
        conn.close();
        return list;
    }

    // Get a customer's yearly payments
    // cid is customer's cid
    // we do not use first or last name since results must be unique to one customer only
    public List<Bill> getCustomerPastPayments(int cid) throws SQLException {
        String query = "select c.cid, c.fname as Name, " +
                    "c.lname as Surname, " +
                    "SUM(b.amountPaid) as Payment " +
                    "from Customer c, Bill b where b.cid = ? " +
                    "and c.cid = ? and b.cid = c.cid and CURRENT_DATE > dueDate and isPaid = 1" +
                    "group by c.cid, c.fname, c.lname";
        List<Bill> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, cid);
        ps.setInt(2, cid);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Bill b = new Bill(
                    rs.getString("Name"),
                    rs.getString("Surname"),
                    rs.getBigDecimal("Payment")
            );
            list.add(b);
        }
        conn.close();
        return list;
    }

    // Get a customer's yearly payments
    // cid is customer's cid
    // we do not use first or last name since results must be unique to one customer only
    public List<Customer> getCustomerWith2Payments() throws SQLException {
        String query = "select * from customer where cid in (" +
                       "select cid from bill group by cid having COUNT(*) = 2)";
        List<Customer> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Customer b = new Customer(
                    rs.getInt("cid"),
                    rs.getString("fname"),
                    rs.getString("lname"),
                    rs.getLong("phone_Num"),
                    rs.getDate("dob"),
                    rs.getString("email"),
                    rs.getString("address")
            );
            list.add(b);
        }
        conn.close();
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
        conn.close();
        return list;
    }


    /* ------------------------------------------------------------------------------------------------------------------------------- //
    ----------------------------------------------- Appointment Methods -----------------------------------------------------------------
    */

    public void addAppointment(int num, String type, Timestamp fromTime, Timestamp toTime, int rid, int cid) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO appointment VALUES (\'" + num + "\', \'" + type + "\', \'" + fromTime + "\', \'" + toTime + "\', \'" + rid + "\', \'" + cid + "\')");
        conn.close();
    }

    public void updateAppointment(int num, String type, Timestamp fromTime, Timestamp toTime) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE appointment SET type = \'" + type + "\', fromTime = \'" + fromTime + "\', toTime = \'" + toTime + "\' WHERE num = \'" + num + "\'");
    }

    public void removeAppointment(int num) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM APPOINTMENT WHERE num = \'" + num + "\'");
        conn.close();
    }


    /* ------------------------------------------------------------------------------------------------------------------------------- //
    ----------------------------------------------- Bill Methods -----------------------------------------------------------------
    */

    public void addBill(int bid, String type, BigDecimal amountPaid, BigDecimal amountOwes,
                        Date dueDate, int isPaid, int cid) throws SQLException {
        Connection conn = getConnection();
        String query = "insert into bill(bid, type, amountPaid, amountOwes, dueDate, isPaid, cid) values(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, bid);
        ps.setString(2, type);
        ps.setBigDecimal(3, amountPaid);
        ps.setBigDecimal(4, amountOwes);
        ps.setDate(5, dueDate);
        ps.setInt(6, isPaid);
        ps.setInt(7, cid);
        ps.executeUpdate();
        conn.close();
    }

    public void updateBill(int code, String type, BigDecimal amountPaid, BigDecimal amountOwes,
                           Date dueDate, int isPaid) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE bill SET type = \'" + type + "\', amountPaid = \'" + amountPaid + "\', amountOwes = \'" + amountOwes + "\', dueDate = \'" + dueDate + "\', isPaid = \'" + isPaid + "\' WHERE code = \'" + code + "\'");
    }

    public void removeBill(int bid) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM bill WHERE bid = \'" + bid + "\'");
    }

    /* ------------------------------------------------------------------------------------------------------------------------------- //
    ----------------------------------------------- Medicine Methods -----------------------------------------------------------------
    */

    public void addMedicine(int code, double cost, String description) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO medicine VALUES (\'" + code + "\', \'" + cost + "\', \'" + description + "\')");
    }

    public void updateMedicine(int code, double cost, String description) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE medicine SET cost = \'" + cost + "\', description = \'" + description + "\' WHERE code = \'" + code + "\'");
    }

    public void removeMedicine(int code) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM medicine WHERE code = \'" + code + "\'");
    }

    public void addTreats(int code, int cid) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO treats VALUES (\'" + code + "\', \'" + cid + "\')");
    }

    public void removeTreats(int code, int cid) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM medicine WHERE code = \'" + code + "\' AND cid = \'" + cid + "\'");
    }

    // Finds  medicines for customer
    public List<Medicine> getCustomerMedicines(int cid) throws SQLException {
        String query = "SELECT m.code AS CODE, m.cost AS COST, m.description AS Description from Treats t, Medicine m where " +
                "t.cid = \'" + cid + "\'";
        List<Medicine> list = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            Medicine m = new Medicine(
                    rs.getInt("CODE"),
                    rs.getDouble("COST"),
                    rs.getString("DESCRIPTION"));
            list.add(m);
        }
        return list;
    }


}
