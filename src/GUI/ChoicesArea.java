package GUI;

import Controllers.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Scanner;

/**
 * This class is the left panel of the main "JpotifyGUI" frame.
 * It contains buttons and a list for showing and managing playlists
 * as well as adding songs
 *
 * @author Mahvash
 */

public class ChoicesArea extends JPanel {
    private JScrollPane jScrollPane;
    private static DefaultListModel model;

    public ChoicesArea() {
        this.setLayout(new GridLayout(10, 1));
        this.setPreferredSize(new Dimension(190,10000));

        JButton saveButton = new JButton("Add Song");
        saveButton.setFont(saveButton.getFont().deriveFont(12f)); // will only change size to 14pt
        saveButton.setVisible(true);
        this.add(saveButton);
        saveButton.addActionListener(new ClickListenerForAddingSongs());


        JButton homeButton = new JButton("Home");
        homeButton.setVisible(true);
        homeButton.setFont(homeButton.getFont().deriveFont(12f));
        this.add(homeButton);
        homeButton.addActionListener(new ClickListenerForShowingHome());

        JButton songsButton = new JButton("Songs");
        songsButton.setVisible(true);
        songsButton.setFont(songsButton.getFont().deriveFont(12f)); // will only change size to 14pt

        this.add(songsButton);
        songsButton.addActionListener(new ClickListenerForShowingSongsList());
        try {
            Image img = ImageIO.read(getClass().getResource("songs1.png"));
            songsButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JButton albumsButton = new JButton("Albums");
        albumsButton.setVisible(true);
        albumsButton.setFont(albumsButton.getFont().deriveFont(12f)); // will only change size to 14pt
        this.add(albumsButton);
        albumsButton.addActionListener(new ClickListenerForShowingAlbums());

        JButton favouritePlaylistButton = new JButton("Favourite Playlist");
        favouritePlaylistButton.setVisible(true);
        favouritePlaylistButton.setFont(favouritePlaylistButton.getFont().deriveFont(12f)); // will only change size to 14pt
        this.add(favouritePlaylistButton);
        favouritePlaylistButton.addActionListener(new ClickListenerForShowingFavouritePlaylist());



        JButton sharedPlaylistButton = new JButton("Shared Playlist");
        sharedPlaylistButton.setVisible(true);
        sharedPlaylistButton.setFont(sharedPlaylistButton.getFont().deriveFont(12f)); // will only change size to 14pt
        this.add(sharedPlaylistButton);
        sharedPlaylistButton.addActionListener(new ClickListenerForShowingSharedPlaylist());


        JButton newPlayListButton = new JButton("New PlayList");
        newPlayListButton.setVisible(true);
        newPlayListButton.setFont(newPlayListButton.getFont().deriveFont(12f));
        this.add(newPlayListButton);
        newPlayListButton.addActionListener(new ClickListenerForNewPlaylist());
        try {
            Image img = ImageIO.read(getClass().getResource("add1.png"));
            newPlayListButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JLabel playlistLable = new JLabel("  Your Playlists:");
        playlistLable.setVisible(true);
        playlistLable.setFont(new Font("Verdana", 9, 10));
        playlistLable.setBackground(Color.cyan);
        this.add(playlistLable, BorderLayout.NORTH);

        model = new DefaultListModel();
        JList list = new JList(model);
        list.setVisible(true);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(2);
        list.addListSelectionListener(new SelectedPlaylistListener());
        list.setBackground(Color.cyan);
//        list.addListSelectionListener(new ListSelectionListener());
        try {
            String playlistName;
            if (!new File("src/PlaylistNames.txt").exists()) {
                PrintWriter fr = new PrintWriter(new FileWriter("src/PlaylistNames.txt"));
            }
            Scanner sc = new Scanner(new FileReader(new File("src/PlaylistNames.txt")));
            while (sc.hasNext()) {
                playlistName = sc.nextLine();
                model.addElement(playlistName);
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //Menu for right click on the list
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem showPlaylist=new JMenuItem("Show Playlist");
        showPlaylist.addActionListener(new ItemClickedShowPlaylist());
        popupMenu.add(showPlaylist);
        popupMenu.add(new JPopupMenu.Separator());
        JMenuItem addSong =new JMenuItem("Add Songs");
        addSong.addActionListener(new ItemClickedAddSongToPlaylist());
        popupMenu.add(addSong);
        JMenuItem removeSong=new JMenuItem("Remove Songs");
        removeSong.addActionListener(new ItemClickedRemoveSongFromPlaylist());
        popupMenu.add(removeSong);
        popupMenu.add(new JPopupMenu.Separator());
        JMenuItem renamePlaylist=new JMenuItem("Rename Playlist");
        renamePlaylist.addActionListener(new ItemClickedRenamePlaylist());
        popupMenu.add(renamePlaylist);
        JMenuItem removePlaylist=new JMenuItem("Remove Playlist");
        removePlaylist.addActionListener(new ItemClickedRemovePlaylist());
        popupMenu.add(removePlaylist);

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)    // if right mouse button clicked
                        && !list.isSelectionEmpty()            // and list selection is not empty
                        && list.locationToIndex(e.getPoint()) // and clicked point is
                        == list.getSelectedIndex()) {       //   inside selected item bounds
                    popupMenu.show(list, e.getX(), e.getY());
                }
            }
        });

        jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(list);
        this.add(jScrollPane);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setViewportBorder(new LineBorder(Color.pink));
        jScrollPane.updateUI();
        jScrollPane.setVisible(true);

    }
    public static DefaultListModel getModel(){
        return model;
    }



    @Override
    public void paint(Graphics g) {

        super.paint(g);

        Graphics2D g2 = (Graphics2D) g.create();
        int w = this.getWidth();
        int h = this.getHeight();
        //this line
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
        g2.setPaint(new GradientPaint(0, 0, Color.pink, 0, h, Color.cyan));
        g2.fillRect(0, 0, w, h);
        g2.dispose();
    }

}


