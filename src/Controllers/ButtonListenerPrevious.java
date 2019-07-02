package Controllers;

import Logic.Main;
import Logic.PlayerManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * click listener for previous button
 * @author mahvash
 */
public class ButtonListenerPrevious implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        int newIndex= Main.getSongQueueIndex()-1;
        QueueIndexController.setIndex(newIndex);
        PlayerManager.playerManager();
        ButtonListenerPauseAndPlay.setSongToPlay();

    }
}
