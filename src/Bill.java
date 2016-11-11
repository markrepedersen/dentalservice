import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by mark on 2016-11-10.
 */
public class Bill {
    private String cname;
    private String surname;
    private int bid;
    private String     type;
    private BigDecimal amountPaid;
    private BigDecimal amountOwes;
    private BigDecimal balance;
    private Date       dueDate;
    private int        isPaid;
    private int        cid;

    //default constructor with all fields
    public Bill(int bid, String type, BigDecimal amountPaid, BigDecimal amountOwes, Date dueDate, int isPaid, int cid) {
        this.bid = bid;
        this.type = type;
        this.amountPaid = amountPaid;
        this.amountOwes = amountOwes;
        this.dueDate = dueDate;
        this.isPaid = isPaid;
        this.cid = cid;
    }

    // constructor for getCustomerBills method
    public Bill(String name, String surname, String type, Date dueDate, BigDecimal payment, BigDecimal balance) {
        this.cname = name;
        this.surname = surname;
        this.type = type;

        this.dueDate = dueDate;
        this.amountPaid = payment;
        this.balance = balance;
    }

    // constructor for getCustomerYearlyPayments method
    public Bill(String name, String surname, String type, Date dueDate, BigDecimal payment) {
        this.cname = name;
        this.surname = surname;
        this.type = type;
        this.dueDate = dueDate;
        this.amountPaid = payment;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCname() {
        return cname;

    }

    public String getSurname() {
        return surname;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setAmountOwes(BigDecimal amountOwes) {
        this.amountOwes = amountOwes;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }


    public String getType() {
        return type;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public BigDecimal getAmountOwes() {
        return amountOwes;
    }

    public Date getDueDate() {
        return dueDate;
    }

}
