package Controllers;

import Logic.Main;
import Logic.PlayerManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
/**
 * This class contains a lsitener for a list that plays
 * the song item clicked on the list
 * @author Mahvash
 */
public class SelectPlayFromSearchMenu implements javax.swing.event.ListSelectionListener {
    private static String songName;

    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList jL = (JList) (e.getSource());
        //finds the name of the selected song
        if (e.getValueIsAdjusting() == false) {
            int index = jL.getSelectedIndex();
            songName= null;
            System.out.println(jL.getSelectedIndex());

            try {
                Scanner sc = new Scanner(new FileReader(new File("src/SearchResults.txt")));
                for (int i = 0; i <= index; i++) {
                    songName = sc.nextLine();
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

            for (int i = 0; i < Main.getCurrentQueue().size() ; i++) {
                if(songName.equals(Main.getCurrentQueue().get(i).getSongName())){
                    System.out.println(songName);
                    Main.setSongQueueIndex(i);
                    PlayerManager.playerManager();
                    ButtonListenerPauseAndPlay.setSongToPlay();
                }
            }
        }

    }
}
