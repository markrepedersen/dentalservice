/**
 * Created by mark on 2016-11-07.
 */
public class Receptionist extends Employee {
    private int did;
    private int salary;
    private int age;
    private String sex;
    private String dob;
    private String phoneNum;
    private String fname;
    private String lname;

    public Receptionist(int did, int salary, int age, String sex, String dob, String phoneNum, String fname, String lname) {
        super(did, salary, age, sex, dob, phoneNum, fname, lname);
    }
}
