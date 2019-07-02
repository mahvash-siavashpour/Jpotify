package Controllers;

import Logic.Main;
import Logic.PlayerManager;
import Logic.SongPlayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListenerShufflePlay implements ActionListener {

    private static boolean shuffleOn=false;

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jB = (JButton) (e.getSource());
        if(jB.getText().equals("Off")){
            shuffleOn=true;
            jB.setText("On");
        }
        else if(jB.getText().equals("On")){
            shuffleOn=false;
            jB.setText("Off");
        }

    }
    public static boolean isShuffleOn() {
        return shuffleOn;
    }
}
