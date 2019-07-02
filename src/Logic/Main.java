package Logic;

import Controllers.VolumeChangeListener;
import GUI.*;
import Network.Client_ReceivesFiles;
import Network.Server_SendsFiles;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * This is the main class of the project
 */
public class Main {
    private static JpotifyGUI jpotifyGUI;
    private static ObjectOutputStream objOut;
    private static ObjectInputStream objIn;
    private static ArrayList<SongData> currentQueue;
    private static int songQueueIndex = 0;
    private static String myName = "admin";
    private static int flag = 0;
    private static Server_SendsFiles server_sendsFiles;
    private static Client_ReceivesFiles client_receivesFiles;

    public static void setIPList(ArrayList<String> IPList) {
        Main.IPList = IPList;
    }

    public static int getFlag() {
        return flag;
    }

    private static ArrayList<String> IPList;

    private static boolean jpotifyGUIWindowClosed; //true if closed, false otherwise.

    public static void main(String[] args) {


        try {
            Client_ReceivesFiles.prepareReceivedFilesDestination("src/RECEIVED/");
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentQueue = null;
        System.out.println("hello");

        SignInOrSignUp signInOrSignUp = new SignInOrSignUp();
        while (flag != 1) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        signInOrSignUp.dispose();

        //set volume to middle
        VolumeChangeListener.setVolume(50);
        new PlayerManager();
        System.out.println(PlayerManager.getSongDataArrayList().size());
        PlayerManager.playerManager();

        jpotifyGUI = new JpotifyGUI();
        IPList = new ArrayList<>();
        IPList.add("127.0.0.1");
        IPList.add("192.168.43.122");

        try {
            server_sendsFiles = new Server_SendsFiles(8080);
            server_sendsFiles.run();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * This method creats a sub Arraylist of the main Arraylist which is sorted based on the
     * last time listened.
     * @param fileName the text file's name from which we read the song's paths of the sun Arraylist
     * @return
     */
    public static boolean creatCurrentQueueByTime(String fileName) {
        Scanner sc = null;
        try {
            sc = new Scanner(new FileReader(new File("src/" + fileName + ".txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        if(!sc.hasNext()) return false;

        PlayerManager.getSongDataArrayList().sort(new SortByTime());
        currentQueue = null;
        currentQueue = new ArrayList<SongData>();
        //this way we get the current play list in the order that it is stored in the .bin file
        //first we get all the elements from text file
        ArrayList<String> songPaths = new ArrayList<>();
        while (sc.hasNext()) {
            songPaths.add(sc.nextLine());
        }

        for (int i = 0; i < PlayerManager.getSongDataArrayList().size(); i++) {
//                System.out.println(PlayerManager.getSongDataArrayList().get(i).getSongName()+"    "+PlayerManager.getSongDataArrayList().get(i).getLastTimeListened());
            for (int j = 0; j < songPaths.size(); j++) {
                if (PlayerManager.getSongDataArrayList().get(i).getAbsolutePath().equals(songPaths.get(j))) {
                    currentQueue.add(PlayerManager.getSongDataArrayList().get(i));
                }
            }
        }

        System.out.println("sub:" + currentQueue.size());

        return true;
    }

    /**
     * This method creats a sub Arraylist of the main Arraylist which is sorted based on the
     * order they were added in the text file
     * @param fileName the text file's name from which we read the song's paths of the sun Arraylist
     * @return
     */
    public static boolean creatCurrentQueueByAdd(String fileName) {

        Scanner sc = null;
        try {
            sc = new Scanner(new FileReader(new File("src/" + fileName + ".txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        if(!sc.hasNext()) return false;

        currentQueue = null;
        currentQueue = new ArrayList<SongData>();
        //this way we get the current play list in the order that it is stored in the .bin file
        //first we get all the elements from text file
        ArrayList<String> songPaths = new ArrayList<>();
        while (sc.hasNext()) {
            songPaths.add(sc.nextLine());
        }

        for (int j = 0; j < songPaths.size(); j++) {
            for (int i = 0; i < PlayerManager.getSongDataArrayList().size(); i++) {
                if (PlayerManager.getSongDataArrayList().get(i).getAbsolutePath().equals(songPaths.get(j))) {
                    currentQueue.add(PlayerManager.getSongDataArrayList().get(i));
                }
            }
        }

        System.out.println("sub:" + currentQueue.size());
        return true;
    }

    public static void setClient_receivesFiles(Client_ReceivesFiles client_receivesFiles) {
        Main.client_receivesFiles = client_receivesFiles;
    }

    public static Client_ReceivesFiles getClient_receivesFiles() {
        return client_receivesFiles;
    }

    public static ArrayList<String> getIPList() {
        return IPList;
    }

    public static void setCurrentQueue(ArrayList<SongData> arr) {
        currentQueue = arr;
    }

    public static int getSongQueueIndex() {
        return songQueueIndex;
    }

    public static void setSongQueueIndex(int i) {
        songQueueIndex = i;
    }

    public static ArrayList<SongData> getCurrentQueue() {
        return currentQueue;
    }

    public static JpotifyGUI getJpotifyGUI() {
        return jpotifyGUI;
    }

    public static ObjectOutputStream getObjOut() {
        return objOut;
    }

    public static void setObjOut(ObjectOutputStream objOut) {
        Main.objOut = objOut;
    }

    public static ObjectInputStream getObjIn() {
        return objIn;
    }

    public static void setObjIn(ObjectInputStream objIn) {
        Main.objIn = objIn;
    }

    public static void prepareObjOut() {
        try {
            if (objIn != null) {
                objIn.close();
            }
            if (objOut == null)
                objOut = new ObjectOutputStream(new FileOutputStream("src/AddedSongs.bin"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO ? add synchronized?
    public static void prepareObjIn() {
        try {
            if (!new File("src/AddedSongs.bin").exists()) return;
            if (objOut != null) {
                objOut.flush();
                objOut.close();
            }
            if (objIn != null) {
                objIn.close();
                objIn = new ObjectInputStream(new FileInputStream("src/AddedSongs.bin"));
            } else {
                objIn = new ObjectInputStream(new FileInputStream("src/AddedSongs.bin"));
            }

        } catch (IOException e) {
            //Todo figure out this exception
            e.printStackTrace();
        }

    }

    public static String getMyName() {
        return myName;
    }

    public static void setMyName(String myName) {
        Main.myName = myName;
    }

    public static void setFlag(int flag) {
        Main.flag = flag;
    }

    public static boolean isJpotifyGUIWindowClosed() {
        return jpotifyGUIWindowClosed;
    }

    public static void setJpotifyGUIWindowClosed(boolean isJpotifyGUIWindowClosed) {
        Main.jpotifyGUIWindowClosed = isJpotifyGUIWindowClosed;
    }
}
