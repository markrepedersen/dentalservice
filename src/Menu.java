import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;



/**
 * Created by liamadams on 2016-11-08.
 */
public class Menu extends JFrame{
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
    private JRadioButton custLNameRadioButton;
    private JRadioButton custIDRadioButton;
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
    private JButton showCustomersWith2Button;
    private JLabel custIDBillLabel;
    private JTextField medCustIDField;
    private JTable medsTable;
    private JScrollPane medsScrollPane;
    private JButton findMedicinesButton;
    private JTextField empUpdateField;
    private JButton updateButton;
    private JComboBox empUpdateCombo;
    private JButton empUpdateButton;
    private DBHandler dbh;


    public Menu() {
        super("Menu Dashboard");
        dbh = new DBHandler();

        //CUSTOMER SEARCH
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = custSearchBoxField.getText();

                if(custIDRadioButton.isSelected() && custLNameRadioButton.isSelected()){
                    JOptionPane.showMessageDialog(null, "Please choose a single criterion for searching.");
                }
                else if(custLNameRadioButton.isSelected()){

                    List<Customer> data = null;
                    try {
                        data = dbh.customerSearchByLastName(searchText);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    populateCustomerTable(data);

                    if(data.isEmpty())
                        JOptionPane.showMessageDialog(null, "No records exist for that search.");


                }
                else if(custIDRadioButton.isSelected()){

                    List<Customer> data = null;
                    int cid = 0;
                    try {
                        try{
                            cid = Integer.parseInt(searchText);
                            data = dbh.customerSearchByCID(cid);
                        }
                        catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "CID needs to be a number not string!");
                        }

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    populateCustomerTable(data);

                    if(data.isEmpty())
                        JOptionPane.showMessageDialog(null, "No records exist for that search.");

                }
                else{

                    List<Customer> data = null;
                    try {
                        data = dbh.customerViewDefaultTable();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    populateCustomerTable(data);
                }

            }
        });

        //APPOINTMENT SEARCH
        appointmentSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String searchText = appointmentsSearchField.getText();

                if(lastNameRadioButton.isSelected() && cidRadioButton.isSelected()){
                    JOptionPane.showMessageDialog(null, "Please choose a single criterion for searching.");
                }
                else if(lastNameRadioButton.isSelected()){

                    List<Appointment> data = null;
                    try {
                        data = dbh.searchCustomerAppointmentByLName(searchText);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    populateAppointmentData(data);
                    if(data.isEmpty())
                        JOptionPane.showMessageDialog(null, "No records exist for that search.");


                }
                else if(cidRadioButton.isSelected()){

                    List<Appointment> data = null;
                    try {

                        try{
                            int cid = Integer.parseInt(searchText);
                            data = dbh.searchCustomerAppointmentByCid(searchText);
                            populateAppointmentData(data);
                            if(data.isEmpty())
                                JOptionPane.showMessageDialog(null, "No records exist for that search.");
                        }
                        catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "CID needs to be a number not string!");
                        }

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                }
                else{

                    List<Appointment> data = null;
                    try {
                        data = dbh.getUpcomingCustomerAppointments();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    populateAppointmentData(data);

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
                }
                catch (NumberFormatException ex) {
                    System.out.println("Not a number");
                }

                type = newAppTypeComboBox.getSelectedItem().toString();

                try {
                    if(dbh.isValidCustomerID(cid)) {

                        dbh.addAppointment(appID, type, from, to, rid, cid);
                        System.out.println("Valid ID entered!");
                        populateAppointmentData(dbh.getUpcomingCustomerAppointments());

                     }
                     else{
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
                    if(dbh.isValidAppNum(appNum)) {

                        dbh.removeAppointment(appNum);
                        populateAppointmentData(dbh.getUpcomingCustomerAppointments());
                        JOptionPane.showMessageDialog(null, "Appointment successfully deleted!");

                    }
                    else{
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
                    newCid = dbh.getHighestCustomerID()+1;

                }
                catch (Exception e3) {
                    e3.printStackTrace();
                }

                try {
                    phone = Long.parseLong(newCustPhoneField.getText());
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Phone number needs to be a number!");
                }

                String email = newCustEmailField.getText();

                if (!isValidEmailAddress(email)){
                    JOptionPane.showMessageDialog(null, "Invalid email address entered.");
                }
                else {
                    try {
                        dbh.addCustomer(newCid, fname, lname, phone, new Date(birthYear, birthMonth, birthDay), email, address);
                        populateCustomerTable(dbh.customerViewDefaultTable());
                        JOptionPane.showMessageDialog(null, "New Customer Successfully Added!");
                    }
                    catch (SQLException e1){
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
                    if(dbh.isValidCustomerID(custID)) {

                        dbh.removeCustomer(custID);
                        populateCustomerTable(dbh.customerViewDefaultTable());
                        JOptionPane.showMessageDialog(null, "Customer successfully deleted!");

                    }
                    else{
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

                if(byEmpLNameRadioButton.isSelected() && byEmpIDRadioButton.isSelected()){
                    JOptionPane.showMessageDialog(null, "Please choose a single criterion for searching.");
                }
                else if(byEmpLNameRadioButton.isSelected()){

                    List<Employee> data = null;
                    try {
                        data = dbh.empSearchByLastName(searchText);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    populateEmployeeTable(data);
                    if(data.isEmpty())
                        JOptionPane.showMessageDialog(null, "No records exist for that search.");


                }
                else if(byEmpIDRadioButton.isSelected()){

                    List<Employee> data = null;
                    try {

                        try{
                            int eid = Integer.parseInt(searchText);
                            data = dbh.empSearchByEID(eid);
                            populateEmployeeTable(data);
                            if(data.isEmpty())
                                JOptionPane.showMessageDialog(null, "No records exist for that search.");
                        }
                        catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Employee ID needs to be a number not string!");
                        }

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                }
                else{

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
                   newEid = dbh.getHighestCustomerID()+1;

                }
                catch (Exception e3) {
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
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Phone number needs to be a number!");
                }
                try {
                    empAge = Integer.parseInt(empAgeField.getText());
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Age needs to be a number!");
                }
                try {
                    empSal = Integer.parseInt(empSalField.getText());
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Salary needs to be a number!");
                }

                try {
                    dbh.addEmployee(newEid, empFName, empLName, empSal, empAge, empSex, empPhone, 0);
                    populateEmployeeTable(dbh.employeeViewDefaultTable());
                    JOptionPane.showMessageDialog(null, "New Employee Successfully Added!");
                }
                catch (SQLException e1){
                    e1.printStackTrace();
                }


            }
        });

        //BILLING DATA RETRIEVAL BUTTON
        retrieveBillDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int custID = 0;
                try {
                    custID = Integer.parseInt(custIDBillingField.getText());
                }catch (NumberFormatException nm) {
                    JOptionPane.showMessageDialog(null, "Customer ID must be a number!");
                }

                int comboSelection = billingCombo.getSelectedIndex();

                try{
                    if(comboSelection==0){
                        populateBillsTable(dbh.getCustomerUnpaidBills(custID));

                    }
                else if(comboSelection == 1){
                        //Unpaid Bills
                        populateBillsTable(dbh.getCustomerUnpaidBills(custID));

                    }
                    else{
                        //Past Payments
                        populateBillsTableWithPastData(dbh.getCustomerPastPayments(custID));
                    }
                } catch (SQLException s) {
                    s.printStackTrace();
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
                }catch (SQLException e1){
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
                try{
                    empID = Integer.parseInt(empIDField.getText());
                }
                catch (NumberFormatException e1){
                    JOptionPane.showMessageDialog(null, "Employee ID needs to be a number!");
                    e1.printStackTrace();
                }


                switch(updateOption){
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
                try{
                    empID = Integer.parseInt(empIDField.getText());
                    try {
                        dbh.removeEmployee(empID);
                        JOptionPane.showMessageDialog(null, "Employee Successfully Deleted!");
                        populateEmployeeTable(dbh.employeeViewDefaultTable());
                    }
                    catch (SQLException e1){
                        e1.printStackTrace();
                    }
                }
                catch (NumberFormatException e1){
                    JOptionPane.showMessageDialog(null, "Employee ID needs to be a number!");
                    e1.printStackTrace();
                }

            }
        });
    }

    //POPULATES INSIGHTS TABLE FROM A LIST OF EMPLOYEES Data
    public void populateInsightsTableWithEmployee(List<Employee> data){

        DefaultTableModel dtm = new DefaultTableModel();

        String[] colNames = {"Employee ID", "First Name", "Last Name"};

        //Add Column Names
        for(int i=0; i<colNames.length; i++){
            dtm.addColumn(colNames[i]);
        }

        //Add Row Data
        Object[] rowData = new Object[6];
        for(int i=0; i < data.size(); i++){

            rowData[0] = data.get(i).getEid();
            rowData[1] = data.get(i).getFname();
            rowData[2] = data.get(i).getLname();
            dtm.addRow(rowData);
        }
        insightsTable.setModel(dtm);
    }

    //POPULATES INSIGHTS TABLE FROM A LIST OF MEDICINE DATA
    public void populateInsightsTableWithMedicine(List<Medicine> data){

        DefaultTableModel dtm = new DefaultTableModel();

        String[] colNames = {"Medicine Code", "Medicine Cost"};

        //Add Column Names
        for(int i=0; i<colNames.length; i++){
            dtm.addColumn(colNames[i]);
        }

        //Add Row Data
        Object[] rowData = new Object[6];
        for(int i=0; i < data.size(); i++){

            rowData[0] = data.get(i).getCode();
            rowData[1] = data.get(i).getCost();
            dtm.addRow(rowData);
        }
        insightsTable.setModel(dtm);
    }

    //POPULATES INSIGHTS TABLE FROM A LIST OF DENTISTS Data
    public void populateInsightsTableWithDentist(List<Dentist> data){

        DefaultTableModel dtm = new DefaultTableModel();

        String[] colNames = {"Dentist ID", "First Name", "Last Name"};

        //Add Column Names
        for(int i=0; i<colNames.length; i++){
            dtm.addColumn(colNames[i]);
        }

        //Add Row Data
        Object[] rowData = new Object[6];
        for(int i=0; i < data.size(); i++){

            rowData[0] = data.get(i).getDid();
            rowData[1] = data.get(i).getFname();
            rowData[2] = data.get(i).getLname();
            dtm.addRow(rowData);
        }
        insightsTable.setModel(dtm);
    }


    //POPULATES BILLS TABLE FROM A LIST OF BILLS Data
    public void populateBillsTable(List<Bill> data){

        DefaultTableModel dtm = new DefaultTableModel();

        String[] colNames = {"First Name", "Last Name", "Type", "Due", "Payment", "Balance"};

        //Add Column Names
        for(int i=0; i<colNames.length; i++){
            dtm.addColumn(colNames[i]);
        }

        //Add Row Data
        Object[] rowData = new Object[6];
        for(int i=0; i < data.size(); i++){

            rowData[0] = data.get(i).getCname();
            rowData[1] = data.get(i).getSurname();
            rowData[2] = data.get(i).getType();
            rowData[3] = data.get(i).getDueDate();
            rowData[4] = data.get(i).getAmountPaid();
            rowData[5] = data.get(i).getBalance();
            dtm.addRow(rowData);
        }
        billsTable.setModel(dtm);
    }

    //POPULATES BILLS TABLE FROM A LIST OF BILLS Data
    public void populateBillsTableWithPastData(List<Bill> data){

        DefaultTableModel dtm = new DefaultTableModel();

        String[] colNames = {"First Name", "Last Name", "Payment"};

        //Add Column Names
        for(int i=0; i<colNames.length; i++){
            dtm.addColumn(colNames[i]);
        }

        //Add Row Data
        Object[] rowData = new Object[6];
        for(int i=0; i < data.size(); i++){

            rowData[0] = data.get(i).getCname();
            rowData[1] = data.get(i).getSurname();
            rowData[4] = data.get(i).getAmountPaid();
            dtm.addRow(rowData);
        }
        billsTable.setModel(dtm);
    }



    //POPULATES CUSTOMER TABLE FROM A LIST OF CUSTOMERS
    public void populateEmployeeTable(List<Employee> list){

        DefaultTableModel dtm = new DefaultTableModel();

        String[] colNames = {"Employee ID", "Last Name", "First Name", "Age", "Sex", "Birthday", "Phone", "Salary"};

        //Add Column Names
        for(int i=0; i<colNames.length; i++){
            dtm.addColumn(colNames[i]);
        }

        //Add Row Data
        Object[] rowData = new Object[8];
        for(int i=0; i < list.size(); i++){
            rowData[0] = list.get(i).getEid();
            rowData[1] = list.get(i).getLname();
            rowData[2] = list.get(i).getFname();
            rowData[3] = list.get(i).getAge();
            rowData[4] = list.get(i).getSex();
            rowData[6] = list.get(i).getPhoneNum();
            rowData[7] = list.get(i).getSalary();
            dtm.addRow(rowData);
        }
        empTable.setModel(dtm);
    }

    //POPULATES CUSTOMER TABLE FROM A LIST OF CUSTOMERS
    public void populateCustomerTable(List<Customer> list){

       // List<Customer> data = new ArrayList<Customer>();

        DefaultTableModel dtm = new DefaultTableModel();

        String[] colNames = {"Customer ID", "Last Name", "First Name", "Email", "Phone", "Birthday", "Address", "Dentist"};

        //Add Column Names
        for(int i=0; i<colNames.length; i++){
            dtm.addColumn(colNames[i]);
        }

        //Add Row Data
        Object[] rowData = new Object[8];
        for(int i=0; i < list.size(); i++){
            rowData[0] = list.get(i).getCID();
            rowData[1] = list.get(i).getLname();
            rowData[2] = list.get(i).getFname();
            rowData[3] = list.get(i).getEmail();
            rowData[4] = list.get(i).getPhoneNum();
            rowData[5] = list.get(i).getBirthday();
            rowData[6] = list.get(i).getAddress();
            rowData[7] = list.get(i).getDentist();
            dtm.addRow(rowData);
        }
        custTable.setModel(dtm);
    }

    //POPULATES APPOINTMENTS TABLE FROM A LIST OF APPOINTMENTS
    public void populateAppointmentData(List<Appointment> data){

        DefaultTableModel dtm = new DefaultTableModel();

        String[] colNames = {"Name", "Customer ID", "Appointment Number", "Type", "From", "To"};

        //Add Column Names
        for(int i=0; i<colNames.length; i++){
            dtm.addColumn(colNames[i]);
        }

        //Add Row Data
        Object[] rowData = new Object[6];
        for(int i=0; i < data.size(); i++){

            rowData[0] = data.get(i).getCname() + " " + data.get(i).getCsurname();
            rowData[1] = data.get(i).getCid();
            rowData[2] = data.get(i).getNum();
            rowData[3] = data.get(i).getType();
            rowData[4] = data.get(i).getFromTime();
            rowData[5] = data.get(i).getToTime();
            dtm.addRow(rowData);
        }
         AppointmentsTable.setModel(dtm);
    }

    //RETURNS TRUE IF EMAIL ADDRESS IS VALID - taken from Stack Overflow
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public void init(){


        List<Customer> customers = new ArrayList<Customer>();
        List<Appointment> apps = new ArrayList<Appointment>();
        List<Employee> emps = new ArrayList<Employee>();

        try {
            customers = dbh.customerViewDefaultTable();
            apps = dbh.getUpcomingCustomerAppointments();
            emps = dbh.employeeViewDefaultTable();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        populateAppointmentData(apps);
        populateCustomerTable(customers);
        populateEmployeeTable(emps);

        setContentPane(menuPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }



}

