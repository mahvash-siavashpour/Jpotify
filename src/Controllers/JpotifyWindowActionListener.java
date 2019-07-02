package Controllers;

import GUI.MusicSliderBar;
import Logic.Main;
import Logic.PlayerManager;
import Logic.SavedSongData;
import javazoom.jl.player.Player;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;


public class JpotifyWindowActionListener implements WindowListener {
    @Override
    public void windowOpened(WindowEvent windowEvent) {
        Main.prepareObjIn();
        SavedSongData.readFromFile(Main.getObjIn());

        Main.setJpotifyGUIWindowClosed(false);

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        try {
            PrintWriter fr = new PrintWriter(new FileWriter("src/LastSongListened.txt"));
            if(!ButtonListenerPauseAndPlay.getIfFirstTimePlaying()) {
                fr.println(PlayerManager.getsP().getFileName());
                fr.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }



        Main.prepareObjOut();
        SavedSongData.writeToFile(PlayerManager.getSongDataArrayList());

        if(PlayerManager.getsP()!=null){
            System.out.println("Closing the song");
            PlayerManager.getsP().pauseSong();
            PlayerManager.getsP().stopSong();
            MusicSliderBar.getjSliderThread().setCurrentTime(1000000);
        }

    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {
        if(PlayerManager.getsP()!=null)
            PlayerManager.getsP().close();
        Main.setJpotifyGUIWindowClosed(true);
    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }
}
