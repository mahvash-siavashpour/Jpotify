package GUI;

import Controllers.*;
import Logic.Main;
import Logic.PlayerManager;
import Logic.SongData;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * This class is the panel that shows the songs on the selected
 * playlist. It contains buttons for each song and each button owns
 * a lable and an icon for the song's data and another button for liking
 * the song
 *
 * @author Mahvash
 */

public class SongsPanel extends JPanel {
    private static ArrayList<JButton> songButton;
    private JScrollPane jScrollPane;
    private static String selectedSong = null;

    public SongsPanel(ArrayList<SongData> songs) {
        super();

        //add scroller
        jScrollPane = new JScrollPane(this);
        jScrollPane.setViewportView(this);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setViewportBorder(new LineBorder(Color.gray));
        jScrollPane.updateUI();
        jScrollPane.setVisible(true);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        System.out.println(songs.size());
        songButton = new ArrayList<>();
        this.setVisible(true);
        Dimension d = new Dimension(180, 200);
        System.out.println("song size:" + songs.size());

        //showing songs
        for (int i = 0; i < songs.size(); i++) {
            JButton j = new JButton();
            j.setPreferredSize(d);
            songButton.add(j);
            songButton.get(i).setLayout(new BorderLayout());
            this.add(songButton.get(i), gbc);
            songButton.get(i).setVisible(true);
            songButton.get(i).setName(songs.get(i).getAbsolutePath());
            songButton.get(i).addActionListener(new PlaySpecificSongOnClick());
            if (songs.get(i).getIcon() != null)
                songButton.get(i).setIcon(new ImageIcon(((ImageIcon) songs.get(i).getIcon()).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
            if (songs.get(i).getSongName() != null) {
                JTextArea a = new JTextArea("Song:" + songs.get(i).getSongName() + "\nArtist:" + songs.get(i).getArtist());
                songButton.get(i).add(a, BorderLayout.SOUTH);
            } else {
                JTextArea a = new JTextArea("UNKNOWN");
                songButton.get(i).add(a, BorderLayout.NORTH);

            }

            int finalI = i;

            //creating a like button for every song
            JButton likeButton = new JButton();
            likeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (int k = 0; k < PlayerManager.getSongDataArrayList().size(); k++) {
                        if (PlayerManager.getSongDataArrayList().get(k).getAbsolutePath().equals(songs.get(finalI).getAbsolutePath())) {


                            //remove from favourite
                            if (songs.get(finalI).getIsLiked()) {
                                PlayerManager.getSongDataArrayList().get(k).setIsLiked(false);
                                FavouriteManager.removeFavourite(songs.get(finalI).getAbsolutePath());

                                //change button to disliked
                                try {
                                    Image img = ImageIO.read(getClass().getResource("Dislike1.png"));
                                    likeButton.setIcon((Icon) new ImageIcon(img.getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
                                    likeButton.setPreferredSize(new Dimension(30, 30));
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }


                            //add to favorite
                            else {
                                PlayerManager.getSongDataArrayList().get(k).setIsLiked(true);
                                FavouriteManager.addFavourite(songs.get(finalI).getAbsolutePath());

                                //change button to liked
                                try {
                                    Image img = ImageIO.read(getClass().getResource("Like1.png"));
                                    likeButton.setIcon((Icon) new ImageIcon(img.getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
                                    likeButton.setPreferredSize(new Dimension(30, 30));
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }

                        }

                    }
//                    SwingUtilities.updateComponentTreeUI(Main.getJpotifyGUI());
                }
            });


            if (songs.get(i).getIsLiked()) {
                try {
                    Image img = ImageIO.read(getClass().getResource("Like1.png"));
                    likeButton.setIcon((Icon) new ImageIcon(img.getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
                    likeButton.setPreferredSize(new Dimension(30, 30));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    Image img = ImageIO.read(getClass().getResource("Dislike1.png"));
                    likeButton.setIcon((Icon) new ImageIcon(img.getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
                    likeButton.setPreferredSize(new Dimension(30, 30));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            likeButton.setVisible(true);
            songButton.get(i).add(likeButton, BorderLayout.NORTH);

            gbc.gridx++;
            if (gbc.gridx == 4) {
                gbc.gridx = 0;
                gbc.gridy++;
            }
            JPopupMenu popupMenu = new JPopupMenu();

            JMenuItem addSharedPlaylist = new JMenuItem("Add to Shared Playlist");
            addSharedPlaylist.addActionListener(new MenuClickedAddSharedPlaylist());
            popupMenu.add(addSharedPlaylist);


            JMenuItem removeSharedPlaylist = new JMenuItem("Remove from Shared Playlist");
            removeSharedPlaylist.addActionListener(new MenuClickedRemoveSharedPlaylist());
            popupMenu.add(removeSharedPlaylist);


            songButton.get(i).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
                    if (SwingUtilities.isRightMouseButton(e)) {
                        popupMenu.show(songButton.get(finalI), e.getX(), e.getY());
                        selectedSong = songs.get(finalI).getAbsolutePath();
                    }
                }
            });
            this.repaint();
            this.revalidate();

        }


    }

    public static String getSelectedSongPath() {
        return selectedSong;
    }

    public static ArrayList<JButton> getSongButton() {
        return songButton;
    }


}
