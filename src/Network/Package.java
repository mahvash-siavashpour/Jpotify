package Network;

import Logic.Main;
import Logic.Music;
import Logic.SongPlayer;
import javazoom.jl.decoder.JavaLayerException;

import java.io.Serializable;

public class Package implements Serializable {
    private Music music;
    private SongPlayer sP;
    private String senderName;

    public Package(String absolutePath){
        music = new Music(absolutePath);
        try {
            sP = new SongPlayer(absolutePath);
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
        senderName = Main.getMyName();
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public SongPlayer getsP() {
        return sP;
    }

    public void setsP(SongPlayer sP) {
        this.sP = sP;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
