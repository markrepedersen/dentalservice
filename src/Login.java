import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.*;

/**
 * Created by Theodore on 11/11/16.
 */

public class Login {
    private JPanel loginView;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private ImageIcon logoImage;
    private JLabel logoLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;

    private int type; // 1 - Doctor, 2 - Hygienist, 3 - Receptionist

    DBHandler db;

    public Login() {
        loginButton.addActionListener(new LoginButtonClicked(usernameField.getText(), passwordField.getPassword()));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new Login().loginView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private class LoginButtonClicked implements ActionListener {
        private String username;
        private String password;

        public LoginButtonClicked(String username, char[] password) {
            this.username = username;
            this.password = String.valueOf(password);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            db = new DBHandler();

            try {
                type = db.queryLoginInfo(username, password);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            if (type == 1) {
                // Set Scene to ...
            } else if (type == 2) {
                // Set Scene to ...
            } else if (type == 3) {
                // Set Scene to ...
            } else {
                // Username & Password Incorrect
            }
        }
    }
}