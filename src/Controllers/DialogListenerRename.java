package Controllers;

import GUI.DialogBoxChooseName;
import Logic.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * this class contains a listener that gets a string from a static method and replace
 * it with an existing one in the text file "PlaylistNames.txt"
 * @author Mahvash
 */
public class DialogListenerRename implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String oldName=SelectedPlaylistListener.getPlaylistName();
        String newName= DialogBoxChooseName.getTextField().getText();

        //In order to avoid adding an existing playlist
        Scanner sc = null;
        try {
            sc = new Scanner(new FileReader(new File("src/PlaylistNames.txt")));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        while (sc.hasNext()){
            if(sc.nextLine().equals(newName)) {
                System.out.println("playlist already exists");
                return;
            }
        }

        //delete the old one
        ArrayList<String> allPlaylist=new ArrayList<>();
        int count=0;
        try {
            sc=new Scanner(new FileReader(new File("src/PlaylistNames.txt")));
            while (sc.hasNext()){
                String temp=sc.nextLine();
                if(!temp.equals(oldName)){
                    allPlaylist.add(temp);
                }
                else{
                    allPlaylist.add(newName);
                }
                count++;
            }


        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            PrintWriter pw=new PrintWriter(new FileWriter(new File("src/PlaylistNames.txt"),false));

            for (int i = 0; i <count ; i++) {
                pw.println(allPlaylist.get(i));
                pw.flush();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            sc=new Scanner(new FileReader(new File("src/"+oldName+".txt")));
            PrintWriter pw=new PrintWriter(new FileWriter(new File("src/"+newName+".txt")),true);
            while (sc.hasNext()){
                pw.println(sc.nextLine());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //refresh
        Main.getJpotifyGUI().getChoicesArea().getModel().removeAllElements();
        for (int i = 0; i <allPlaylist.size() ; i++) {
            Main.getJpotifyGUI().getChoicesArea().getModel().addElement(allPlaylist.get(i));
        }


    }
}
