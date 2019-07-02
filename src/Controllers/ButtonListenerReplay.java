package Controllers;

import GUI.MusicSliderBar;
import Logic.Music;
import Logic.PlayerManager;
import Logic.SongPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListenerReplay implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        PlayerManager.getsP().playInMiddle(0);
        MusicSliderBar.getjSliderThread().setCurrentTime(0);
        MusicSliderBar.getjSliderThread().setFlag(1);
        try {
            Image img = ImageIO.read(getClass().getResource("pause1.png"));
            MusicSliderBar.getPlayButton().setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        MusicSliderBar.getPlayButton().setText("Pause");
    }
}
