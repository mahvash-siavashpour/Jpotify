package Controllers;

import java.util.ArrayList;

public class ReceivedFriendInfo {
    private static String friendName;
    private static String IP;
    private static String lastSong;
    private static String lastTimeListened;


    public ReceivedFriendInfo(String name,String lastSong,String lastTimeListened, String IP){
        this.friendName =name;
       this.lastSong=lastSong;
       this.IP=IP;
       this.lastTimeListened=lastTimeListened;
    }

    public static void setFriendName(String friendName) {
        ReceivedFriendInfo.friendName = friendName;
    }

    public static void setLastSong(String lastSong) {
        ReceivedFriendInfo.lastSong = lastSong;
    }

    public static void setLastTimeListened(String lastTimeListened) {
        ReceivedFriendInfo.lastTimeListened = lastTimeListened;
    }
    public static String getIP() {
        return IP;
    }

    public static void setIP(String IP) {
        ReceivedFriendInfo.IP = IP;
    }

    public static String getFriendName() {
        return friendName;
    }

    public static String getLastSong() {
        return lastSong;
    }

    public static String getLastTimeListened() {
        return lastTimeListened;
    }
}
