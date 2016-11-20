import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mark on 2016-11-07.
 */
public class Customer {
    private int cid;
    private long phoneNum;
    private String fname;
    private String lname;
    private Date birthday;
    private String email;
    private String address;
    private String dentist; //Customer's currently assigned dentist - can be null if no assigned dentist at time

    public Customer(int cid, String fname, String lname, long phoneNum, String email, String address) {
        this.cid = cid;
        this.phoneNum = phoneNum;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.address = address;
    }

    public Customer(int cid, String fname, String lname, long phoneNum, Date birthday, String email, String address) {
        this.cid = cid;
        this.phoneNum = phoneNum;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.address = address;
        this.birthday = birthday;
    }

    public Customer(int cid, String fname, String lname, long phoneNum, Date birthday, String email, String address, String dentist) {
        this.cid = cid;
        this.phoneNum = phoneNum;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.address = address;
        this.birthday = birthday;
        this.dentist = dentist;
    }

    public void setDentist(String dentist) {
        this.dentist = dentist;
    }

    public void setCID(int cid) {
        this.cid = cid;
    }

    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCID() {
        return cid;
    }

    public Date getBirthday() {
        return birthday;
    }

    public long getPhoneNum() {
        return phoneNum;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getDentist() {
        return dentist;
    }
}


