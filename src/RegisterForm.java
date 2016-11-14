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
                System.out.println(type.getSelectedIndex());
                try {
                    try {
                        Integer.parseInt(age.getText());
                        Long.parseLong(phoneNum.getText());
                        switch (type.getSelectedIndex()) {
                            case 0: //dentist
                                db.registerEmployee(
                                        username.getText(),
                                        password.getSelectedText(),
                                        "d",
                                        fname.getText(),
                                        lname.getText(),
                                        Integer.parseInt(age.getText()),
                                        sex.getSelectedItem().toString(),
                                        Integer.parseInt(phoneNum.getText()));
                                break;
                            case 1: //hygienist
                                db.registerEmployee(
                                        username.getText(),
                                        password.getSelectedText(),
                                        "h",
                                        fname.getText(),
                                        lname.getText(),
                                        Integer.parseInt(age.getText()),
                                        sex.getSelectedItem().toString(),
                                        Integer.parseInt(phoneNum.getText()));
                                break;
                            case 2: //receptionist
                                db.registerEmployee(
                                        username.getText(),
                                        password.getSelectedText(),
                                        "r",
                                        fname.getText(),
                                        lname.getText(),
                                        Integer.parseInt(age.getText()),
                                        sex.getSelectedItem().toString(),
                                        Integer.parseInt(phoneNum.getText()));
                                break;
                        }
                        JOptionPane.showMessageDialog(null, "Thank you for registering. You may now log in.");
                        jFrame.dispose();
                    }
                    catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(null, "Age or phone number must be an integer.");
                    }
                }
                catch (SQLException e2) {
                    e2.printStackTrace();
                    //JOptionPane.showMessageDialog(null, "An account with this username already exists. Please choose again.");
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
