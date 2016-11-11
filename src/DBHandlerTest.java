import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

/**
 * Created by mark on 2016-11-09.
 */
public class DBHandlerTest {
    Connection conn;
    DBHandler db;

    @Before
    public void setUp() throws Exception {
        db = new DBHandler();
        conn = db.getConnection();
    }

    // Expects a:
    //  ->  1 if dentist
    //  ->  2 if hygienist
    //  ->  3 if receptionist
    //  -> -1 if not found
    @Test
    public void queryLoginInfo() throws Exception {
        Statement s = conn.createStatement();
        db.registerEmployee("Mark", "Pedersen", "d");
        db.registerEmployee("Abhinav", "Behera", "h");
        db.registerEmployee("Liam", "Adams", "d");
        db.registerEmployee("Theo", "Lau", "r");

        assertEquals(db.queryLoginInfo("Mark", "Pedersen"), 1);
        assertEquals(db.queryLoginInfo("Abhinav", "Behera"), 2);
        assertNotEquals(db.queryLoginInfo("Abhinav", "ljsfld"), 2);
        assertEquals(db.queryLoginInfo("Abhinav", "ljsfld"), -1);
        assertEquals(db.queryLoginInfo("Liam", "Adams"), 1);
        assertNotEquals(db.queryLoginInfo("Theo", "Lau"), 2);
        assertEquals(db.queryLoginInfo("Theo", "Lau"), 3);
    }

    @Test
    public void getCustomerYearlyPayments() throws SQLException {
        db.addCustomer(123456, "hi", "there", 6049970855L, new Date(System.currentTimeMillis()), "hello@gmail.com", "asdf");
        db.addCustomer(234567, "what\'s", "up", 6049970855L, new Date(System.currentTimeMillis()), "hello@gmail.com", "asdf");
        db.addCustomer(345678, "not", "much", 6049970855L, new Date(System.currentTimeMillis()), "hello@gmail.com", "asdf");

        db.addBill(90000, "yo", new BigDecimal(10000.00), new BigDecimal(45000.00), new Date(System.currentTimeMillis() + 100000),
                   0, 123456);
        db.addBill(90001, "yo", new BigDecimal(20000.00), new BigDecimal(45000.00), new Date(System.currentTimeMillis() + 100000),
                0, 123456);
        db.addBill(90002, "yo", new BigDecimal(30000.00), new BigDecimal(45000.00), new Date(System.currentTimeMillis() + 100000),
                0, 123456);
        db.addBill(90003, "yo", new BigDecimal(40000.00), new BigDecimal(1.00), new Date(System.currentTimeMillis() + 100000),
                0, 234567);
        db.addBill(90004, "yo", new BigDecimal(50000.00), new BigDecimal(2.00), new Date(System.currentTimeMillis() + 100000),
                0, 234567);
        db.addBill(90005, "yo", new BigDecimal(60000.00), new BigDecimal(3.00), new Date(System.currentTimeMillis() + 100000),
                0, 234567);
        db.addBill(90006, "yo", new BigDecimal(70000.00), new BigDecimal(4.5), new Date(System.currentTimeMillis() + 100000),
                0, 345678);
        db.addBill(90007, "yo", new BigDecimal(80000.00), new BigDecimal(4.5), new Date(System.currentTimeMillis() + 100000),
                0, 345678);
        db.addBill(90008, "yo", new BigDecimal(90000.00), new BigDecimal(4.5), new Date(System.currentTimeMillis() + 100000),
                0, 345678);
        assertEquals(db.getCustomerYearlyPayments(123456).size(), 1);
        assertEquals(db.getCustomerYearlyPayments(123456).get(0).getAmountPaid(), new BigDecimal(60000.00));


    }

    @Test
    public void customerViewDefaultTable() throws Exception {

    }

    @Test
    public void customerSearchByCID() throws Exception {

    }

    @Test
    public void customerSearchByName() throws Exception {

    }

    @Test
    public void customerSearchByLastName() throws Exception {

    }

    @Test
    public void getUpcomingCustomerAppointments() throws Exception {

    }

    @Test
    public void getPastCustomerAppointments() throws Exception {

    }

    @Test
    public void addCustomer() throws Exception {

    }

    @Test
    public void removeCustomer() throws Exception {

    }

    @Test
    public void addEmployee() throws Exception {

    }

    @Test
    public void removeEmployee() throws Exception {

    }

    @Test
    public void addAppointment() throws Exception {

    }

    @Test
    public void removeAppointment() throws Exception {

    }

}