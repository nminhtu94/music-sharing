import filetable.FileTable;
import filetable.FileTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class MusicSharingForm extends JFrame {
    private JPanel rootPanel;

    private FileTable localFileTable;
    private FileTable remoteFileTable;

    private JButton selectFolderBtn;
    private JSplitPane fileSplitPane;
    private JScrollPane scrollLocalFileList;
    private JScrollPane scrollRemoteFileList;

    final static private JFileChooser fileChooser = new JFileChooser();

    public MusicSharingForm() {
        super("Music Sharing");
        setContentPane(rootPanel);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        fileSplitPane.setDividerLocation(0.5);

        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);


        selectFolderBtn.addActionListener(handler -> fileChooserHandler());

        localFileTable = new FileTable(false);
        remoteFileTable = new FileTable(true);
        scrollLocalFileList.getViewport().setView(localFileTable);
        scrollRemoteFileList.getViewport().setView(remoteFileTable);

        
    }

    private void fileChooserHandler() {
        int returnVal = fileChooser.showOpenDialog(MusicSharingForm.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            System.out.println("[*] Selected folder: " + file.getAbsolutePath());
            selectFolderBtn.setText(file.getName());
            ArrayList<File> files = getAllMusicFiles(file.getAbsolutePath());
            ((FileTableModel) localFileTable.getModel()).setData(files);
            ((FileTableModel) localFileTable.getModel()).fireTableDataChanged();
        }
    }

    private ArrayList<File> getAllMusicFiles(String directoryPath) {
        File folder = new File(directoryPath);
        File[] listOfFiles = folder.listFiles();
        ArrayList<File> fileNames = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() &&
                (listOfFiles[i].getName().endsWith(".mp3") || listOfFiles[i].getName().endsWith(".flac"))) {
                fileNames.add(listOfFiles[i].getAbsoluteFile());
                System.out.println(listOfFiles[i].getAbsolutePath());
            }
        }

        return fileNames;
    }
}
