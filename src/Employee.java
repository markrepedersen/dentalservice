import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by mark on 2016-11-07.
 */
public class Employee {
    private int eid;
    private int salary;
    private int age;
    private String sex;
    private String dob;
    private String phoneNum;
    private String lname;
    private String fname;

    public Employee(int eid, int salary, int age, String sex, String dob, String phoneNum, String lname, String fname) {
        this.eid = eid;
        this.salary = salary;
        this.age = age;
        this.sex = sex;
        this.dob = dob;
        this.phoneNum = phoneNum;
        this.lname = lname;
        this.fname = fname;
    }

    public int getEid() {
        return eid;
    }

    public int getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public String getDob() {
        return dob;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getLname() {
        return lname;
    }

    public String getFname() {
        return fname;
    }
}
