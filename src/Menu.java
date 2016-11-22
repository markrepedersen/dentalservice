import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by liamadams on 2016-11-08.
 */
public class Menu extends JFrame {
    private JButton logoutButton;
    private JButton createNewAppointmentButton;
    private JButton addCustomerButton;
    private JPanel menuPane;
    private JTabbedPane tabbedPane1;
    private JTable AppointmentsTable;
    private JTextField appointmentsSearchField;
    private JTable custTable;
    private JScrollPane TableScroll;
    private JScrollPane AppointmentsScrollPane;
    private JTextField newAppCidField;
    private JTextField custSearchBoxField;
    private JButton searchButton;
    private JTextField newCustFName;
    private JTextField newCustLName;
    private JButton createCustomerButton;
    private JTextField deleteCustField;
    private JButton deleteButton;
    private JButton appointmentSearchButton;
    private JRadioButton cidRadioButton;
    private JRadioButton lastNameRadioButton;
    private JComboBox insightsComboBox;
    private JTable insightsTable;
    private JScrollPane insightsTableScrollPane;
    private JButton showDataButton;
    private JComboBox newAppTypeComboBox;
    private JComboBox newAppFromBox;
    private JComboBox newAppToBox;
    private JTextField deleteAppField;
    private JButton deleteAppButton;
    private JTextField newCustEmailField;
    private JTextField newCustPhoneField;
    private JTextField newCustAddressField;
    private JTable empTable;
    private JTextField empSearchBoxField;
    private JButton empSearchButton;
    private JRadioButton byEmpLNameRadioButton;
    private JRadioButton byEmpIDRadioButton;
    private JScrollPane empTableScrollPane;
    private JComboBox custBirthYearCBox;
    private JComboBox custBirthMonthCBox;
    private JComboBox custBirthDayCBox;
    private JTextField empIDField;
    private JButton empDeleteButton;
    private JTextField empFNameField;
    private JTextField empLNameField;
    private JTextField empAgeField;
    private JTextField empPhoneField;
    private JTextField empSalField;
    private JButton addEmployeeButton;
    private JComboBox empTypeCombo;
    private JLabel empFNameLabel;
    private JLabel EmpLNameLabel;
    private JLabel empAgeLabel;
    private JLabel empSexLabel;
    private JLabel empPhoneLabel;
    private JLabel empSalLabel;
    private JLabel empTypeLabel;
    private JComboBox empSexCombo;
    private JTable billsTable;
    private JScrollPane billsTableScrollPane;
    private JTextField custIDBillingField;
    private JButton retrieveBillDataButton;
    private JComboBox billingCombo;
    private JLabel custIDBillLabel;
    private JTextField medCustIDField;
    private JTable medsTable;
    private JScrollPane medsScrollPane;
    private JButton findMedicinesButton;
    private JTextField empUpdateField;
    private JButton updateButton;
    private JComboBox empUpdateCombo;
    private JButton empUpdateButton;
    private JRadioButton byCustIDRadioButton;
    private JRadioButton byCustLNameRadioButton;
    private JRadioButton bySearchTermRadioButton;
    private JComboBox numPaymentsComboBox;
    private JButton pieChartButton;
    private JLabel logo;
    private JComboBox appSearchCriteria;
    private JComboBox custSearchCriteria;
    private DBHandler dbh;

    // row data arrays
    private Object[] custRowData;
    private Object[] medRowData;
    private Object[] billsRowData;
    private Object[] empRowData;
    private Object[] apptRowData;
    private Object[] insightsRowData;


    public Menu() {
        super("Menu Dashboard");
        dbh = new DBHandler();

        // -----------------------------------------------------------------------------------------------------//
        // Adds row selection functionality
        // Upon double clicking a row in a table, opens up a window with specific functionality depending
        // on respective table
        // ------>
        // added some stubs for all the tables - only have functionality for customers and appointments as of now
        // since no idea what the functionality for the others would be

        ImageIcon image = new ImageIcon("images/logo.png");
        logo = new JLabel(image);

        billsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        medsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        custTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        empTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        AppointmentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        insightsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
/*
        billsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    StringBuffer sb = new StringBuffer();
                    String lineSeparator = System.getProperty("line.separator");
                    sb.append(tabledata[row][0] + lineSeparator);
                    sb.append(tabledata[row][1] + lineSeparator);
                    sb.append(tabledata[row][2] + lineSeparator);
                    TextFrame textFrame = new TextFrame(sb.toString());
                    textFrame.setVisible(true);
                }
            }
        });
*/

        medsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    if (e.getClickCount() == 2) {
                        JTable target = (JTable) e.getSource();
                        int code = (int) target.getValueAt(target.getSelectedRow(), 0);
                        List<Customer> data = dbh.getCustomersWithMedicine(code);
                        if (data.size() == 0) {
                            JOptionPane.showMessageDialog(null, "No prescribed customers.");
                            return;
                        }
                        CustForMeds frame = new CustForMeds(data);
                        frame.setVisible(true);
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });



        custTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    if (e.getClickCount() == 2) {
                        JTable target = (JTable) e.getSource();
                        int cid = (int) target.getValueAt(target.getSelectedRow(), 0);
                        String name = (String) target.getValueAt(target.getSelectedRow(), 2) + " " + target.getValueAt(target.getSelectedRow(), 1);
                        List<Appointment> data = dbh.getUpcomingCustomerAppointmentsByCID(cid);
                        if (data.size() == 0) {
                            JOptionPane.showMessageDialog(null, "There are no appointments for this customer currently.");
                            return;
                        }
                        CustAptmts frame = new CustAptmts(data, name);
                        frame.setVisible(true);
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });


        /*

        empTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    StringBuffer sb = new StringBuffer();
                    String lineSeparator = System.getProperty("line.separator");
                    sb.append(tabledata[row][0] + lineSeparator);
                    sb.append(tabledata[row][1] + lineSeparator);
                    sb.append(tabledata[row][2] + lineSeparator);
                    TextFrame textFrame = new TextFrame(sb.toString());
                    textFrame.setVisible(true);
                }
            }
        });
*/
        AppointmentsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    if (e.getClickCount() == 2) {
                        JTable target = (JTable) e.getSource();
                        int num = (int) target.getValueAt(target.getSelectedRow(), 0);
                        Customer c = dbh.getCustomerByAppointmentNum(num);
                        if (c == null) {
                            // why did i even put this here. this isn't even supposed to be possible.
                            JOptionPane.showMessageDialog(null, "No customers for this appointment");
                        } else {
                            CustForAptmt cust = new CustForAptmt(
                                    c.getCID(),
                                    c.getFname(),
                                    c.getLname(),
                                    c.getPhoneNum(),
                                    c.getEmail(),
                                    c.getAddress());
                        }

                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });

        /*

        insightsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    StringBuffer sb = new StringBuffer();
                    String lineSeparator = System.getProperty("line.separator");
                    sb.append(tabledata[row][0] + lineSeparator);
                    sb.append(tabledata[row][1] + lineSeparator);
                    sb.append(tabledata[row][2] + lineSeparator);
                    TextFrame textFrame = new TextFrame(sb.toString());
                    textFrame.setVisible(true);
                }
            }
        });
        */

        // -----------------------------------------------------------------------------------------------------//


        //CUSTOMER SEARCH
        searchButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {

               String searchText = custSearchBoxField.getText();
               int criteria = custSearchCriteria.getSelectedIndex();
               List<Customer> data = new ArrayList<Customer>();

               try {

                   switch (criteria) {

                       //By Last Name
                       case 0:
                           if (searchText.equals("")) {
                               JOptionPane.showMessageDialog(null, "Please enter text in the search box.");
                           } else {
                               data = dbh.customerSearchByLastName(searchText);
                               populateCustomerTable(data);
                           }
                           break;
                       //By CID
                       case 1:
                           int cid = Integer.parseInt(searchText);
                           data = dbh.customerSearchByCID(cid);
                           populateCustomerTable(data);
                           break;
                       //Show All
                       case 2:
                           data = dbh.customerViewDefaultTable();
                           populateCustomerTable(data);
                           break;

                   }
                   if (data != null && data.isEmpty())
                       JOptionPane.showMessageDialog(null, "No records exist for that search.");

               } catch (NumberFormatException e1) {
                   JOptionPane.showMessageDialog(null, "Customer ID has to be a number.");

               } catch (SQLException e2) {
                   e2.printStackTrace();
               }
           }
        });

        //APPOINTMENT SEARCH
        appointmentSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String searchText = appointmentsSearchField.getText();
                int criteria = appSearchCriteria.getSelectedIndex();
                List<Appointment> data = new ArrayList<Appointment>();

                try {
                    switch (criteria) {
                        //By Last Name
                        case 0:
                            if (searchText.equals("")) {
                                JOptionPane.showMessageDialog(null, "Please enter text in the search box.");
                            } else {
                                data = dbh.searchCustomerAppointmentByLName(searchText);
                                populateAppointmentData(data);
                            }
                            break;
                        //By CID
                        case 1:
                            int cid = Integer.parseInt(searchText);
                            data = dbh.searchCustomerAppointmentByCid(cid);
                            populateAppointmentData(data);
                            break;
                        //Show All
                        case 2:
                            data = dbh.getUpcomingCustomerAppointments();
                            populateAppointmentData(data);
                            break;

                    }
                    if (data != null && data.isEmpty())
                        JOptionPane.showMessageDialog(null, "No records exist for that search.");

                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Customer ID has to be a number.");

                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        });

        pieChartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<CountColumn> data = dbh.getUpcomingCustomerAppointmentTypesColumnCount();

                    PieChart frame = new PieChart("Appointment Types", data);
                    frame.pack();
                    RefineryUtilities.centerFrameOnScreen(frame);
                    frame.setVisible(true);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //CREATES NEW APPOINTMENT
        createNewAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Needs editing

                String type;
                int cid = 0;
                Timestamp from = new Timestamp(2017);
                Timestamp t = new Timestamp(12000);
                //     int year, int month, int date, int hour, int minute, int second, int nano
                Timestamp to = new Timestamp(2018, 12, 10, 1, 2, 3, 4);
                int rid = 134;
                int appID = 0;


                try {
                    cid = Integer.parseInt(newAppCidField.getText());
                } catch (NumberFormatException ex) {
                    System.out.println("Not a number");
                }

                type = newAppTypeComboBox.getSelectedItem().toString();

                try {
                    if (dbh.isValidCustomerID(cid)) {

                        dbh.addAppointment(appID, type, from, to, rid, cid);
                        System.out.println("Valid ID entered!");
                        populateAppointmentData(dbh.getUpcomingCustomerAppointments());

                    } else {
                        JOptionPane.showMessageDialog(null, "Customer ID entered is not valid");
                    }

                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        });

        //DELETE APPOINTMENT BUTTON
        deleteAppButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int appNum = Integer.parseInt(deleteAppField.getText());

                try {
                    if (dbh.isValidAppNum(appNum)) {

                        dbh.removeAppointment(appNum);
                        populateAppointmentData(dbh.getUpcomingCustomerAppointments());
                        JOptionPane.showMessageDialog(null, "Appointment successfully deleted!");

                    } else {
                        JOptionPane.showMessageDialog(null, "There is no appointment with that number!");
                    }

                } catch (SQLException e2) {
                    e2.printStackTrace();
                }


            }
        });

        //CREATE NEW CUSTOMER
        createCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int newCid = 0;
                long phone = 0;
                int birthYear = Integer.parseInt(custBirthYearCBox.getSelectedItem().toString());
                int birthMonth = Integer.parseInt(custBirthMonthCBox.getSelectedItem().toString());
                int birthDay = Integer.parseInt(custBirthDayCBox.getSelectedItem().toString());
                String fname = newCustFName.getText();
                String lname = newCustLName.getText();
                String address = newCustAddressField.getText();

                try {
                    newCid = dbh.getHighestCustomerID() + 1;

                } catch (Exception e3) {
                    e3.printStackTrace();
                }

                try {
                    phone = Long.parseLong(newCustPhoneField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Phone number needs to be a number!");
                }

                String email = newCustEmailField.getText();

                if (!isValidEmailAddress(email)) {
                    JOptionPane.showMessageDialog(null, "Invalid email address entered.");
                } else {
                    try {
                        dbh.addCustomer(newCid, fname, lname, phone, new Date(birthYear, birthMonth, birthDay), email, address);
                        populateCustomerTable(dbh.customerViewDefaultTable());
                        JOptionPane.showMessageDialog(null, "New Customer Successfully Added!");
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        //DELETE A CUSTOMER BUTTON
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int custID = Integer.parseInt(deleteCustField.getText());

                try {
                    if (dbh.isValidCustomerID(custID)) {

                        dbh.removeCustomer(custID);
                        populateCustomerTable(dbh.customerViewDefaultTable());
                        JOptionPane.showMessageDialog(null, "Customer successfully deleted!");

                    } else {
                        JOptionPane.showMessageDialog(null, "There is no customer with that ID!");
                    }

                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
            }
        });


        //SEARCH EMPLOYEES
        empSearchButton.addActionListener(new ActionListener() {
                                              @Override
                                              public void actionPerformed(ActionEvent e) {
                                                  String searchText = empSearchBoxField.getText();

                                                  if (searchText.equals("") ||
                                                          (searchText.equals("") && byEmpIDRadioButton.isSelected()) ||
                                                          (searchText.equals("") && byEmpLNameRadioButton.isSelected())) {
                                                      List<Employee> data = null;
                                                      try {
                                                          data = dbh.employeeViewDefaultTable();
                                                      } catch (SQLException e1) {
                                                          e1.printStackTrace();
                                                      }

                                                      populateEmployeeTable(data);
                                                  } else if (byEmpLNameRadioButton.isSelected() && byEmpIDRadioButton.isSelected()) {
                                                      JOptionPane.showMessageDialog(null, "Please choose a single criterion for searching.");
                                                  } else if (byEmpLNameRadioButton.isSelected()) {

                                                      List<Employee> data = null;
                                                      try {
                                                          data = dbh.empSearchByLastName(searchText);
                                                      } catch (SQLException e1) {
                                                          e1.printStackTrace();
                                                      }

                                                      populateEmployeeTable(data);
                                                      if (data.isEmpty())
                                                          JOptionPane.showMessageDialog(null, "No records exist for that search.");


                                                  } else if (byEmpIDRadioButton.isSelected()) {

                                                      List<Employee> data = null;
                                                      try {

                                                          try {
                                                              int eid = Integer.parseInt(searchText);
                                                              data = dbh.empSearchByEID(eid);
                                                              populateEmployeeTable(data);
                                                              if (data.isEmpty())
                                                                  JOptionPane.showMessageDialog(null, "No records exist for that search.");
                                                          } catch (NumberFormatException ex) {
                                                              JOptionPane.showMessageDialog(null, "Employee ID needs to be a number not string!");
                                                          }

                                                      } catch (SQLException e1) {
                                                          e1.printStackTrace();
                                                      }

                                                  } else {

                                                      List<Employee> data = null;
                                                      try {
                                                          data = dbh.employeeViewDefaultTable();
                                                      } catch (SQLException e1) {
                                                          e1.printStackTrace();
                                                      }

                                                      populateEmployeeTable(data);

                                                  }


                                              }
                                          }
        );
        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int newEid = 0;
                try {
                    newEid = dbh.getHighestCustomerID() + 1;

                } catch (Exception e3) {
                    e3.printStackTrace();
                }

                boolean flag = true;


                String empFName = empFNameField.getText();
                String empLName = empLNameField.getText();
                String empSex = empSexCombo.getSelectedItem().toString();
                int empAge = 0;
                long empPhone = 0;
                int empSal = 0;

                try {
                    empPhone = Long.parseLong(empPhoneField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Phone number needs to be a number!");
                }
                try {
                    empAge = Integer.parseInt(empAgeField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Age needs to be a number!");
                }
                try {
                    empSal = Integer.parseInt(empSalField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Salary needs to be a number!");
                }

                try {
                    dbh.addEmployee(newEid, empFName, empLName, empSal, empAge, empSex, empPhone, 0);
                    populateEmployeeTable(dbh.employeeViewDefaultTable());
                    JOptionPane.showMessageDialog(null, "New Employee Successfully Added!");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }


            }
        });

        //BILLING DATA RETRIEVAL BUTTON
        retrieveBillDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = custIDBillingField.getText();
                int custID = 0;
                try {
                    if (!searchText.equals("")) {
                        custID = Integer.parseInt(searchText);
                    } else {
                        populateBillsTable(dbh.billsDefaultView());
                        return;
                    }
                    int comboSelection = billingCombo.getSelectedIndex();
                    if (comboSelection == 0) {
                        populateBillsTable(dbh.getCustomerUnpaidBills(custID));
                    } else if (comboSelection == 1) {
                        //Unpaid Bills
                        populateBillsTable(dbh.getCustomerUnpaidBills(custID));
                    } else {
                        //Past Payments
                        populateBillsTableWithPastData(dbh.getCustomerPastPayments(custID));
                    }
                } catch (NumberFormatException nm) {
                    JOptionPane.showMessageDialog(null, "Customer ID must be a number!");
                } catch (SQLException exc) {
                    exc.printStackTrace();
                }


            }
        });
        showDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selected = insightsComboBox.getSelectedIndex();
                //JOptionPane.showMessageDialog(null, "Selected: " + selected);

                try {
                    switch (selected) {

                        case 0:
                            populateInsightsTableWithDentist(dbh.getAllDentistsAttended());
                            break;
                        case 1:
                            populateInsightsTableWithEmployee(dbh.getHighestEarningEmployee());
                            break;
                        case 2:
                            populateInsightsTableWithEmployee(dbh.getLowestEarningEmployee());
                            break;
                        case 3:
                            populateInsightsTableWithMedicine(dbh.getCheapestMedicine());
                            break;
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }


            }
        });

        //Update Emp Button
        empUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String updateText = empUpdateField.getText();
                int empID = 0;
                int updateOption = empUpdateCombo.getSelectedIndex();

                //Determine employee ID format is valid
                try {
                    empID = Integer.parseInt(empIDField.getText());
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Employee ID needs to be a number!");
                    e1.printStackTrace();
                }


                switch (updateOption) {
                    //Case 0 = Fname
                    case 0:
                        break;
                    //Case 1 = Lname
                    case 1:
                        break;
                    //Case 2 = Salary
                    case 2:
                        break;
                    //Case 3 = Sex
                    case 3:
                        break;
                    //Case 4 = Age
                    case 4:
                        break;
                }


            }
        });

        //DELETE AN EMPLOYEE
        empDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                int empID = 0;
                try {
                    empID = Integer.parseInt(empIDField.getText());
                    try {
                        dbh.removeEmployee(empID);
                        JOptionPane.showMessageDialog(null, "Employee Successfully Deleted!");
                        populateEmployeeTable(dbh.employeeViewDefaultTable());
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Employee ID needs to be a number!");
                    e1.printStackTrace();
                }

            }
        });
        findMedicinesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = medCustIDField.getText();
                if (searchText.equals("")) {
                    List<Medicine> data = null;
                    try {
                        data = dbh.medDefaultView();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    populateMedicineTable(data);
                } else if (byCustIDRadioButton.isSelected() && byCustLNameRadioButton.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Please choose a single criterion for searching.");
                } else if (byCustLNameRadioButton.isSelected()) {

                    List<Medicine> data = null;
                    try {
                        data = dbh.getCustomerMedicines(searchText);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    populateMedicineTable(data);

                    if (data.isEmpty())
                        JOptionPane.showMessageDialog(null, "No records exist for that search.");


                } else if (byCustIDRadioButton.isSelected()) {

                    List<Medicine> data = null;
                    int cid = 0;
                    try {
                        try {
                            cid = Integer.parseInt(searchText);
                            data = dbh.getCustomerMedicines(cid);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "CID needs to be a number not string!");
                        }

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    populateMedicineTable(data);

                    if (data.isEmpty())
                        JOptionPane.showMessageDialog(null, "No records exist for that search.");

                } else if (bySearchTermRadioButton.isSelected()) {

                    List<Medicine> data = null;
                    int cid = 0;
                    try {
                        data = dbh.medSearchByDescriptionTerm(searchText);


                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    populateMedicineTable(data);

                    if (data.isEmpty())
                        JOptionPane.showMessageDialog(null, "No records exist for that search.");

                } else {

                    List<Medicine> data = null;
                    try {
                        data = dbh.medDefaultView();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    populateMedicineTable(data);
                }
            }
        });

        // logs out of user's view and goes back to login screen
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login().init();
                dispose();
            }
        });
        numPaymentsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    //POPULATES INSIGHTS TABLE FROM A LIST OF EMPLOYEES Data
    public void populateInsightsTableWithEmployee(List<Employee> data) {

        DefaultTableModel dtm = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] colNames = {"Employee ID", "First Name", "Last Name", "Age", "Sex", "Salary"};

        //Add Column Names
        for (int i = 0; i < colNames.length; i++) {
            dtm.addColumn(colNames[i]);
        }

        //Add Row Data
        insightsRowData = new Object[100];
        for (int i = 0; i < data.size(); i++) {

            insightsRowData[0] = data.get(i).getEid();
            insightsRowData[1] = data.get(i).getFname();
            insightsRowData[2] = data.get(i).getLname();
            insightsRowData[3] = data.get(i).getAge();
            insightsRowData[4] = data.get(i).getSex();
            insightsRowData[5] = data.get(i).getSalary();
            dtm.addRow(insightsRowData);
        }
        insightsTable.setModel(dtm);
    }

    //POPULATES INSIGHTS TABLE FROM A LIST OF MEDICINE DATA
    public void populateInsightsTableWithMedicine(List<Medicine> data) {

        DefaultTableModel dtm = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] colNames = {"Medicine Code", "Description", "Medicine Cost"};

        //Add Column Names
        for (int i = 0; i < colNames.length; i++) {
            dtm.addColumn(colNames[i]);
        }

        //Add Row Data
        insightsRowData = new Object[100];
        for (int i = 0; i < data.size(); i++) {

            insightsRowData[0] = data.get(i).getCode();
            insightsRowData[1] = data.get(i).getDescription();
            insightsRowData[2] = data.get(i).getCost();
            dtm.addRow(insightsRowData);
        }
        insightsTable.setModel(dtm);
    }

    //POPULATES INSIGHTS TABLE FROM A LIST OF DENTISTS Data
    public void populateInsightsTableWithDentist(List<Dentist> data) {

        DefaultTableModel dtm = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] colNames = {"Dentist ID", "First Name", "Last Name", "Age", "Sex"};

        //Add Column Names
        for (int i = 0; i < colNames.length; i++) {
            dtm.addColumn(colNames[i]);
        }

        //Add Row Data
        insightsRowData = new Object[100];
        for (int i = 0; i < data.size(); i++) {

            insightsRowData[0] = data.get(i).getDid();
            insightsRowData[1] = data.get(i).getFname();
            insightsRowData[2] = data.get(i).getLname();
            insightsRowData[3] = data.get(i).getAge();
            insightsRowData[4] = data.get(i).getSex();
            dtm.addRow(insightsRowData);
        }
        insightsTable.setModel(dtm);
    }


    //POPULATES BILLS TABLE FROM A LIST OF BILLS Data
    public void populateBillsTable(List<Bill> data) {

        DefaultTableModel dtm = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] colNames = {"Customer ID", "First Name", "Last Name", "Type", "Due", "Payment", "Balance"};

        //Add Column Names
        for (int i = 0; i < colNames.length; i++) {
            dtm.addColumn(colNames[i]);
        }

        //Add Row Data
        billsRowData = new Object[100];
        for (int i = 0; i < data.size(); i++) {
            billsRowData[0] = data.get(i).getCid();
            billsRowData[1] = data.get(i).getCname();
            billsRowData[2] = data.get(i).getSurname();
            billsRowData[3] = data.get(i).getType();
            billsRowData[4] = data.get(i).getDueDate();
            billsRowData[5] = data.get(i).getAmountPaid();
            billsRowData[6] = data.get(i).getBalance();
            dtm.addRow(billsRowData);
        }
        billsTable.setModel(dtm);
    }

    //POPULATES BILLS TABLE FROM A LIST OF BILLS Data
    public void populateBillsTableWithPastData(List<Bill> data) {

        DefaultTableModel dtm = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] colNames = {"Customer ID", "First Name", "Last Name", "Type", "Due", "Amount"};

        //Add Column Names
        for (int i = 0; i < colNames.length; i++) {
            dtm.addColumn(colNames[i]);
        }

        //Add Row Data
        billsRowData = new Object[100];
        for (int i = 0; i < data.size(); i++) {
            billsRowData[0] = data.get(i).getCid();
            billsRowData[1] = data.get(i).getCname();
            billsRowData[2] = data.get(i).getSurname();
            billsRowData[3] = data.get(i).getType();
            billsRowData[4] = data.get(i).getDueDate();
            billsRowData[5] = data.get(i).getAmountOwes();
            dtm.addRow(billsRowData);
        }
        billsTable.setModel(dtm);
    }


    //POPULATES EMPLOYEE TABLE FROM A LIST OF CUSTOMERS
    public void populateEmployeeTable(List<Employee> list) {

        DefaultTableModel dtm = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] colNames = {"Employee ID", "Last Name", "First Name", "Age", "Sex", "Phone", "Salary"};

        //Add Column Names
        for (int i = 0; i < colNames.length; i++) {
            dtm.addColumn(colNames[i]);
        }

        //Add Row Data
        empRowData = new Object[100];
        for (int i = 0; i < list.size(); i++) {
            empRowData[0] = list.get(i).getEid();
            empRowData[1] = list.get(i).getLname();
            empRowData[2] = list.get(i).getFname();
            empRowData[3] = list.get(i).getAge();
            empRowData[4] = list.get(i).getSex();
            empRowData[5] = list.get(i).getPhoneNum();
            empRowData[6] = list.get(i).getSalary();
            dtm.addRow(empRowData);
        }
        empTable.setModel(dtm);
    }

    //POPULATES CUSTOMER TABLE FROM A LIST OF CUSTOMERS
    public void populateCustomerTable(List<Customer> list) {

        DefaultTableModel dtm = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] colNames = {"Customer ID", "Last Name", "First Name", "Email", "Phone", "Birthday", "Address", "Dentist"};

        //Add Column Names
        for (int i = 0; i < colNames.length; i++) {
            dtm.addColumn(colNames[i]);
        }

        //Add Row Data
        custRowData = new Object[100];
        for (int i = 0; i < list.size(); i++) {
            custRowData[0] = list.get(i).getCID();
            custRowData[1] = list.get(i).getLname();
            custRowData[2] = list.get(i).getFname();
            custRowData[3] = list.get(i).getEmail();
            custRowData[4] = list.get(i).getPhoneNum();
            custRowData[5] = list.get(i).getBirthday();
            custRowData[6] = list.get(i).getAddress();
            custRowData[7] = list.get(i).getDentist();
            dtm.addRow(custRowData);
        }
        custTable.setModel(dtm);
    }

    //POPULATES APPOINTMENTS TABLE FROM A LIST OF APPOINTMENTS
    public void populateAppointmentData(List<Appointment> data) {

        DefaultTableModel dtm = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] colNames = {"Appointment Number", "Type", "From", "To"};

        //Add Column Names
        for (int i = 0; i < colNames.length; i++) {
            dtm.addColumn(colNames[i]);
        }

        //Add Row Data
        apptRowData = new Object[data.size()];
        for (int i = 0; i < data.size(); i++) {

            apptRowData[0] = data.get(i).getNum();
            apptRowData[1] = data.get(i).getType();
            apptRowData[2] = data.get(i).getFromTime();
            apptRowData[3] = data.get(i).getToTime();
            dtm.addRow(apptRowData);
        }
        AppointmentsTable.setModel(dtm);
    }

    //POPULATES APPOINTMENTS TABLE FROM A LIST OF APPOINTMENTS
    public void populateMedicineTable(List<Medicine> data) {

        DefaultTableModel dtm = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] colNames = {"code", "description", "cost"};

        //Add Column Names
        for (int i = 0; i < colNames.length; i++) {
            dtm.addColumn(colNames[i]);
        }

        //Add Row Data
        medRowData = new Object[3];
        for (int i = 0; i < data.size(); i++) {

            medRowData[0] = data.get(i).getCode();
            medRowData[1] = data.get(i).getDescription();
            medRowData[2] = data.get(i).getCost();
            dtm.addRow(medRowData);
        }
        medsTable.setModel(dtm);
    }

    //RETURNS TRUE IF EMAIL ADDRESS IS VALID - taken from Stack Overflow
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public void init() {

        List<Customer> customers = new ArrayList<>();
        List<Appointment> apps = new ArrayList<>();
        List<Employee> emps = new ArrayList<>();
        List<Medicine> meds = new ArrayList<>();
        List<Bill> bills = new ArrayList<>();


        try {
            customers = dbh.customerViewDefaultTable();
            apps = dbh.getUpcomingCustomerAppointments();
            emps = dbh.employeeViewDefaultTable();
            meds = dbh.medDefaultView();
            bills = dbh.billsDefaultView();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        populateAppointmentData(apps);
        populateCustomerTable(customers);
        populateEmployeeTable(emps);
        populateMedicineTable(meds);
        populateBillsTable(bills);

        setContentPane(menuPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

}

