package Controllers;

import Logic.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * this class contains a listener for the button that shows the home panel
 * @author Mahvash
 */
public class ClickListenerForShowingHome implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        //cleans everything
        Main.getJpotifyGUI().getHomePanel().setVisible(true);
        Main.getJpotifyGUI().getHomePanel().removeAll();
        Main.getJpotifyGUI().getContentPane().invalidate();
        Main.getJpotifyGUI().getContentPane().revalidate();
        Main.getJpotifyGUI().getContentPane().repaint();


    }
}
