package filetable;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.util.ArrayList;

public class FileTableModel extends AbstractTableModel {
    private final String[] columnNamesDownload = new String[] {"File name", "Speed", "Status", "Action"};
    private final String[] columnNamesNormal = new String[] {"File name", "Speed", "Status"};
    private String[] columnNames;
    private ArrayList<File> data;

    public FileTableModel(ArrayList<File> data, boolean hasAction) {
        this.data = data;
        if (hasAction) {
            columnNames = columnNamesDownload;
        } else {
            columnNames = columnNamesNormal;
        }
    }

    public FileTableModel(boolean hasAction) {
        this.data = new ArrayList<>();
        if (hasAction) {
            columnNames = columnNamesDownload;
        } else {
            columnNames = columnNamesNormal;
        }
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
                return new JButton();
            }
        }
        return null;
    }

    public Class getColumnClass(int col) {
        return getValueAt(0, col).getClass();
    }
}
