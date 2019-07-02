package Logic;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.Instant;
import java.util.Date;

public class Music implements Serializable {
    private Mp3File mp3File;
    private String absolutePath;
    private SongData songData;

    public Music(String absolutePath) {
        this.absolutePath = absolutePath;
        try {
            mp3File = new Mp3File(absolutePath);
        } catch (IOException | InvalidDataException | UnsupportedTagException e) {
            e.printStackTrace();
        }
        try {
            songData = new SongData(absolutePath, Date.from(Instant.now()));
            catchData();
            songData.setMusicLength(mp3File.getLengthInSeconds());
        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
            e.printStackTrace();
        }


    }

    public Music(SongData songData) {
        this.songData = songData;
        try {
            mp3File = new Mp3File(songData.getAbsolutePath());
            catchData();
        } catch (IOException | InvalidDataException | UnsupportedTagException e) {
            e.printStackTrace();
        }
    }

    private void catchData() {
        if (mp3File == null) return;


        if (mp3File.hasId3v1Tag()) {

            //reading last 128 bytes
            Last128BytesDataReading();

            //Using np3agic library
//            LibraryDataReading();

        }

        if (mp3File.hasId3v2Tag()) {

            //Handle empty field
            if (mp3File.getId3v2Tag().getTitle() == null) {
                songData.setSongName(getNameForMe(mp3File.getFilename()));
                if (getNameForMe(mp3File.getFilename()) == null || getNameForMe(mp3File.getFilename()).equals(""))
                    songData.setSongName("Unknown");
            } else
                songData.setSongName(mp3File.getId3v2Tag().getTitle());


            if (mp3File.getId3v2Tag().getAlbum() == null)
                songData.setAlbum("Unknown");
            else if (mp3File.getId3v2Tag().getAlbum().equals(""))
                songData.setAlbum("Unknown");
            else
                songData.setAlbum(mp3File.getId3v2Tag().getAlbum());


            if (mp3File.getId3v2Tag().getArtist() == null)
                songData.setArtist("Unknown");
            else if (mp3File.getId3v2Tag().getArtist().equals(""))
                songData.setArtist("Unknown");
            else
                songData.setArtist(mp3File.getId3v2Tag().getArtist());

            songData.setGenre(0);
            songData.setGenre(mp3File.getId3v2Tag().getGenre());


            byte[] imageBytes = mp3File.getId3v2Tag().getAlbumImage();
            try {
                int localFlag = 0;
                if (imageBytes != null) {
                    Image img = ImageIO.read(new ByteArrayInputStream(imageBytes));
                    if (img != null) {
                        localFlag = 1;
                        Icon icon = new ImageIcon(img);
                        songData.setIcon(icon);
                    }

                }
                //handle empty icon
                if (localFlag == 0) {
                    try {
                        Image img = ImageIO.read(getClass().getResource("defaultSongIcon.png"));
                        songData.setIcon(new ImageIcon(img));

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void LibraryDataReading() {
        if (mp3File.getId3v1Tag().getTitle() == null) {
            songData.setSongName(getNameForMe(mp3File.getFilename()));
            if (getNameForMe(mp3File.getFilename()) == null || getNameForMe(mp3File.getFilename()).equals(""))
                songData.setSongName("Unknown");
        } else
            songData.setSongName(mp3File.getId3v1Tag().getTitle());

        if (mp3File.getId3v1Tag().getAlbum() == null)
            songData.setAlbum("Unknown");
        else if (mp3File.getId3v1Tag().getAlbum().equals(""))
            songData.setAlbum("Unknown");
        else
            songData.setAlbum(mp3File.getId3v1Tag().getAlbum());


        if (mp3File.getId3v1Tag().getArtist() == null)
            songData.setArtist("Unknown");
        else if (mp3File.getId3v1Tag().getArtist().equals(""))
            songData.setArtist("Unknown");
        else
            songData.setArtist(mp3File.getId3v1Tag().getArtist());

        songData.setGenre(0);
        songData.setGenre(mp3File.getId3v1Tag().getGenre());

        try {
            Image img = ImageIO.read(getClass().getResource("defaultSongIcon.png"));
            songData.setIcon(new ImageIcon(img));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void Last128BytesDataReading() {
        File song = new File(this.absolutePath);
        FileInputStream file = null;
        try {
            file = new FileInputStream(song);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int size = (int) song.length();
        try {
            file.skip(size - 128);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] last128 = new byte[128];
        try {
            file.read(last128);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String id3 = new String(last128);
        String tag = id3.substring(0, 3);
        if (tag.equals("TAG")) {

            if (id3.substring(3, 32) == null || id3.substring(3, 32) == "")
                songData.setSongName("Unknown");
            else
                songData.setSongName(id3.substring(3, 32));
            if (id3.substring(33, 62) == null || id3.substring(33, 62) == "")
                songData.setArtist("Unknown");
            else
                songData.setArtist(id3.substring(33, 62));
            if (id3.substring(63, 91) == null || id3.substring(63, 91) == "")
                songData.setAlbum("Unknown");
            else
                songData.setAlbum(id3.substring(63, 91));
        } else
            System.out.println(" does not contain ID3 information.");
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            Image img = ImageIO.read(getClass().getResource("defaultSongIcon.png"));
            songData.setIcon(new ImageIcon(img));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static String getNameForMe(String string) {
        for (int i = string.length() - 1; i >= 0; i--) {


            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                //Operating system is based on Windows
                if (string.charAt(i) == '\\') {
                    return string.substring(i + 1);
                }
            } else if (os.contains("nix") || os.contains("aix") || os.contains("nux") || os.contains("osx")) {
                //Operating system is based on Linux/Unix/*AIX
                //Operating system is Apple OSX based

                if (string.charAt(i) == '/') {
                    return string.substring(i + 1);
                }

            }
        }
        return null;
    }


    public void setSongData(SongData songData) {
        this.songData = songData;
    }

    public SongData getSongData() {
        return songData;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }
}
