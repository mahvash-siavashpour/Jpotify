package Controllers;

import GUI.MusicSliderBar;
import Logic.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *This class manages play and pause actions
 * @author mahvash
 */
public class ButtonListenerPauseAndPlay implements ActionListener {
    private static SongPlayer sP;
    private static boolean ifFirstTimePlaying = true;
    private static boolean ifNewSong = true;
    private static boolean buttonPlaying = false;
    private static boolean endOfSong = false;
    private static SliderThread sliderThread;

    /**
     * This is the action listener for the pause/play button . It changes the button icon and does the pause/play actions.
     * @param e action event
     */

    @Override
    public void actionPerformed(ActionEvent e) {

        //do nothing if there was not a queue to play from
        if(Main.getCurrentQueue()==null || Main.getCurrentQueue().size()==0)
            return;


        JButton jB = (JButton) (e.getSource());
        if (endOfSong) {

        }
        if (jB.getText().equals(" Play") || endOfSong) {
            if (jB.getText().equals(" Play")) {
                buttonPlaying = true;
                jB.setText("Pause");
                try {
                    Image img = ImageIO.read(getClass().getResource("pause1.png"));
                    jB.setIcon(new ImageIcon(img));
                    sP = PlayerManager.getsP();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            setSongToPlay();

        } else {
            buttonPlaying = false;
            jB.setText(" Play");
            try {
                Image img = ImageIO.read(getClass().getResource("play1.png"));
                jB.setIcon(new ImageIcon(img));
                sP.pauseSong();
                sliderThread.setFlag(0);
                ifFirstTimePlaying = false;

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * this method checks the status of a song and sets it to be played as needed
     */

    public static void setSongToPlay() {
        if(MusicSliderBar.getPlayButton().getText().equals(" Play")) {
            MusicSliderBar.getPlayButton().setText(" Pause");
            try {
                Image img = ImageIO.read(ButtonListenerPauseAndPlay.class.getResource("pause1.png"));
                MusicSliderBar.getPlayButton().setIcon(new ImageIcon(img));

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        buttonPlaying = true;
        if (ifFirstTimePlaying) {
            sliderThread = MusicSliderBar.getjSliderThread();
            sliderThread.setFlag(1);
            sliderThread.start();
            ifFirstTimePlaying = false;

        }

        if (ifNewSong && !ifFirstTimePlaying) {

            //song cover icon
            System.out.println(Main.getCurrentQueue().get(Main.getSongQueueIndex()));
            MusicSliderBar.getSongIconLable().setIcon(new ImageIcon(((ImageIcon) Main.getCurrentQueue().get(Main.getSongQueueIndex()).getIcon()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
            MusicSliderBar.getSongInfoBox().setText("\n\nSong:"+Main.getCurrentQueue().get(Main.getSongQueueIndex()).getSongName()+"\nAlbum:"+Main.getCurrentQueue().get(Main.getSongQueueIndex()).getAlbum()+"\nArtist:"+Main.getCurrentQueue().get(Main.getSongQueueIndex()).getArtist());
            System.out.println(sliderThread.getState());
            sliderThread.setCurrentTime(0);
            sliderThread.setFlag(1);
            if (sP != null)
                sP.pauseSong();
            sP = null;
            sP = PlayerManager.getsP();
            MusicSliderBar.getJSlider().setValue(0);
            MusicSliderBar.setMusicLength(Main.getCurrentQueue().get(Main.getSongQueueIndex()).getMusicLength());
            MusicSliderBar.getJSlider().setMaximum((int) Main.getCurrentQueue().get(Main.getSongQueueIndex()).getMusicLength());
            MusicSliderBar.getJSlider().setMajorTickSpacing(1000);
            ifNewSong = false;
            endOfSong = false;
            ifFirstTimePlaying = false;
        } else {
            sliderThread.setFlag(1);
            if (sP == null) {
                return;
            }
            sP.resumeSong();
        }
        sliderThread.setFlag(1);
        if (sP == null) {
            System.out.println("NULL");
            return;
        }
        if (sP.ifPlayerNotstarted()) {
            sP.playSong();
        } else {
            if (ScrollSliderChanger.ifSliderChangedWhilePaused()) {
                sP.pauseSong();
                sP.playInMiddle((int) (ScrollSliderChanger.getFrames() * ScrollSliderChanger.getPassedPercentage()));
            } else sP.resumeSong();
        }

    }

    public static boolean getIfFirstTimePlaying() {
        return ifFirstTimePlaying;
    }

    public static void setEndOfSong() {
        endOfSong = true;
    }

    public static SongPlayer getSongCurrentPlayer() {
        return sP;
    }

    public static void setsP(SongPlayer songPlayer) {
        sP = songPlayer;
    }

    public static void setIfFirstTimePlaying() {
        ifFirstTimePlaying = true;
    }

    public static boolean getIfNewSong() {
        return ifNewSong;
    }

    public static void setIfNewSong() {
        ifNewSong = true;
    }

    public static boolean ifButtonPlaying() {
        return buttonPlaying;
    }
}