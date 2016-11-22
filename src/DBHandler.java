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
    private static final String CUSTOMER_WITH_DENTIST =
            "select c.cid, c.fname, c.lname, c.phone_Num, c.birthday, c.email, " +
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    /* ------------------------------------------------------------------------------------------------------------------------------- //
    ----------------------------------------------- Login Methods -----------------------------------------------------------------
     */

    // Determines if user is one of below:
    //  Returns 1 if user is dentist
    //         2 if user is hygienist
    //         3 if user is receptionist
    //        -1 if user's information is not found
    // login type can only be one of: d, r, or h
    // employee cannot be in more than one of: r, d, or h tables
    // Queries database for user's username and password
    public int queryLoginInfo(String username, String pw) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("select * from login_details where username = " + "\'" + username + "\'");
        ResultSet rs = stmt.getResultSet();
        //result set has tuples
        if (rs.next()) {
            LoginInformation login = new LoginInformation(
                    rs.getString("username"),
                    rs.getString("hashpass"),
                    rs.getString("salt"),
                    rs.getString("type"),
                    rs.getInt("eid"));
            //Checks if hashpass = hash(pw + salt)
            if (!login.getHashPass()
                    .equals(BCrypt.hashpw(pw, login.getSalt()))) {
                return -1; // password does not match
            }

            conn.close();
            switch (login.getType()) {
                case "d" :
                    return 1;
                case "h" :
                    return 2;
                case "r" :
                    return 3;
            }
        }
        conn.close();
        return -1; // empty result set => username not found
    }



    /* ------------------------------------------------------------------------------------------------------------------------------- //
    ----------------------------------------------- Customer Methods -----------------------------------------------------------------
    */

    // Finds the customer for a given appointment number
    // returns a customer
    public Customer getCustomerByAppointmentNum(int num) throws SQLException {
        String query = "select c.cid as id, c.fname as f, c.lname as l, c.phone_Num as p, c.email as e, c.address as a from Appointment a, Customer c where c.cid = a.cid and a.num = ?";
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, num);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Customer(
                    rs.getInt("id"),
                    rs.getString("f"),
                    rs.getString("l"),
                    rs.getLong("p"),
                    rs.getString("e"),
                    rs.getString("a"));
        }
        return null;
    }

    // Find out if a cid is valid or not
    // Returns true if cid is in use
    // False otherwise
    public boolean isValidCustomerID(int cid) throws SQLException {
        String query = "select * from Customer where cid = ?";
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, cid);
        ResultSet rs = ps.executeQuery();
        boolean valid = false;
        if (rs.next()) {
            valid = true;
        }
        conn.close();
        return valid;
    }

    // The default view that an employee will have
    // Creates a list of customer objects that user must iterate through to handle
    public List<Customer> customerViewDefaultTable() throws SQLException {
        String query = "select * from customer";
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
    public List<Appointment> getUpcomingCustomerAppointmentsByCID(int cid) throws SQLException {
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

    // Finds specific appointment records for all customers
    public List<CountColumn> getUpcomingCustomerAppointmentTypesColumnCount() throws SQLException {
        String query = "SELECT type AS Type, count(*) AS Count FROM appointment group by type";
        List<CountColumn> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            CountColumn c = new CountColumn(
                    rs.getString("Type"),
                    rs.getInt("Count")
            );
            list.add(c);
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

    public void removeCustomer(int cid) throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM CUSTOMER WHERE cid = \'" + cid + "\'");
        conn.close();
    }

    // Get highest Customer ID
    public int getHighestCustomerID() throws Exception {
        String query = "select max(cid) from customer";
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute(query);
        ResultSet rs = stmt.getResultSet();
        int result = 0;
        while (rs.next()) {
            result = rs.getInt(1);
        }
        conn.close();
        return result;
    }

    public List<Bill> getAllCustomerBills() throws SQLException {
        String query = "select c.fname as Name, c.lname as Surname, b.type as type, b.dueDate as Due, " +
                "b.amountPaid as Payment, (b.amountOwes - b.amountPaid) as Balance" +
                " from Customer c, Bill b where b.cid = c.cid'";
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
   ----------------------------------------------- Employee Methods -----------------------------------------------------------------
    */

    // Find highest earning employee
    public List<Employee> getHighestEarningEmployee() throws SQLException {
        String query = "select eid, fname, lname, age, sex, salary from employee where salary in (" +
                "select max(salary) from employee)";
        ArrayList<Employee> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Employee e = new Employee(
                    rs.getInt("eid"),
                    rs.getInt("salary"),
                    rs.getInt("age"),
                    rs.getString("sex"),
                    rs.getString("fname"),
                    rs.getString("lname")
            );
            list.add(e);
        }
        conn.close();
        return list;
    }

    // Find lowest earning employee
    public List<Employee> getLowestEarningEmployee() throws SQLException {
        String query = "select eid, fname, lname, age, sex, salary from employee where salary in (" +
                       "select min(salary) from employee)";
        ArrayList<Employee> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Employee e = new Employee(
                    rs.getInt("eid"),
                    rs.getInt("salary"),
                    rs.getInt("age"),
                    rs.getString("sex"),
                    rs.getString("fname"),
                    rs.getString("lname")
            );
            list.add(e);
        }
        conn.close();
        return list;
    }

    // Selecting all dentists who have attended all customers
    public List<Dentist> getAllDentistsAttended() throws SQLException {
        String query = "select d.fname, d.lname, d.did, d.age, d.sex from dentist d where not exists ((" +
                "select cid from customer) MINUS (select cid from attends a where a.did = d.did))";
        ArrayList<Dentist> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Dentist e = new Dentist(
                    rs.getInt("did"),
                    rs.getString("fname"),
                    rs.getString("lname"),
                    rs.getInt("age"),
                    rs.getString("sex")
            );
            list.add(e);
        }
        conn.close();
        return list;
    }

    // Registers an employee into the system, letting them have access to the application
    // Employee credentials are username, pw
    // type can only be one of: d, r, or h
    public void registerEmployee(String username, String pw,
                                 String type,
                                 String fname, String lname, int age,
                                 String sex, long phoneNum) throws SQLException {
        int eid = getHighestEmployeeID() + 1;
        addEmployee(eid, fname, lname, 0, age, sex, phoneNum, 0);
        String salt = BCrypt.gensalt();
        String hashpass = BCrypt.hashpw(pw, salt);
        String query = "insert into login_details values (?, ?, ?, ?, ?)";
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, hashpass);
        ps.setString(3, salt);
        ps.setString(4, type);
        ps.setInt(5, eid);
        ps.executeUpdate();
        conn.close();
    }

    public void addEmployee(int eid, String fname, String lname, int salary, int age, String sex, long phoneNum, int isSupervisor) throws SQLException {
        Connection conn = getConnection();
        String query = "INSERT INTO EMPLOYEE VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, eid);
        ps.setString(2, fname);
        ps.setString(3, lname);
        ps.setInt(4, salary);
        ps.setInt(5, age);
        ps.setString(6, sex);
        ps.setLong(7, phoneNum);
        ps.setInt(8, isSupervisor);
        ps.executeUpdate();
        conn.close();
    }

    public void updateEmployee(int eid, String fname, String lname, int salary, int age, String sex) throws SQLException {
        Connection conn = getConnection();
        String query = "UPDATE employee SET fname = ?, lname = ?, salary = ?, age = ?, sex = ? WHERE eid = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        //  ps.executeQuery();
        ps.setString(1, fname);
        ps.setString(2, lname);
        ps.setInt(3, salary);
        ps.setInt(4, age);
        ps.setString(5, sex);
        //     ps.setLong(6, phoneNum);
        ps.setInt(6, eid);
        ps.executeUpdate();
        conn.close();
    }

    public void removeEmployee(int eid) throws SQLException {
        Connection conn = getConnection();
        String query = "DELETE FROM EMPLOYEE WHERE eid = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, eid);
        ps.executeQuery();
    }

    public List<Employee> employeeSearchByFirstName(String fname) throws SQLException {
        String query = "select e.eid AS EmployeeID, e.fname AS FirstName, e.lname AS LastName, e.salary AS Salary, e.age AS Age, e.sex AS Sex, " +
                "phoneNum AS PhoneNumber from Employee e WHERE e.fname LIKE \'%" + fname + "%\'";
        ArrayList<Employee> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, fname);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Employee e = new Employee(
                    rs.getInt("EmployeeID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getInt("Salary"),
                    rs.getInt("Age"),
                    rs.getString("Sex"),
                    rs.getLong("PhoneNumber")
            );
            list.add(e);
        }
        conn.close();
        return list;
    }

    // The default view that an employee will have
    // Creates a list of customer objects that user must iterate through to handle
    public List<Employee> employeeViewDefaultTable() throws SQLException {
        String query = "select * from Employee";

        List<Employee> list = new ArrayList<>();
        Connection conn = getConnection();

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Employee e = new Employee(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getInt(5),
                    rs.getString(6),
                    rs.getLong(7));
            list.add(e);
        }

        return list;
    }


    // Filters employees by eid
    // Shows only those with eid = parameter eid
    // Creates a list of Employee objects that user must iterate through to handle
    public ArrayList<Employee> empSearchByEID(int eid) throws SQLException {
        String query = "select eid, fname, lname, age, sex, " +
                       "phone_Num from Employee where eid = ?";
        ArrayList<Employee> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, eid);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Employee e = new Employee(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getString(5),
                    rs.getLong(6));
            list.add(e);
        }
        return list;
    }

    // Filters customers by lname
    // Shows only those with lname = parameter lname
    // Creates a list of customer objects that user must iterate through to handle
    public List<Employee> empSearchByLastName(String lname) throws SQLException {
        String query = "select eid, fname, lname, age, sex, " +
                "phone_Num from Employee where lname = ?";
        ArrayList<Employee> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, lname);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Employee e = new Employee(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getString(5),
                    rs.getLong(6));
            list.add(e);
        }
        return list;
    }

    // Get highest Customer ID
    public int getHighestEmployeeID() throws SQLException {
        String query = "select max(eid) from employee";
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        int result = 0;
        while (rs.next()) {
            result = rs.getInt(1);
        }
        conn.close();
        return result;
    }

    /* ------------------------------------------------------------------------------------------------------------------------------- //
    ----------------------------------------------- Supervisor Methods -----------------------------------------------------------------
    */

    // Finds all supervisors who are dentists
    public List<Dentist> getDentistsWhoAreSupervisors() throws SQLException {
        String query = "select * from employee where isSupervisor = 1";
        List<Dentist> list = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute(query);
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            Dentist b = new Dentist(
                    rs.getInt("eid"),
                    rs.getString("fname"),
                    rs.getString("lname")
            );
            list.add(b);
        }
        conn.close();
        return list;
    }

    // Finds all supervisors who are dentists
    public List<Hygienist> getHygienistsWhoAreSupervisors() throws SQLException {
        String query = "select * from employee where isSupervisor = 1";
        List<Hygienist> list = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute(query);
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            Hygienist b = new Hygienist(
                    rs.getInt("eid"),
                    rs.getString("fname"),
                    rs.getString("lname"),
                    rs.getInt("salary"),
                    rs.getInt("age"),
                    rs.getString("sex"),
                    rs.getLong("phone_Num")
            );
            list.add(b);
        }
        conn.close();
        return list;
    }

    // Finds all supervisors who are dentists
    public List<Receptionist> getReceptionistsWhoAreSupervisors() throws SQLException {
        String query = "select * from employee where isSupervisor = 1";
        List<Receptionist> list = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute(query);
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            Receptionist b = new Receptionist(
                    rs.getInt("eid"),
                    rs.getString("fname"),
                    rs.getString("lname"),
                    rs.getInt("salary"),
                    rs.getInt("age"),
                    rs.getString("sex"),
                    rs.getLong("phone_Num")
            );
            list.add(b);
        }
        conn.close();
        return list;
    }

    // Find all employees who work for this supervisor
    public List<Employee> getEmployeesUnderSupervisor(int eid) throws SQLException {
        String query = "select e.eid, e.fname, e.lname, e.salary, e.age, e.sex, e.phone_Num" +
                " from Employee e, WorksFor w" +
                " where w.sid = ? and w.eid = e.eid";
        List<Employee> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, eid);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Employee b = new Employee(
                    rs.getInt("eid"),
                    rs.getString("fname"),
                    rs.getString("lname"),
                    rs.getInt("salary"),
                    rs.getInt("age"),
                    rs.getString("sex"),
                    rs.getLong("phone_Num")
            );
            list.add(b);
        }
        conn.close();
        return list;
    }

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
        String query = "select c.cid as id, c.fname as Name, " +
                "c.lname as Surname, b.type as t, " +
                "b.amountOwes as owes, b.dueDate as due " +
                "from Customer c, Bill b where b.cid = ? " +
                "and c.cid = ? and b.cid = c.cid and CURRENT_DATE > dueDate and isPaid = 1";
        List<Bill> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, cid);
        ps.setInt(2, cid);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Bill b = new Bill(
                    rs.getInt("id"),
                    rs.getString("Name"),
                    rs.getString("Surname"),
                    rs.getString("t"),
                    rs.getDate("due"),
                    rs.getBigDecimal("owes")
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
        String query = "select c.cid as id, c.fname as Name, c.lname as Surname, b.type as type, b.dueDate as Due, " +
                "b.amountPaid as Payment, (b.amountOwes - b.amountPaid) as Balance" +
                " from Customer c, Bill b where b.cid = \'" + cid + "\' and c.cid = \'" + cid + "\' and b.isPaid = 0";
        List<Bill> list = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute(query);
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            Bill b = new Bill(
                    rs.getInt("id"),
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


    // Finds specific appointment records for all customers by Last Name
    public List<Appointment> searchCustomerAppointmentByLName(String key) throws SQLException {
        String query = "select * from customer c, appointment a where a.cid = c.cid and CURRENT_TIMESTAMP <= a.from_Time and lname = ?";
        List<Appointment> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, key);
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

    // Finds specific appointment records for all customers by cid
    public List<Appointment> searchCustomerAppointmentByCid(int cid) throws SQLException {
        String query = "select * from customer c, appointment a where a.cid = c.cid and CURRENT_TIMESTAMP <= a.from_Time and c.cid = ?";
        List<Appointment> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, cid);
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

    /* ------------------------------------------------------------------------------------------------------------------------------- //
    ----------------------------------------------- Appointment Methods -----------------------------------------------------------------
    */

    // Find out if a cid is valid or not
    // Returns true if cid is in use
    // False otherwise
    public boolean isValidAppNum(int appID) throws SQLException {
        String query = "select * from Appointment where num = ?";
        //  List<Customer> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, appID);
        ResultSet rs = ps.executeQuery();
        boolean valid = false;
        if (rs.next()) {
            valid = true;
        }
        conn.close();
        return valid;
    }

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


    // Get appointment num of the latest appointment
    public int getLatestAppointmentNum() throws Exception {
        String query = "select num from appointment where num > ALL (select num from appointment)";
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute(query);
        ResultSet rs = stmt.getResultSet();
        int result = 0;
        while (rs.next()) {
            result = rs.getInt("num");
        }
        conn.close();
        return result;
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

    // The default view that bill table will have
    // Creates a list of bill objects that user must iterate through to handle
    public List<Bill> billsDefaultView() throws SQLException {
        String query = "select c.cid as id, c.fname as f, c.lname as l, b.type as t, b.dueDate as d, b.amountPaid as p, (b.amountOwes - b.amountPaid) as bal" +
                       " from Bill b, Customer c where c.cid = b.cid";
        List<Bill> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Bill m = new Bill(
                    rs.getInt("id"),
                    rs.getString("f"),
                    rs.getString("l"),
                    rs.getString("t"),
                    rs.getDate("d"),
                    rs.getBigDecimal("p"),
                    rs.getBigDecimal("bal")
            );
            list.add(m);
        }

        return list;
    }

    /* ------------------------------------------------------------------------------------------------------------------------------- //
    ----------------------------------------------- Medicine Methods -----------------------------------------------------------------
    */

    // Search in medicine description for this term
    // returns all medicines that have searchTerm in the description
    public List<Medicine> medSearchByDescriptionTerm(String searchTerm) throws SQLException {
        String query = "select * from Medicine where description like \'%" + searchTerm + "%\'";
        List<Medicine> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Medicine m = new Medicine(
                    rs.getInt(1),
                    rs.getDouble(2),
                    rs.getString(3)
            );
            list.add(m);
        }

        return list;
    }

    // The default view that medicine table will have
    // Creates a list of medicine objects that user must iterate through to handle
    public List<Medicine> medDefaultView() throws SQLException {
        String query = "select * from Medicine";

        List<Medicine> list = new ArrayList<>();
        Connection conn = getConnection();

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            Medicine m = new Medicine(
                    rs.getInt(1),
                    rs.getDouble(2),
                    rs.getString(3)
            );
            list.add(m);
        }

        return list;
    }

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

    // Finds medicines for customer
    public List<Medicine> getCustomerMedicines(int cid) throws SQLException {
        String query = "SELECT m.code AS CODE, m.cost AS COST, m.description AS Description from Treats t, Medicine m where " +
                "t.code = m.code and t.cid = ?";
        List<Medicine> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, cid);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Medicine m = new Medicine(
                    rs.getInt("CODE"),
                    rs.getDouble("COST"),
                    rs.getString("DESCRIPTION"));
            list.add(m);
        }
        return list;
    }

    // Finds medicines for customer by last name
    public List<Medicine> getCustomerMedicines(String lname) throws SQLException {
        String query = "SELECT m.code AS CODE, m.cost AS COST, m.description AS Description from Treats t, Medicine m, Customer c where " +
                "t.code = m.code and t.cid = c.cid and c.lname like \'%" + lname + "%\'";
        List<Medicine> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Medicine m = new Medicine(
                    rs.getInt("CODE"),
                    rs.getDouble("COST"),
                    rs.getString("DESCRIPTION"));
            list.add(m);
        }
        return list;
    }

    // Finds cheapest medicine(s)
    public List<Medicine> getCheapestMedicine() throws SQLException {
        String query = "select code, description, cost from medicine where cost in (select min(cost) from medicine)";
        List<Medicine> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Medicine m = new Medicine(
                    rs.getInt("code"),
                    rs.getDouble("cost"),
                    rs.getString("description")
                    );
            list.add(m);
        }
        return list;
    }


    // Finds customers who are prescribed this medicine
    public List<Customer> getCustomersWithMedicine(int code) throws SQLException {
        String query = "select c.cid as id, c.fname as f, c.lname as l, c.phone_Num as p, c.email as e, c.address as a " +
                       "from Customer c, Medicine m, Treats t where c.cid = t.cid and m.code = t.code and m.code = ?";
        List<Customer> list = new ArrayList<>();
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, code);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Customer m = new Customer(
                    rs.getInt("id"),
                    rs.getString("f"),
                    rs.getString("l"),
                    rs.getLong("p"),
                    rs.getString("e"),
                    rs.getString("a"));
            list.add(m);
        }
        return list;
    }

}