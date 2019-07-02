package Controllers;

import GUI.DialogBoxChooseName;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClickListenerForNewPlaylist implements ActionListener {
    private static boolean visible=false;
    @Override
    public void actionPerformed(ActionEvent e) {
        visible=true;
        new DialogBoxChooseName("ADD",new DialogListenerAddButton());

    }

}
