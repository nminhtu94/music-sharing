import filetable.FileTable;
import filetable.FileTableModel;
import filetable.MusicFileModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class MusicSharingForm extends JFrame {
    private JPanel rootPanel;

    private FileTable localFileTable;
    private FileTable remoteFileTable;

    private JButton selectFolderBtn;
    private JSplitPane fileSplitPane;
    private JScrollPane scrollLocalFileList;
    private JScrollPane scrollRemoteFileList;
    private JTextField searchTxf;
    private JButton searchBtn;
    private JTextField portTxf;
    private JButton updatePortBtn;
    private JButton addBtn;
    private JButton deleteBtn;

    final static private JFileChooser fileChooser = new JFileChooser();
    static private Path musicDirectory;

    public MusicSharingForm() {
        super("Music Sharing");
        setContentPane(rootPanel);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        fileSplitPane.setDividerLocation(0.5);

        selectFolderBtn.addActionListener(handler -> fileChooserHandler());
        searchBtn.addActionListener(handler -> onSearch());
        updatePortBtn.addActionListener(handler -> onUpdatePort());
        addBtn.addActionListener(handler -> onAddMusic());
        deleteBtn.addActionListener(handler -> onDeleteMusic());

        remoteFileTable = new FileTable();
        remoteFileTable.setRowSelectionAllowed(false);

        localFileTable = new FileTable();
        localFileTable.setRowSelectionAllowed(true);
        localFileTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        remoteFileTable.setTableListener(new FileTable.FileTableListener() {
            @Override
            public void onFileSelected(FileTable sender, MusicFileModel f) {
                if (f.isSelected()) {
                    // TODO: Start downloading file.
                    System.out.println("Start downloading file: " + f.getFile().getName());
                } else {
                    // TODO: Stop downloading file.
                    System.out.println("Stop downloading file: " + f.getFile().getName());
                }
            }
        });

        localFileTable.setTableListener(new FileTable.FileTableListener() {
            @Override
            public void onFileSelected(FileTable sender, MusicFileModel f) {
                if (f.isSelected()) {
                    // TODO: start uploading file.
                    System.out.println("Start uploading file: " + f.getFile().getName());
                } else {
                    // TODO: Stop uploading file.
                    System.out.println("Stop uploading file: " + f.getFile().getName());
                }
            }
        });
        scrollLocalFileList.getViewport().setView(localFileTable);
        scrollRemoteFileList.getViewport().setView(remoteFileTable);
    }

    private void fileChooserHandler() {
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        int returnVal = fileChooser.showOpenDialog(MusicSharingForm.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            System.out.println("[*] Selected folder: " + file.getAbsolutePath());
            musicDirectory = file.toPath();
            selectFolderBtn.setText(file.getName());

            ArrayList<MusicFileModel> files = getAllMusicFiles(file.getAbsolutePath());
            ((FileTableModel) localFileTable.getModel()).setData(files);
            ((FileTableModel) localFileTable.getModel()).fireTableDataChanged();
        }
    }

    private void onSearch() {
        String query = searchTxf.getText();
        // TODO: Start search.
    }

    private void onUpdatePort() {
        int port = Integer.parseInt(portTxf.getText());

        // TODO: Update port.
    }

    private void onAddMusic() {
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setMultiSelectionEnabled(true);
        int returnVal = fileChooser.showOpenDialog(MusicSharingForm.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();

            for (File file : files) {
                try {
                    Files.copy(
                            file.toPath(),
                            (new File(musicDirectory.toString() + "/" + file.getName())).toPath(),
                            StandardCopyOption.REPLACE_EXISTING
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            ArrayList<MusicFileModel> allFiles = getAllMusicFiles(musicDirectory.toString());
            ((FileTableModel) localFileTable.getModel()).setData(allFiles);
            ((FileTableModel) localFileTable.getModel()).fireTableDataChanged();
        }
    }

    private void onDeleteMusic() {
        int[] selectedIndexes = localFileTable.getSelectedRows();

        for (int index : selectedIndexes) {
            MusicFileModel musicFile = ((FileTableModel)localFileTable.getModel()).getData().get(index);
            try {
                Files.delete(musicFile.getFile().toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ArrayList<MusicFileModel> allFiles = getAllMusicFiles(musicDirectory.toString());
        ((FileTableModel) localFileTable.getModel()).setData(allFiles);
        ((FileTableModel) localFileTable.getModel()).fireTableDataChanged();
    }

    private ArrayList<MusicFileModel> getAllMusicFiles(String directoryPath) {
        File folder = new File(directoryPath);
        File[] listOfFiles = folder.listFiles();
        ArrayList<MusicFileModel> fileNames = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() &&
                (listOfFiles[i].getName().endsWith(".mp3") || listOfFiles[i].getName().endsWith(".flac"))) {

                // Create music file model with host and port.
                fileNames.add(new MusicFileModel(listOfFiles[i].getAbsoluteFile(), "127.0.0.1", 1234));
                System.out.println(listOfFiles[i].getAbsolutePath());
            }
        }
        return fileNames;
    }
}
