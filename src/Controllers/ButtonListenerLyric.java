package Controllers;

import Logic.Main;
import Network.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * shows lyrics for the song which is currently being played.
 * @ niki
 */
public class ButtonListenerLyric implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String lyrics = "";
        try {
            lyrics = ParseFromWeb.parser(ParseFromWeb.makeURL(Main.getCurrentQueue().get(Main.getSongQueueIndex()).getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String newLyric = "";
        if(lyrics.length()<60) newLyric = lyrics;
        else {
            int counter = 60;
            for (int i = 0; i < lyrics.length(); ) {
                if (counter > lyrics.length()) break;
                while (lyrics.charAt(counter) != ' ') {
                    counter--;
                }
                newLyric = newLyric + "\n" + lyrics.substring(i, counter);
                i = counter;
                counter = i + 60;
            }
        }

        JDialog jDialog = new JDialog();
        jDialog.setSize(500, 400);
        jDialog.setVisible(true);

        JTextArea currentSongText = new JTextArea();
        currentSongText.setVisible(true);
        currentSongText.setFont(new Font("Verdana", 9, 13));
        currentSongText.setBackground(Color.GRAY);
        currentSongText.setText(newLyric);
        currentSongText.setBorder(new EmptyBorder(30, 20, 30, 20));
        JPanel tmp = new JPanel();
        tmp.setVisible(true);
        tmp.add(currentSongText);

        JScrollPane jScrollPane;
        jScrollPane = new JScrollPane(tmp);
        jScrollPane.setViewportView(currentSongText);
        jScrollPane.setVisible(true);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVisible(true);

        jDialog.add(jScrollPane);


    }
}
