import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mark on 2016-11-09.
 */
public class DBHandlerTest {
    Connection conn;
    DBHandler db;
    private static boolean setUpIsDone = false;


    @Before
    public void setUp() throws Exception {
        db = new DBHandler();
        conn = db.getConnection();
        conn.prepareStatement("commit").execute();
        if (setUpIsDone) {
            return;
        }
        db.addCustomer(123456, "mark", "p", 6049970855L, new Date(System.currentTimeMillis()), "hello@gmail.com", "asdf");
        db.addCustomer(234567, "loa", "be", 6049970855L, new Date(System.currentTimeMillis()), "hello@gmail.com", "asdf");
        db.addCustomer(345678, "john", "hamm", 6049970855L, new Date(System.currentTimeMillis()), "hello@gmail.com", "asdf");
        db.getConnection().prepareStatement("insert into Attends values (0000331, 123456)").executeQuery();
        db.getConnection().prepareStatement("insert into Attends values (0000331, 234567)").executeQuery();
        db.getConnection().prepareStatement("insert into Attends values (0000331, 345678)").executeQuery();
        db.addBill(90000, "yo", new BigDecimal(10000.00), new BigDecimal(45000.00), new Date(System.currentTimeMillis() + 100000),
                0, 123456);
        db.addBill(90001, "yo", new BigDecimal(20000.00), new BigDecimal(45000.00), new Date(System.currentTimeMillis() + 100000),
                0, 123456);
        db.addBill(90002, "yo", new BigDecimal(30000.00), new BigDecimal(45000.00), new Date(System.currentTimeMillis() + 100000),
                0, 123456);
        db.addBill(90003, "yo", new BigDecimal(1.00), new BigDecimal(1000.00), new Date(System.currentTimeMillis() + 100000),
                0, 234567);
        db.addBill(90004, "yo", new BigDecimal(2.00), new BigDecimal(2000.00), new Date(System.currentTimeMillis() + 100000),
                0, 234567);
        db.addBill(90005, "yo", new BigDecimal(3.00), new BigDecimal(200.99), new Date(System.currentTimeMillis() + 100000),
                0, 234567);
        db.addBill(90006, "yo", new BigDecimal(123.90), new BigDecimal(12313.00), new Date(System.currentTimeMillis() + 100000),
                0, 345678);
        db.addBill(90007, "yo", new BigDecimal(234.33), new BigDecimal(12313.90), new Date(System.currentTimeMillis() + 100000),
                0, 345678);
        db.addBill(90008, "yo", new BigDecimal(231.33), new BigDecimal(325.98), new Date(System.currentTimeMillis() + 100000),
                0, 345678);

        db.registerEmployee("Mark", "Pedersen", "d", "Mark", "Pedersen", 12, "m", 6049970855L);
        db.registerEmployee("Abhinav", "Behera", "h", "Mark", "Pedersen", 12, "m", 6049970855L);
        db.registerEmployee("Liam", "Adams", "d", "Mark", "Pedersen", 12, "m", 6049970855L);
        db.registerEmployee("Theo", "Lau", "r", "Mark", "Pedersen", 12, "m", 6049970855L);
        setUpIsDone = true;
        conn.prepareStatement("commit").execute();
    }

    // Expects a:
    //  ->  1 if dentist
    //  ->  2 if hygienist
    //  ->  3 if receptionist
    //  -> -1 if not found
    @Test
    public void queryLoginInfo() throws Exception {
        assertEquals(db.queryLoginInfo("Mark", "Pedersen"), 1);
        assertEquals(db.queryLoginInfo("Abhinav", "Behera"), 2);
        assertNotEquals(db.queryLoginInfo("Abhinav", "ljsfld"), 2);
        assertEquals(db.queryLoginInfo("Abhinav", "ljsfld"), -1);
        assertEquals(db.queryLoginInfo("Liam", "Adams"), 1);
        assertNotEquals(db.queryLoginInfo("Theo", "Lau"), 2);
        assertEquals(db.queryLoginInfo("Theo", "Lau"), 3);
    }

    @Test
    public void getCustomerPastPayments() throws SQLException {
        List<Bill> list = db.getCustomerPastPayments(111801);
        //assertEquals(list.size(), 1);
        for (int i = 0; i < list.size();i++) {
            System.out.println(list.get(i).getAmountPaid());
        }
        //assertEquals(list.get(0).getAmountPaid(), new BigDecimal(100.00));
        for (Bill b : list) {
            System.out.println("name: " + b.getCname() + b.getSurname());
            System.out.println("payments: " + b.getAmountPaid());
            System.out.println();
            System.out.println();
        }
    }

    @Test
    public void getCustomerWith2Payments() throws SQLException {
        List<Customer> list = db.getCustomerWith2Payments();
        //assertEquals(list.size(), 1);
        for (int i = 0; i < list.size();i++) {
            System.out.println(list.get(i).getCID());
        }
        //assertEquals(list.get(0).getAmountPaid(), new BigDecimal(100.00));
    }

    @Test
    public void getCustomerBills() throws Exception {
        assertEquals(db.getCustomerBills(234567).size(), 3);
        List<Bill> bills = db.getCustomerBills(234567);
        for (Bill b : bills) {
            System.out.println("name of customer: " + b.getCname() + b.getSurname());
            System.out.println("Balance: " + b.getBalance());
            System.out.println("Paid: " + b.getAmountPaid());
            System.out.println();
            System.out.println();
        }
    }

    @Test
    public void customerViewDefaultTable() throws Exception {
        List<Customer> custs = db.customerViewDefaultTable();
        assertEquals(custs.size(), 17);
        for (Customer c : custs) {
            System.out.println("name of customer: " + c.getFname() + c.getLname());
            System.out.println("cid: " + c.getCID());
            System.out.println();
            System.out.println();
        }
    }

    @Test
    public void customerSearchByCID() throws Exception {
        List<Customer> custs = db.customerSearchByCID(111801);
        assertEquals(custs.size(), 1);
        for (Customer c : custs) {
            System.out.println("name of customer: " + c.getFname() + c.getLname());
            System.out.println("cid: " + c.getCID());
            System.out.println();
            System.out.println();
        }
    }

    @Test
    public void customerSearchByName() throws Exception {
        List<Customer> custs = db.customerSearchByFirstName("Mark");
        assertEquals(custs.size(), 3);
        for (Customer c : custs) {
            System.out.println("name of customer: " + c.getFname() + c.getLname());
            System.out.println("cid: " + c.getCID());
            System.out.println();
            System.out.println();
        }
    }

    @Test
    public void customerSearchByLastName() throws Exception {
        List<Customer> custs = db.customerSearchByLastName("Pedersen");
        assertEquals(custs.size(), 3);
        for (Customer c : custs) {
            System.out.println("name of customer: " + c.getFname() + c.getLname());
            System.out.println("cid: " + c.getCID());
            System.out.println();
            System.out.println();
        }
    }

    @Test
    public void getUpcomingCustomerAppointmentsByCID() throws Exception {
        List<Appointment> custs = db.getUpcomingCustomerAppointmentsByCID(111801);
        assertEquals(custs.size(), 2);
        for (Appointment c : custs) {
            System.out.println("num: " + c.getNum());
            System.out.println("from: " + c.getFromTime());
            System.out.println("til: " + c.getToTime());
            System.out.println();
            System.out.println();
        }
    }
/*
    @Test
    public void getCustomerUnpaidBills() throws Exception {
        assertEquals(db.getCustomerBills(123456).size(), 1);
        assertEquals(db.getCustomerYearlyPayments(123456).get(0).getAmountPaid(), new BigDecimal(60000.00));
    }
*/

    @Test
    public void empSearchByLastName() throws SQLException {
        List<Employee> emps = db.empSearchByLastName("Smith");
        assertEquals(emps.size(), 1);
        for (Employee c : emps) {
            System.out.println("eid: " + c.getEid());
            System.out.println("fname: " + c.getFname());
            System.out.println("lname: " + c.getLname());
            System.out.println();
            System.out.println();
        }
    }


    @Test
    public void getUpcomingCustomerAppointments() throws Exception {
        List<Appointment> custs = db.getUpcomingCustomerAppointments();
        assertEquals(custs.size(), 10);
        for (Appointment c : custs) {
            System.out.println("cid: " + c.getCid());
            System.out.println("fname: " + c.getCname());
            System.out.println("lname: " + c.getCsurname());
            System.out.println("num: " + c.getNum());
            System.out.println("from: " + c.getFromTime());
            System.out.println("til: " + c.getToTime());
            System.out.println();
            System.out.println();
        }
    }

    @Test
    public void getEmployeesUnderSupervisor() throws Exception {
        List<Appointment> custs = db.getUpcomingCustomerAppointments();
        assertEquals(custs.size(), 10);
        for (Appointment c : custs) {
            System.out.println("cid: " + c.getCid());
            System.out.println("fname: " + c.getCname());
            System.out.println("lname: " + c.getCsurname());
            System.out.println("num: " + c.getNum());
            System.out.println("from: " + c.getFromTime());
            System.out.println("til: " + c.getToTime());
            System.out.println();
            System.out.println();
        }
    }

    @Test
    public void getPastCustomerAppointments() throws Exception {
        List<Appointment> custs = db.getPastCustomerAppointments(111801);
        assertEquals(custs.size(), 2);
        for (Appointment c : custs) {
            System.out.println("num: " + c.getNum());
            System.out.println("from: " + c.getFromTime());
            System.out.println("til: " + c.getToTime());
            System.out.println();
            System.out.println();
        }
    }

    @Test
    public void getAllDentistsAttended() throws Exception {
        List<Dentist> custs = db.getAllDentistsAttended();
        assertEquals(custs.size(), 1);
        System.out.println(custs.size());
        for (Dentist c : custs) {
            System.out.println("num: " + c.getDid());
            System.out.println("name: " + c.getFname() + c.getLname());
            System.out.println();
            System.out.println();
        }
    }

    @Test
    public void getCheapestMedicine() throws Exception {
        List<Medicine> custs = db.getCheapestMedicine();
        assertEquals(custs.size(), 1);
        System.out.println(custs.size());
        for (Medicine c : custs) {
            System.out.println("code: " + c.getCode());
            System.out.println("cost: " + c.getCost());
            System.out.println();
            System.out.println();
        }
    }


    @Test
    public void simpleLoginTest() throws SQLException {
        db.registerEmployee("m", "m", "d", "mark", "mark", 99, "m", 6048887777L);
        assertEquals(db.queryLoginInfo("m", "m"), 1);
    }


}