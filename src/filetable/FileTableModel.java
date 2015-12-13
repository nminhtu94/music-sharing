package filetable;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.util.ArrayList;

public class FileTableModel extends AbstractTableModel {
    private final String[] columnNames = new String[] {"File name", "Speed", "Status", "Select"};
    private ArrayList<File> data;

    public FileTableModel(ArrayList<File> data) {
        this.data = data;
    }

    public FileTableModel() {
        this.data = new ArrayList<>();
    }

    public ArrayList<File> getData() {
        return this.data;
    }

    public void setData(ArrayList<File> data) {
        this.data = data;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        File f = data.get(rowIndex);
        // TODO: Modify data for this file:
        // Add download speed, status and selection.
        switch (columnIndex) {
            case 0: {
                return f.getName();
            }

            case 1: {
                return "100 Kb/s";
            }

            case 2: {
                return "Uploading";
            }

            case 3: {
                return true;
            }
        }
        return null;
    }

    public Class getColumnClass(int col) {
        return getValueAt(0, col).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        if (col == 3) {
            return true;
        } else {
            return false;
        }
    }

    public void setValueAt(Object value, int row, int col) {
        fireTableCellUpdated(row, col);
    }
}
