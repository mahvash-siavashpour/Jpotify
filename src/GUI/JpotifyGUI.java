package GUI;

import Controllers.JpotifyWindowActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

/**
 * The main frame.
 */
public class JpotifyGUI extends JFrame {
    private static HomePanel homePanel;
    private static SongsPanel songsPanel;
    private static MusicSliderBar musicSliderBar;
    private static JLayer<Component> jLayer;
    private static AlbumsPanel albumsPanel;
    private static SearchBarPanel searcBarPanel;
    private static ChoicesArea choicesArea;

    public static FriendsActivityArea getFriendsActivityArea() {
        return friendsActivityArea;
    }

    public static void setFriendsActivityArea(FriendsActivityArea friendsActivityArea) {
        JpotifyGUI.friendsActivityArea = friendsActivityArea;
    }

    private static FriendsActivityArea friendsActivityArea;


    public JpotifyGUI() {
        super();
        Image icon = Toolkit.getDefaultToolkit().getImage("src/GUI/Jpotify.jpg");
        this.setIconImage(icon);
        this.addWindowListener(new JpotifyWindowActionListener());

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        this.setTitle("Jpotify");
        this.setVisible(true);
        this.setSize(1200, 700);
        this.setLayout(new BorderLayout());
//        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


        choicesArea = new ChoicesArea();
        choicesArea.setVisible(true);
        this.add(choicesArea, BorderLayout.WEST);


        friendsActivityArea = new FriendsActivityArea();
        friendsActivityArea.setVisible(true);
        this.add(friendsActivityArea.getjScrollPane(), BorderLayout.EAST);



        homePanel = new HomePanel();
        this.add(homePanel.getjScrollPane(), BorderLayout.CENTER);
//        jLayer = new JLayer<>(homePanel.getjScrollPane(), new MyLayerUI());
//        this.add(jLayer, BorderLayout.CENTER);



        musicSliderBar = new MusicSliderBar(100);
        musicSliderBar.setVisible(true);
        this.add(musicSliderBar, BorderLayout.SOUTH);
        searcBarPanel = new SearchBarPanel();
        searcBarPanel.setVisible(true);
        this.add(searcBarPanel, BorderLayout.NORTH);
        choicesArea.setBackground(Color.gray);
        //refreshes the layout after changes
        this.validate();

    }


    public static void setSongsPanel(SongsPanel songsPanel) {
        JpotifyGUI.songsPanel = songsPanel;
    }

    public static SearchBarPanel getSearcBarPanel() {
        return searcBarPanel;
    }

    public static void setSearcBarPanel(SearchBarPanel searcBarPanel) {
        JpotifyGUI.searcBarPanel = searcBarPanel;
    }

    public void setAlbumsPanel(AlbumsPanel albumsPanel) {
        JpotifyGUI.albumsPanel = albumsPanel;
    }

    public static SongsPanel getSongsPanel() {
        return songsPanel;
    }

    public static AlbumsPanel getAlbumsPanel() {
        return albumsPanel;
    }


    public static ChoicesArea getChoicesArea() {
        return choicesArea;
    }

    public static JLayer<Component> getjLayer(){
        return jLayer;
    }

    public HomePanel getHomePanel() {
        return homePanel;
    }

    public static MusicSliderBar getMusicSliderBar() {
        return musicSliderBar;
    }
}
