package Controllers;

import GUI.ChoicesArea;
import Logic.Main;

import javax.swing.event.ListSelectionEvent;
import java.io.*;
import java.util.Scanner;

/**
 * This class contains a listener for a list that adds
 * the selected song item to the related playlist it was called on
 * by adding it to the playlist's text file
 * @author Mahvash
 */
public class SelectSongToAddToPlaylistListener implements javax.swing.event.ListSelectionListener {
    private static String songDirectory;
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            //Finding the selected item
            int index = e.getFirstIndex();
            songDirectory = null;
            System.out.println(e.getFirstIndex());
            Scanner sc = null;
            try {
                sc = new Scanner(new FileReader(new File("src/AddedSongAdresses.txt")));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            for (int i = 0; i <= index; i++) {
                songDirectory = sc.nextLine();
            }

            //writing the selected item to the file
            try {

                PrintWriter pw=new PrintWriter(new FileWriter(new File("src/"+SelectedPlaylistListener.getPlaylistName()+".txt"), true));
                //It prohibits adding an exisying song
                Scanner scanner=new Scanner(new FileReader(new File("src/"+SelectedPlaylistListener.getPlaylistName()+".txt")));
                while (scanner.hasNext()){
                    if(scanner.nextLine().equals(songDirectory)) {
                        System.out.println("Song Exists");
                        return;
                    }
                }
                pw.println(songDirectory);
                pw.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public static String getSongDirectory(){
        return songDirectory;
    }
}
