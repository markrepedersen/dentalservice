import java.sql.Date;

/**
 * Created by mark on 2016-11-07.
 */
public class Dentist extends Employee {
    private int did;
    private int salary;
    private int age;
    private String sex;
    private Date dob;
    private long phoneNum;
    private String fname;
    private String lname;

    public Dentist(int eid, String fname, String lname, int salary, int age, String sex, Date dob, long
            phoneNum, int did, String fname1, String lname1) {
        super(eid, fname, lname, salary, age, sex, dob, phoneNum);
        this.did = did;
        this.fname = fname1;
        this.lname = lname1;
    }

    public Dentist(int did, String fname, String lname) {
        super(did, fname, lname );
    }

    public int getDid() {
        return did;
    }

    @Override
    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public void setFname(String fname) {
        this.fname = fname;
    }

    @Override
    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setDid(int did) {

        this.did = did;
    }

    @Override
    public int getSalary() {
        return salary;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public String getSex() {
        return sex;
    }

    @Override
    public Date getDob() {
        return dob;
    }

    @Override
    public long getPhoneNum() {
        return phoneNum;
    }

    @Override
    public String getFname() {
        return fname;
    }

    @Override
    public String getLname() {
        return lname;
    }
}
