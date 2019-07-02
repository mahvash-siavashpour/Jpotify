package Controllers;

import GUI.SongsPanel;
import Logic.Main;
import Logic.PlayerManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * this class contains a listener for the button that shows the shared playlist
 *
 * @author Mahvash
 */
public class ClickListenerForShowingSharedPlaylist implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

        //cleaning everything
        Main.getJpotifyGUI().getHomePanel().setVisible(true);
        Main.getJpotifyGUI().getHomePanel().removeAll();
        Main.getJpotifyGUI().getContentPane().invalidate();
        Main.getJpotifyGUI().getContentPane().revalidate();
        Main.getJpotifyGUI().getContentPane().repaint();


        //set all songs as the current playlist
        if (Main.creatCurrentQueueByTime("SharedPlaylist")) {
            Main.setSongQueueIndex(0);
            PlayerManager.playerManager();


            //making new panel show up
            SongsPanel songsPanel = new SongsPanel(Main.getCurrentQueue());
            Main.getJpotifyGUI().setSongsPanel(songsPanel);
            Main.getJpotifyGUI().getHomePanel().removeAll();
            Main.getJpotifyGUI().getHomePanel().add(Main.getJpotifyGUI().getSongsPanel(), BorderLayout.CENTER);
            Main.getJpotifyGUI().revalidate();
        }

    }
}
