package Logic;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.Port;
import javax.sound.sampled.SourceDataLine;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

/**
 * This class contains the methods that control playing a song
 *
 * @author Mahvash
 */
public class SongPlayer implements Serializable {
    private Status playerStatus;
    private String fileName;
    private Thread t = null;

    public AdvancedPlayer getPlayer() {
        return player;
    }

    // the player actually doing all the work
    private AdvancedPlayer player;

    // locking object used to communicate with player thread
    private final Object playerLock = new Object();

    enum Status {
        NOTSTARTED, PLAYING, PAUSED, FINISHED;
    }

    /**
     * This is the constructor of the class that constructs a sing player
     * using the songs path
     *
     * @param fileName the path of the song
     * @throws JavaLayerException
     */
    public SongPlayer(String fileName) throws JavaLayerException {
        playerStatus = Status.NOTSTARTED;
        this.fileName = fileName;
        FileInputStream file;
        try {
            file = new FileInputStream(this.fileName);
            player = new AdvancedPlayer(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed to play the file.");
        }
    }

    /**
     * This method manages calling other methods of the class
     * to play the song based on it's status
     */
    public void playSong() {
        synchronized (playerLock) {
            switch (playerStatus) {
                case NOTSTARTED:
                    final Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            playInternal();
                        }
                    };
                    Thread t = new Thread(r);
                    t.setDaemon(true);
                    t.setPriority(Thread.MAX_PRIORITY);
                    playerStatus = Status.PLAYING;
                    t.start();
                    break;
                case PAUSED:
                    resumeSong();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Changes the status of the song player to Paused
     *
     * @return
     */
    public boolean pauseSong() {
        synchronized (playerLock) {
            if (playerStatus == Status.PLAYING) {
                playerStatus = Status.PAUSED;
            }
            return playerStatus == Status.PAUSED;
        }
    }

    /**
     * Changes the status of the song player to Playing
     *
     * @return
     */
    public boolean resumeSong() {
        synchronized (playerLock) {
            if (playerStatus == Status.PAUSED) {
                playerStatus = Status.PLAYING;
                playerLock.notifyAll();
            }
            return playerStatus == Status.PLAYING;
        }
    }

    /**
     * Changes the status of the song player to Stoped
     *
     * @return
     */
    public void stopSong() {
        synchronized (playerLock) {
            playerStatus = Status.FINISHED;
            playerLock.notifyAll();
        }
    }

    /**
     * This method plays the song frame by frame until the status of the player
     * changes
     */
    private void playInternal() {
        while (playerStatus != Status.FINISHED) {
            try {
                if (!player.decodeFrame()) {
                    break;
                }
            } catch (final JavaLayerException e) {
                System.out.println("EX");
                break;
            }

            // check if paused or terminated
            synchronized (playerLock) {
                while (playerStatus == Status.PAUSED) {
                    try {
                        playerLock.wait();
                    } catch (final InterruptedException e) {
                        // terminate player
                        break;
                    }
                }
            }
        }
        close();
    }

    /**
     * This method closes the current song player
     */
    public void close() {
        synchronized (playerLock) {
            playerStatus = Status.FINISHED;
        }
        try {
            player.close();
        } catch (final Exception e) {
            // ignore, we are terminating anyway
        }
    }

    /**
     * It checks if the player has started working or not and
     * return true if it has and false if it has not
     *
     * @return
     */
    public boolean ifPlayerNotstarted() {
        if (playerStatus == Status.NOTSTARTED)
            return true;
        return false;
    }

    /**
     * This method plays the song from a specific frame.
     */
    public void playInMiddle(int start) {

        //because we need to jump to the first frame , so we recreate the the file.
        FileInputStream file = null;
        try {
            file = new FileInputStream(this.fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            player = new AdvancedPlayer(file);
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }


        //jumping to the wanted frame.
        boolean ret = true;
        int offset = start;
        while (offset-- > 0 && ret) {
            try {
                ret = player.skipFrame();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }
        resumeSong();
    }

    public String getPlayerStatus() {
        return playerStatus.toString();
    }

    public String getFileName() {
        return fileName;
    }

}
