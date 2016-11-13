import java.sql.Date;

/**
 * Created by mark on 2016-11-07.
 */
public class Receptionist extends Employee {
    private int did;
    private int salary;
    private int age;
    private String sex;
    private Date dob;
    private String phoneNum;
    private String fname;
    private String lname;

    public Receptionist(int did, String fname, String lname, int salary, int age, String sex, Date dob, long phoneNum) {
        super(did, fname, lname, salary, age, sex, dob, phoneNum);
    }
}
