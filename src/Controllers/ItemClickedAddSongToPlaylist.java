package Controllers;
import GUI.DialogBoxList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ItemClickedAddSongToPlaylist implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

        //Opens a dialog box that has a list of all available songs.
        DialogBoxList aggDialog=new DialogBoxList("AddedSongAdresses",new SelectSongToAddToPlaylistListener());

    }
}
