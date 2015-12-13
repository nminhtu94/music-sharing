package filetable;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.io.File;

public class FileTable extends JTable {
    public interface FileTableListener {
        void onFileSelected(FileTable sender, File f);
    }

    private FileTableModel fileTableModel;

    private FileTableListener tableListener;
    public void setTableListener(FileTableListener tableListener) {
        this.tableListener = tableListener;
    }

    public FileTable() {
        super();
        fileTableModel = new FileTableModel();
        fileTableModel.addTableModelListener(new FileTableModelListener());
        this.setModel(fileTableModel);

        getColumn("File name").setPreferredWidth(300);
        getColumn("Speed").setPreferredWidth(50);
        getColumn("Status").setPreferredWidth(50);
        setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        setRowHeight(25);
    }

    private class FileTableModelListener implements TableModelListener {
        @Override
        public void tableChanged(TableModelEvent e) {
            int row = e.getFirstRow();
            FileTableModel model = (FileTableModel) e.getSource();

            File f = model.getData().get(row);

            if (tableListener != null) {
                tableListener.onFileSelected(FileTable.this, f);
            }
        }
    }
}
