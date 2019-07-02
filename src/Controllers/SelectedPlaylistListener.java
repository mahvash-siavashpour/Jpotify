package Controllers;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * This class contains a listener for the playlist names' list
 * and it stores the name of the selected item of the list in a
 * static string
 * @author Mahvash
 */

public class SelectedPlaylistListener implements javax.swing.event.ListSelectionListener {
    private static String playlistName;
    @Override
    public void valueChanged(ListSelectionEvent e) {

        JList jL = (JList) (e.getSource());

        //finds the name of the selected play list
        if (e.getValueIsAdjusting() == false) {
            int index = jL.getSelectedIndex();
            playlistName = null;
            System.out.println(jL.getSelectedIndex());
            Scanner sc = null;
            try {
                sc = new Scanner(new FileReader(new File("src/playlistNames.txt")));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            for (int i = 0; i <= index; i++) {
                playlistName = sc.nextLine();
            }
        }
    }

    public static String getPlaylistName(){
        return playlistName;
    }
}
