package Controllers;

import GUI.DialogBoxChooseName;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemClickedRenamePlaylist implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

        DialogBoxChooseName newDialog=new DialogBoxChooseName("Rename",new DialogListenerRename());

    }
}
