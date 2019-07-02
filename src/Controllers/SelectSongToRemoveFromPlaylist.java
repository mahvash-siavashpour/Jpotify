package Controllers;

import javax.swing.event.ListSelectionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * This class contains a listener for a list that removes
 * the selected song item from the related playlist it was called on
 * by removing it from the playlist's text file
 * @author Mahvash
 */
public class SelectSongToRemoveFromPlaylist implements javax.swing.event.ListSelectionListener {
    private String songDirectory;

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            //Finding the selected item
            int count = 0;
            int index = e.getFirstIndex();
            songDirectory = null;
            System.out.println(e.getFirstIndex());
            Scanner sc = null;
            ArrayList<String> songPath = new ArrayList<>();
            try {
                sc = new Scanner(new FileReader(new File("src/" + SelectedPlaylistListener.getPlaylistName() + ".txt")));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

            while (sc.hasNext()) {
                if (count != index)
                    songPath.add(sc.nextLine());
                else sc.nextLine();
                count++;
            }

            //Deleting the selected item from the file
            try {
                PrintWriter pw = new PrintWriter(new FileWriter(new File("src/" + SelectedPlaylistListener.getPlaylistName() + ".txt"), false));
                for (int i = 0; i < count - 1; i++) {
                    pw.println(songPath.get(i));
                    pw.flush();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
