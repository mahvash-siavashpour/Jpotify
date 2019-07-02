package GUI;

import Controllers.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * The bottom panel of the main frame that the music can
 * be controlled from. It contains buttons and sliders for that.
 * This panel also shows the current song playing's datas
 * @author Mahvash
 */

public class MusicSliderBar extends JPanel {
    private static SliderThread jSliderThread;
    private static JSlider jSlider;
    private static long musicLength;
    private long MUSIC_LENGHT;
    private JButton previousButton;
    private static JButton playButton;
    private JButton nextButton;
    private JButton replayButton;
    private JButton repeatButton;
    private JButton lyricsButton;
    private static JTextArea showTime;
    private static JLabel songIconLable;
    private static JTextArea songInfoBox;


    //    private Thread jSliderThread;
    MusicSliderBar(long musicLength) {
        super();
        this.setBackground(Color.gray);
        this.setLayout(new BorderLayout());
        this.setVisible(true);

        JPanel currSongPanel = new JPanel();
        currSongPanel.setLayout(new BorderLayout());
        currSongPanel.setVisible(true);

        JLabel currentSongLable = new JLabel("");
        currentSongLable.setVisible(true);
        currentSongLable.setFont(new Font("Verdana", 9, 13));
        currentSongLable.setBackground(Color.red);
        currSongPanel.add(currentSongLable, BorderLayout.NORTH);
        currSongPanel.setVisible(true);
        currSongPanel.setBackground(Color.gray);
        this.add(currSongPanel, BorderLayout.WEST);
        currSongPanel.setVisible(true);


        JPanel rightPanel = new JPanel();
        rightPanel.setVisible(true);
        this.add(rightPanel, BorderLayout.CENTER);


        //creating song icon
        songIconLable = new JLabel();
        Dimension d = new Dimension(120, 110);
        songIconLable.setVisible(true);
        songIconLable.setPreferredSize(d);
        songInfoBox =new JTextArea();
        songInfoBox.setVisible(true);
        songInfoBox.setBackground(Color.gray);
        songIconLable.setPreferredSize(new Dimension(100,100));
        songInfoBox.setFont(new Font("M",1,10));
        currSongPanel.add(songIconLable,BorderLayout.WEST);
        currSongPanel.add(songInfoBox,BorderLayout.EAST);




        rightPanel.setLayout(new GridLayout(2, 1));
        MUSIC_LENGHT = musicLength;
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.gray);
        topPanel.setLayout(new FlowLayout());
        rightPanel.add(topPanel);

        //Creating previous button
        previousButton = new JButton();
        previousButton.setVisible(true);
        previousButton.setBackground(Color.gray);
        previousButton.addActionListener(new ButtonListenerPrevious());
        topPanel.add(previousButton);
        try {
            Image img = ImageIO.read(getClass().getResource("previous1.png"));
            previousButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Creating Play/Pause button
        playButton = new JButton();
        playButton.setVisible(true);
        playButton.setText(" Play");
        playButton.addActionListener(new ButtonListenerPauseAndPlay());
        topPanel.add(playButton);
        try {
            Image img = ImageIO.read(getClass().getResource("play1.png"));
            playButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        playButton.setBackground(Color.gray);

        //Creating next button
        nextButton = new JButton();
        nextButton.setVisible(true);
        nextButton.setBackground(Color.gray);
        nextButton.addActionListener(new ButtonListenerNext());
        topPanel.add(nextButton);
        try {
            Image img = ImageIO.read(getClass().getResource("next1.png"));
            nextButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Creating ShufflePlay button
        JButton shufflePlayButton = new JButton("Off");
        shufflePlayButton.setVisible(true);
        shufflePlayButton.setBackground(Color.gray);
        topPanel.add(shufflePlayButton);
        shufflePlayButton.addActionListener(new ButtonListenerShufflePlay());
        try {
            Image img = ImageIO.read(getClass().getResource("shuffle1.png"));
            shufflePlayButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            ex.printStackTrace();
        }



        //creating repeat button
         repeatButton= new JButton("Off");
        repeatButton.setVisible(true);
        repeatButton.setBackground(Color.gray);
        topPanel.add(repeatButton);
        repeatButton.addActionListener(new ButtonListenerRepeat());
        try {
            Image img = ImageIO.read(getClass().getResource("repeat1.png"));
            repeatButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        //Creating replay button
        replayButton = new JButton("");
        replayButton.setVisible(true);
        replayButton.setBackground(Color.gray);
        topPanel.add(replayButton);
        replayButton.addActionListener(new ButtonListenerReplay());
        try {
            Image img = ImageIO.read(getClass().getResource("replay1.png"));
            replayButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        lyricsButton = new JButton("Lyrics");
        lyricsButton.setVisible(true);
        lyricsButton.setBackground(Color.gray);
        topPanel.add(lyricsButton);
        lyricsButton.addActionListener(new ButtonListenerLyric());
        try {
            Image img = ImageIO.read(getClass().getResource("lyrics1.png"));
            lyricsButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        //creating volume slider
        JSlider volumeSlider=new JSlider(0,100,50);
        volumeSlider.setVisible(true);
        volumeSlider.addChangeListener(new VolumeChangeListener());
        volumeSlider.setPaintLabels(true);
        volumeSlider.setPaintTrack(true);
        volumeSlider.setMajorTickSpacing(50);
        volumeSlider.setBackground(Color.gray);
        topPanel.add(volumeSlider);

        //Volume lable
        JLabel volume=new JLabel(":Volume");
        volume.setFont(new Font("M",20,15));
        volume.setVisible(true);
        topPanel.add(volume);



        //Creating jSlider
        jSlider = new JSlider(0, (int) MUSIC_LENGHT, 0);
        jSlider.setVisible(true);
        jSlider.setPaintTicks(true);
        jSlider.setPaintLabels(true);
        jSlider.addChangeListener(new ScrollSliderChanger());
        jSlider.setBackground(Color.gray);

        jSlider.setPaintTrack(true);

        //creating bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.gray);
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setVisible(true);
        rightPanel.add(bottomPanel);
        bottomPanel.add(jSlider, BorderLayout.CENTER);

        //Creating a text field to show time
        showTime = new JTextArea("00:00/00:00");
        bottomPanel.add(showTime, BorderLayout.EAST);
        showTime.setFont(showTime.getFont().deriveFont(16f)); // will only change size to 16pt
        showTime.setBackground(Color.gray);
        showTime.setVisible(true);


        //Thread for Sliding
        jSliderThread = new SliderThread();

        //creating a listener class for it in this case creates undesired lag.
        jSlider. addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();
                double percent = p.x / ((double) jSlider.getWidth());
                int range = jSlider.getMaximum() - jSlider.getMinimum();
                double newVal = range * percent;
                int result = (int)(jSlider.getMinimum() + newVal);
                jSlider.setValue(result);
            }
        });

        volumeSlider. addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();
                double percent = p.x / ((double) volumeSlider.getWidth());
                int range = volumeSlider.getMaximum() - volumeSlider.getMinimum();
                double newVal = range * percent;
                int result = (int)(volumeSlider.getMinimum() + newVal);
                volumeSlider.setValue(result);
            }
        });


    }



    public static JTextArea getSongInfoBox() {
        return songInfoBox;
    }

    public static void setPlayButton(JButton playButton) {
        MusicSliderBar.playButton = playButton;
    }
    public static JButton getPlayButton(){
        return playButton;
    }
    public static JTextArea getShowTime() {
        return showTime;
    }

    public static void setMusicLength(long length) {
        musicLength =length;
    }

    public static JSlider getJSlider() {
        return jSlider;
    }

    public static long getMusicLenght() {
        return musicLength;
    }

    public static SliderThread getjSliderThread() {
        return jSliderThread;
    }
    public static void setjSliderThread(SliderThread sliderThread){jSliderThread=sliderThread;}
    public static JLabel getSongIconLable() {
        return songIconLable;
    }


}






