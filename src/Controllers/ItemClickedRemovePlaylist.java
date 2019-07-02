package Controllers;

import Logic.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * this class contains a listener of one of the items on a menu
 * and removes the playlist that the menu was called on it
 * @author Mahvash
 */
public class ItemClickedRemovePlaylist implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String playlistName=SelectedPlaylistListener.getPlaylistName();
        System.out.println(playlistName);
        ArrayList<String> allPlaylist=new ArrayList<>();
        int count=0;
        try {
            Scanner sc=new Scanner(new FileReader(new File("src/PlaylistNames.txt")));
            while (sc.hasNext()){
                String temp=sc.nextLine();
                if(!temp.equals(playlistName)){
                    allPlaylist.add(temp);
                }
               count++;
            }


        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            PrintWriter pw=new PrintWriter(new FileWriter(new File("src/PlaylistNames.txt"),false));

            for (int i = 0; i <count-1 ; i++) {
                pw.println(allPlaylist.get(i));
                pw.flush();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //update list
        Main.getJpotifyGUI().getChoicesArea().getModel().removeElement(playlistName);


    }
}
