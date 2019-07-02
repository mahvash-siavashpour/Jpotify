package GUI;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileChooser extends JFileChooser {
    private File selectedFile;
    public FileChooser(){
        super(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = this.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = this.getSelectedFile();
            if(selectedFile!=null)
                System.out.println(selectedFile.getAbsolutePath());
        }
    }


}
