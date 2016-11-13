import java.sql.Date;

/**
 * Created by mark on 2016-11-07.
 */
public class Employee {
    private int eid;
    private int salary;
    private int age;
    private String sex;
    private Date dob;
    private long phoneNum;
    private String fname;
    private String lname;

    public Employee(int eid, String fname, String lname, int salary, int age, String sex, Date dob, long phoneNum) {
        this.eid = eid;
        this.salary = salary;
        this.age = age;
        this.sex = sex;
        this.dob = dob;
        this.phoneNum = phoneNum;
        this.lname = lname;
        this.fname = fname;
    }

    public Employee(int did, String fname, String lname) {
        this.eid = did;
        this.fname = fname;
        this.lname = lname;
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

    public Date getDob() {
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

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public long getPhoneNum() {
        return phoneNum;
    }

    public String getLname() {
        return lname;
    }

    public String getFname() {
        return fname;
    }
}
