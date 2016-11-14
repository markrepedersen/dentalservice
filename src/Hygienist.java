import java.sql.Date;

/**
 * Created by mark on 2016-11-07.
 */
public class Hygienist {
    private int did;
    private int salary;
    private int age;
    private String sex;
    private long phoneNum;
    private String fname;
    private String lname;

    public int getDid() {
        return did;
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

    public Hygienist(int did, String fname, String lname, int salary, int age, String sex,  long
            phoneNum) {
        this.did = did;
        this.fname = fname;
        this.lname = lname;
        this.salary = salary;
        this.age = age;
        this.sex = sex;
        this.phoneNum = phoneNum;
    }
}