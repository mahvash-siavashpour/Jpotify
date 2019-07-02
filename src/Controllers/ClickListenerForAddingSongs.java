package Controllers;

import Logic.*;
import GUI.FileChooser;
//import Logic.AppendingObjectOutputStream;
import javazoom.jl.player.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

import static Logic.SavedSongData.*;


/**
 * adds song that is chosen by file chooser to an array list in Main class if they meet certain requirements,
 * such as being an mp3 file and etc.
 * @author niki
 */
public class ClickListenerForAddingSongs implements ActionListener {
    private String absolutePath;
    private File file;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.getSelectedFile();

        if (file==null) return;

        absolutePath  = file.getAbsolutePath();
        if(!absolutePath.endsWith(".mp3")) return;

        try {
            //writing to text file
            PrintWriter fr = new PrintWriter(new FileWriter(new File("src/AddedSongAdresses.txt"), true));
            Scanner sc = new Scanner(new FileReader(new File("src/AddedSongAdresses.txt")));


            while(sc.hasNext()){
                if(absolutePath.equals(sc.nextLine())){
                    return;
                }
            }

            SavedSongData.addToFile(absolutePath);

            fr.println(absolutePath);
            fr.flush();



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //adding to an album
        Music newMusic=new Music(absolutePath);
        String albumName=newMusic.getSongData().getAlbum();
        System.out.println(albumName);
        try {
            PrintWriter fr = new PrintWriter(new FileWriter(new File("src/"+albumName+".txt"), true));
            fr.println(newMusic.getAbsolutePath());
            fr.flush();

            fr= new PrintWriter(new FileWriter(new File("src/AllAlbums.txt"), true));
            //ADDED LATER
            Scanner sc=new Scanner(new FileReader(new File("src/AllAlbums.txt")));
            while(sc.hasNext()){
                if(sc.nextLine().equals(albumName)){
                    return;
                }
            }
            fr.println(albumName);
            fr.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
