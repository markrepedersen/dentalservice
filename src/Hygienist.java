/**
 * Created by mark on 2016-11-07.
 */
public class Hygienist extends Employee {
    private int did;
    private int salary;
    private int age;
    private String sex;
    private String dob;
    private String phoneNum;
    private String fname;
    private String lname;

    public Hygienist(int did, int salary, int age, String sex, String dob, long phoneNum, String fname, String lname) {
        super(did, salary, age, sex, dob, phoneNum, fname, lname);
    }
}