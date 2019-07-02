package GUI;

import Controllers.ShowSpecificAlbumOnClick;
import Logic.Music;
import Logic.PlayerManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

/**
 * This class is a panel that shows the albums .
 * @author Mahvash
 */
public class AlbumsPanel extends JPanel {

    private ArrayList<JButton> albumButton;
    private static ArrayList<String> albums;
    private JScrollPane jScrollPane;

    public AlbumsPanel() {
        super();

        jScrollPane = new JScrollPane(this);
        jScrollPane.setViewportView(this);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setViewportBorder(new LineBorder(Color.gray));
        jScrollPane.updateUI();
        jScrollPane.setVisible(true);
        this.setVisible(true);
        albumButton = new ArrayList<>();
        albums = new ArrayList<>();
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        try {
            Scanner sc = new Scanner(new FileReader(new File("src/AllAlbums.txt")));
            while (sc.hasNext()) {
                albums.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //sort album by time
        sortAlbums();;


        Dimension d = new Dimension(180, 180);
        for (int i = 0; i < albums.size(); i++) {
            JButton j = new JButton();
            j.setPreferredSize(d);
            albumButton.add(j);
            albumButton.get(i).addActionListener(new ShowSpecificAlbumOnClick());
            albumButton.get(i).setLayout(new BorderLayout());
            this.add(albumButton.get(i), gbc);
            albumButton.get(i).setVisible(true);
            albumButton.get(i).setName(albums.get(i));


            Scanner sc = null;
            try {
                sc = new Scanner(new FileReader(new File("src/" + albums.get(i) + ".txt")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            boolean flag = false;
            Music music = null;
            if (sc.hasNext()) {
                music = new Music(sc.nextLine());
                flag = true;
            }
            if (flag) {
                albumButton.get(i).setIcon(new ImageIcon(((ImageIcon) music.getSongData().getIcon()).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
            } else {
                try {

                    Image img = ImageIO.read(getClass().getResource("defaultSongIcon.png"));
                    albumButton.get(i).setIcon(new ImageIcon(img.getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            JTextArea a = new JTextArea("Album:" + albums.get(i));
            albumButton.get(i).add(a, BorderLayout.SOUTH);


            gbc.gridx++;
            if (gbc.gridx == 4) {
                gbc.gridx = 0;
                gbc.gridy++;
            }
            this.repaint();
            this.revalidate();

        }
    }

    /**
     * This method sorts the albums based on the time of the latest played
     * song of each of them
     */

    public void sortAlbums() {
        ArrayList<Date> temp=new ArrayList<>();
        for (int i = 0; i <albums.size() ; i++) {
            for (int j = 0; j < PlayerManager.getSongDataArrayList().size(); j++) {
                if(albums.get(i).equals(PlayerManager.getSongDataArrayList().get(j).getAlbum())){
                    temp.add(PlayerManager.getSongDataArrayList().get(j).getLastTimeListened());
                }
            }
        }

        for (int i = 0; i <temp.size()-1 ; i++) {
            for (int j = 0; j <temp.size()-1-i ; j++) {
                if(temp.get(j).before(temp.get(j+1))){
                    Collections.swap(temp,j,j+1);
                    Collections.swap(albums,j,j+1);
                }
            }
        }

    }


    public JScrollPane getjScrollPane() {
        return jScrollPane;
    }

}
