package Logic;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class BinaryReader {
    public static void readIt(String path){
        if(path.endsWith(".bin")){
            Main.prepareObjIn();
            ArrayList<SongData>tmp=null;
            while(true){
                try {
                    tmp =(ArrayList<SongData>)Main.getObjIn().readObject();
                }  catch (EOFException e) {
                    break;
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
            }
//            Main.prepareObjOut();
            for (int i = 0; i <tmp.size() ; i++) {
                System.out.println("--"+tmp.get(i).getSongName());
            }
        }
    }

    public static void main(String[] args) {
        readIt("src/AddedSongs.bin");
    }
}
