package Controllers;

import Logic.Main;
import Network.Client_ReceivesFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class contains a listener for a button that tries to connect to the
 * client , and it refreshes the Friend Activity Area by each click
 */
public class ClickButtonRefreshFriendActivity implements ActionListener {

    private static ArrayList<ReceivedFriendInfo> receivedFriendInfos;
    @Override
    public void actionPerformed(ActionEvent e) {

        //empty everything
       receivedFriendInfos=new ArrayList<>();

       Main.getJpotifyGUI().getFriendsActivityArea().removeAll();
       Main.getJpotifyGUI().getFriendsActivityArea().add(Main.getJpotifyGUI().getFriendsActivityArea().getRefresh());
       Main.getJpotifyGUI().getFriendsActivityArea().add(Main.getJpotifyGUI().getFriendsActivityArea().getAdd());
        for (int i = 0; i < Main.getIPList().size(); i++) {

            connect(Main.getIPList().get(i));

            Dimension d=new Dimension(190,20);
            JPanel friend=new JPanel();
            friend.setBackground(Color.pink);
            friend.setVisible(true);
            friend.setLayout(new GridLayout(4,1));
            Main.getJpotifyGUI().getFriendsActivityArea().add(friend);
            JLabel name=new JLabel(ClickButtonRefreshFriendActivity.getReceivedFriendInfos().get(i).getFriendName());
            name.setFont(new Font("Verdana", 9, 10));
            name.setHorizontalTextPosition(0);
            name.setVerticalTextPosition(0);
            name.setPreferredSize(d);
            friend.add(name);

            JButton getPlaylist=new JButton("Get Their Playlist !");
            getPlaylist.setName(ClickButtonRefreshFriendActivity.getReceivedFriendInfos().get(i).getIP());
            getPlaylist.setBackground(Color.pink);
            getPlaylist.addActionListener(new ClickListenerForGettingFriendPlaylist());
            friend.add(getPlaylist);
            getPlaylist.setPreferredSize(d);


            JButton friendLastSong=new JButton();
            friendLastSong.setLayout(new GridLayout(1,2));
            JLabel songName=new JLabel(ClickButtonRefreshFriendActivity.getReceivedFriendInfos().get(i).getLastSong());
            JLabel timeListened=new JLabel("     "+ ClickButtonRefreshFriendActivity.getReceivedFriendInfos().get(i).getLastTimeListened());
            friendLastSong.add(songName);
            friendLastSong.add(timeListened);
            friendLastSong.addActionListener(new ClickListenerForPlayingTheLastSongFriendPlaylist());
            friendLastSong.setName(ClickButtonRefreshFriendActivity.getReceivedFriendInfos().get(i).getIP());
            friendLastSong.setFont(new Font("Verdana", 9, 8));
            friendLastSong.setHorizontalTextPosition(0);
            friendLastSong.setVerticalTextPosition(0);
            friendLastSong.setPreferredSize(d);
            friend.add(friendLastSong);

            Main.getJpotifyGUI().revalidate();

        }

    }

    public static void connect (String IP) {
        try {
            Main.setClient_receivesFiles( new Client_ReceivesFiles(IP, 8080));
            if(Main.getClient_receivesFiles()==null) return;
           String name = Main.getClient_receivesFiles().getYourName();
            Main.setClient_receivesFiles( new Client_ReceivesFiles(IP, 8080));
            if(Main.getClient_receivesFiles()==null) return;
            String title = Main.getClient_receivesFiles().getLastListenedTitle();
            Main.setClient_receivesFiles( new Client_ReceivesFiles(IP, 8080));
            if(Main.getClient_receivesFiles()==null) return;
            String time = Main.getClient_receivesFiles().getLastListenedTime();
            ClickButtonRefreshFriendActivity.receivedFriendInfos.add(new ReceivedFriendInfo(name,title,time, IP));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void setReceivedFriendInfos(ArrayList<ReceivedFriendInfo> receivedFriendInfos) {
        ClickButtonRefreshFriendActivity.receivedFriendInfos = receivedFriendInfos;
    }

    public static ArrayList<ReceivedFriendInfo> getReceivedFriendInfos() {
        return receivedFriendInfos;
    }

}

