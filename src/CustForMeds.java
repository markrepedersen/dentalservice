import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Created by mark on 2016-11-20.
 */
public class CustForMeds extends JFrame {
    private JTable custTable;
    private JPanel menuPane;

    public CustForMeds(List<Customer> data) {
        super("Customers prescribed");
        setContentPane(menuPane);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        populateFrame(data);
    }


    public void populateFrame(List<Customer> list) {
        DefaultTableModel dtm = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] colNames = {"Customer ID", "First Name", "Last Name", "Phone", "Email", "Address"};
        //Add Column Names
        for (int i = 0; i < colNames.length; i++) {
            dtm.addColumn(colNames[i]);
        }

        //Add Row Data
        Object[] custRowData = new Object[100];
        for (int i = 0; i < list.size(); i++) {
            custRowData[0] = list.get(i).getCID();
            custRowData[1] = list.get(i).getFname();
            custRowData[2] = list.get(i).getLname();
            custRowData[3] = list.get(i).getPhoneNum();
            custRowData[4] = list.get(i).getEmail();
            custRowData[5] = list.get(i).getAddress();
            dtm.addRow(custRowData);
        }
        custTable.setModel(dtm);
    }
}
