package Controllers;

import GUI.SongsPanel;
import Logic.Main;
import Logic.PlayerManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class contains a listener for clicking a button and
 * plays the song this button points to.
 * @author Mahvash
 */
public class PlaySpecificSongOnClick implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jB = (JButton) (e.getSource());

        int newIndex=0;
        for (int i = 0; i < SongsPanel.getSongButton().size(); i++) {
            if(jB.equals(SongsPanel.getSongButton().get(i)))
                newIndex=i;
        }
        Main.setSongQueueIndex(newIndex);
        PlayerManager.playerManager();
        ButtonListenerPauseAndPlay.setSongToPlay();
    }
}
