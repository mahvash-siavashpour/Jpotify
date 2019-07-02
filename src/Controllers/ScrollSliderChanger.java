package Controllers;

import GUI.MusicSliderBar;
import Logic.Main;
import Logic.PlayerManager;
import Logic.SongPlayer;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.IOException;

/**
 * This class contains a listener for a scroll slider on the Music slider bar
 * @author Mahvash , Niki
 */
public class ScrollSliderChanger implements ChangeListener {
    private static int prev = 0;
    private static int next = 0;
    private static boolean flag;
    private static double passedPercentage;
    private static int frames;

    /**
     * This method is a lsitener that sets the value of the slider based on
     * the current time of the song which is being played , and also notified if
     * the slider's value was changed manually.
     * @param e
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        Mp3File file = null;
        SongPlayer sP;
        flag = false;
        if (!source.getValueIsAdjusting()) {
            int fps = (int) source.getValue();
            sP = PlayerManager.getsP();
            try {
                file = new Mp3File(sP.getFileName());
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (UnsupportedTagException ex) {
                ex.printStackTrace();
            } catch (InvalidDataException ex) {
                ex.printStackTrace();
            }
            frames = file.getFrameCount();
            System.out.println("Go to time:" + fps + " of the song");

            //Now  we need to go to that time of the song
            MusicSliderBar.getjSliderThread().setCurrentTime(fps);
            if (next == 0) {
                next = fps;
            } else {
                prev = next;
                next = fps;
            }

            passedPercentage = (1.0) * fps / MusicSliderBar.getMusicLenght();
            if ((Math.abs(next - prev) > 1)&& !ButtonListenerPauseAndPlay.ifButtonPlaying())
                flag = true;
            else if ((Math.abs(next - prev) > 1) ) {
                sP.pauseSong();
                sP.playInMiddle((int) (frames * passedPercentage));
            } else if ((Math.abs(next - prev) == 1) && MusicSliderBar.getjSliderThread().getFlag() == 0)
                sP.playInMiddle((int) (frames * passedPercentage));
            else flag = false;

                if (fps == Main.getCurrentQueue().get(Main.getSongQueueIndex()).getMusicLength()) {
                    int newIndex = Main.getSongQueueIndex() + 1;
                    if (newIndex < 0 || newIndex >= Main.getCurrentQueue().size()) newIndex = 0;
                    System.out.println(newIndex);
                    Main.setSongQueueIndex(newIndex);
                    PlayerManager.playerManager();
                    ButtonListenerPauseAndPlay.setIfNewSong();
                }


        }

    }

    public static boolean ifSliderChangedWhilePaused() {
        return flag;
    }

    public static double getPassedPercentage() {
        return passedPercentage;
    }

    public static int getFrames() {
        return frames;
    }
}