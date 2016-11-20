import javax.swing.*;

/**
 * Created by mark on 2016-11-19.
 */
public class CustForAptmt extends JFrame {
    private JLabel cidLabel;
    private JLabel lnameLabel;
    private JLabel pNumLabel;
    private JLabel emailLabel;
    private JLabel addressLabel;
    private JPanel menuPane;
    private JLabel cid;
    private JLabel fname;
    private JLabel lname;
    private JLabel phone;
    private JLabel email;
    private JLabel address;

    public CustForAptmt(int cid, String fname, String lname, long phone, String email, String address) {
        super("Customer");
        setContentPane(menuPane);
        this.cid.setText(String.valueOf(cid));
        this.fname.setText(fname);
        this.lname.setText(lname);
        this.phone.setText(String.valueOf(phone));
        this.email.setText(email);
        this.address.setText(address);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
