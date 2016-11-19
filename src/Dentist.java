import java.sql.Date;

/**
 * Created by mark on 2016-11-07.
 */
public class Dentist {
    private int did;
    private int salary;
    private int age;
    private String sex;
    private long phoneNum;
    private String fname;
    private String lname;

    public Dentist(int did, String fname, String lname, int age, String sex) {
        this.did = did;
        this.age = age;
        this.sex = sex;
        this.fname = fname;
        this.lname = lname;
    }

    public Dentist(int did, String fname, String lname, int salary, int age, String sex, long
            phoneNum) {
        this.did = did;
        this.fname = fname;
        this.lname = lname;
        this.salary = salary;
        this.age = age;
        this.sex = sex;
        this.phoneNum = phoneNum;
    }

    public Dentist(int did, String fname, String lname) {
        this.did = did;
        this.fname = fname;
        this.lname = lname;
    }

    public int getDid() {
        return did;
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


    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setDid(int did) {

        this.did = did;
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


    public long getPhoneNum() {
        return phoneNum;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }
}
