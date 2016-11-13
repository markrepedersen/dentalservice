import java.sql.Date;

/**
 * Created by mark on 2016-11-07.
 */
public class Receptionist {
    private int did;
    private int salary;
    private int age;
    private String sex;
    private Date dob;
    private long phoneNum;
    private String fname;
    private String lname;

    public Receptionist(int did, String fname, String lname, int salary, int age, String sex, Date dob, long
            phoneNum) {
        this.did = did;
        this.fname = fname;
        this.lname = lname;
        this.salary = salary;
        this.age = age;
        this.sex = sex;
        this.dob = dob;
        this.phoneNum = phoneNum;
    }
}
