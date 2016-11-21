import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by mark on 2016-11-13.
 */
public class RegisterForm {
    private JTextField username;
    private JPasswordField password;
    private JButton registerButton;
    private JButton Cancel;
    private JPanel registerPanel;
    private JComboBox type;
    private JTextField fname;
    private JTextField lname;
    private JTextField age;
    private JComboBox sex;
    private JTextField phoneNum;
    private JFrame jFrame;

    public void init() {
        jFrame = new JFrame("Register Account");
        jFrame.setContentPane(registerPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
    }

    public RegisterForm() {
        // adds employee if eid is valid
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBHandler db = new DBHandler();
                try {
                    try {
                        Integer.parseInt(age.getText());
                        Long.parseLong(phoneNum.getText());
                        switch (type.getSelectedIndex()) {
                            case 0: //dentist
                                db.registerEmployee(
                                        username.getText(),
                                        new String(password.getPassword()),
                                        "d",
                                        fname.getText(),
                                        lname.getText(),
                                        Integer.parseInt(age.getText()),
                                        sex.getSelectedItem().toString(),
                                        Long.parseLong(phoneNum.getText()));
                                break;
                            case 1: //hygienist
                                db.registerEmployee(
                                        username.getText(),
                                        new String(password.getPassword()),
                                        "h",
                                        fname.getText(),
                                        lname.getText(),
                                        Integer.parseInt(age.getText()),
                                        sex.getSelectedItem().toString(),
                                        Long.parseLong(phoneNum.getText()));
                                break;
                            case 2: //receptionist
                                db.registerEmployee(
                                        username.getText(),
                                        new String(password.getPassword()),
                                        "r",
                                        fname.getText(),
                                        lname.getText(),
                                        Integer.parseInt(age.getText()),
                                        sex.getSelectedItem().toString(),
                                        Long.parseLong(phoneNum.getText()));
                                break;
                        }
                        JOptionPane.showMessageDialog(null, "Thank you for registering. Please log in with those credentials.");
                        jFrame.dispose();
                    }
                    catch (NumberFormatException e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Age or phone number must be an integer.");
                    }
                }
                catch (SQLException e2) {
                    JOptionPane.showMessageDialog(null, "An account with this username already exists. Please choose again.");
                }
            }
        });
        //cancel button returns to previous screen
        Cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
            }
        });
    }
}
