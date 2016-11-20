import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Created by mark on 2016-11-19.
 */
public class CustAptmts extends JFrame {
    private JTable aptmtTable;
    private JPanel menuPane;

    public CustAptmts(List<Appointment> data, String name) {
        super(name + "'s Appointments");
        setContentPane(menuPane);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        populateFrame(data);
    }


    public void populateFrame(List<Appointment> data) {
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
        Object[] rowData = new Object[4];
        for (int i = 0; i < data.size(); i++) {
            rowData[0] = data.get(i).getNum();
            rowData[1] = data.get(i).getType();
            rowData[2] = data.get(i).getFromTime();
            rowData[3] = data.get(i).getToTime();
            dtm.addRow(rowData);
        }
        aptmtTable.setModel(dtm);
    }
}
