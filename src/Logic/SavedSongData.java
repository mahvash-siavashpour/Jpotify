package Logic;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileFormat;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.MissingFormatArgumentException;

public class SavedSongData {
    private static int CouldBeAdded;
    public static void readFromFile(  ObjectInputStream objIn ){
        ArrayList<SongData> tmpArr = new ArrayList<>();
        if(objIn==null) return;
        try {
            while(true)
                tmpArr = (ArrayList<SongData>) objIn.readObject();
        } catch (EOFException e){

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        PlayerManager.setSongDataArrayList(tmpArr);
    }

    public static void writeToFile(ArrayList<SongData> songDataArrayList){
        System.err.println("delete this shit later");
        Main.prepareObjOut();
        try {
            Main.getObjOut().writeObject(songDataArrayList);
            Main.getObjOut().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addToFile(String absolutePath1){
        System.out.println("tryina adddddddd");

        if(absolutePath1==null) {
            System.err.println("absolute path for the song that is being added was passed as null!!");
            return;
        }
        
        Music music = new Music(absolutePath1);
        music.getSongData().setLastTimeListened(new Date(0));
        System.out.println("rthis damned song had its music made!!!!!");
        
        PlayerManager.getSongDataArrayList().add(music.getSongData());
        System.out.println("---=-=---  "+PlayerManager.getSongDataArrayList());

        PlayerManager.getSongDataArrayList().sort(new SortByTime());

        System.out.println("PlayerManager.getSongDataArrayList().size() = " + PlayerManager.getSongDataArrayList().size());
        
//        writeToFile(PlayerManager.getSongDataArrayList());
    }
}


