package filetable;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class FileTable extends JTable {
    private FileTableModel fileTableModel;

    public FileTable(boolean hasAction) {
        super();
        fileTableModel = new FileTableModel(hasAction);
        this.setModel(fileTableModel);

        getColumn("File name").setPreferredWidth(300);
        getColumn("Speed").setPreferredWidth(50);
        getColumn("Status").setPreferredWidth(50);
        setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        setRowHeight(25);
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        if (getColumnName(column).equalsIgnoreCase("Action")) {
            return new DownloadButtonCellRenderer();
        }
        return super.getCellRenderer(row, column);
    }
}
