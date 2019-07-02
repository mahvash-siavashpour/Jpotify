package Logic;

import Controllers.ButtonListenerPauseAndPlay;
import javazoom.jl.decoder.JavaLayerException;

import java.time.Instant;
import java.util.*;

public class PlayerManager {
    private static SongPlayer sP;
    private static ArrayList<SongData> songDataArrayList;

    public PlayerManager(){

        songDataArrayList = new ArrayList<>();
        Main.prepareObjIn();
        SavedSongData.readFromFile(Main.getObjIn());
        songDataArrayList.sort(new SortByTime());

    }
    public static void playerManager() {

        try {
            if(Main.getCurrentQueue()==null) return;
            System.out.println("Queue size: "+Main.getCurrentQueue().size());

            if(Main.getCurrentQueue().size()==0) return;

            System.out.println(Main.getCurrentQueue().get(Main.getSongQueueIndex()).getAbsolutePath());
            sP = new SongPlayer(Main.getCurrentQueue().get(Main.getSongQueueIndex()).getAbsolutePath());
            ButtonListenerPauseAndPlay.setIfNewSong();

            //comment if you don't want to play immediatly
//            ButtonListenerPauseAndPlay.setSongToPlay();

            //Change last time listened
            for (int i = 0; i <PlayerManager.getSongDataArrayList().size() ; i++) {

                if (PlayerManager.getSongDataArrayList().get(i).getAbsolutePath().equals(Main.getCurrentQueue().get(Main.getSongQueueIndex()).getAbsolutePath())){
                    PlayerManager.getSongDataArrayList().get(i).setLastTimeListened(Date.from(Instant.now()));
                    System.out.println("Last time listened modified");
                }

            }

        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }

    public static SongPlayer getsP() {
        return sP;
    }


    public static ArrayList<SongData> getSongDataArrayList() {
        return songDataArrayList;
    }

    public static void setSongDataArrayList(ArrayList<SongData> songDataArrayList) {
        PlayerManager.songDataArrayList = songDataArrayList;
    }
}


class SortByTime implements Comparator<SongData> {

    @Override
    public int compare(SongData songData, SongData t1) {
        if (songData.getLastTimeListened() != t1.getLastTimeListened()) {
            if (songData.getLastTimeListened().after(t1.getLastTimeListened())) {
                return -1;
            } else return 1;
        }
        if (songData.getTimeItWasAdded().after(t1.getTimeItWasAdded())) {
            return -1;
        } else if (songData.getTimeItWasAdded().before(t1.getTimeItWasAdded())) {
            return 1;
        }

        return 0;
    }
}
class SortRandomly implements Comparator<SongData> {

    @Override
    public int compare(SongData songData, SongData t1) {
        Random random = new Random();
        int a = random.nextInt();
        a = Math.abs(a);
        if(a%3==2) return -1;
        return a%3;
    }
}