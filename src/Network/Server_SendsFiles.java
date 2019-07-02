package Network;

import Logic.Main;
import Logic.Music;
import Logic.PlayerManager;
import GUI.*;
import javazoom.jl.player.Player;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server_SendsFiles implements Runnable {
    private ServerSocket serverSocket;
    ExecutorService executorService;
    private boolean isRun;
    private static int numberOfClients = 0;
    private static int lastClientId = 0;

    public Server_SendsFiles(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.executorService = Executors.newCachedThreadPool();
        isRun = true;
    }

    @Override
    public void run() {
        System.out.println("Welcome");
        System.out.println("ready to connect!!");
        while (isRun) {
            try {
                Socket socket = this.serverSocket.accept();
                System.out.println("new client ip: " + socket.getInetAddress());
                Handler handler = new Handler(socket);
                handler.setClientId(++lastClientId);
                numberOfClients++;
                System.out.println("client ID: " + lastClientId);

                this.executorService.submit(handler);
                System.out.println("ready to accept other clients...");


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @return If GUI window is not closed, it returns the absolute path of the the song currently being played.
     * otherwise, It returns the absolute path of last song that was listened before closing the window.
     */
    private static String processRequest() {
//        PlayerManager.getsP() != null;
        if (!Main.isJpotifyGUIWindowClosed()) {
            System.out.println("shut it -------------------------------------------------------------------");
            return PlayerManager.getsP().getFileName();
        } else {
            try {
                Scanner sc = new Scanner(new FileReader("src/LastSongListened.txt"));
                return sc.nextLine();
            } catch (FileNotFoundException e) {
                return null;
            }
        }
    }


    private static class Handler implements Runnable {

        private Socket client;
        private int clientId;
        private String clientName;
        private boolean lastListenedIsWantedFlag = true;


        public Handler(Socket client) {
            this.client = client;
        }

        /**
         * this method is overridden. in this method server takes requests from the client and decides what course
         * of action to take depending on the requests.
         */
        @Override
        public void run() {
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            OutputStream os = null;
            try {
                BufferedReader inp = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);

                String protocolCommand = inp.readLine();
                int localFlag = 0;
                while (localFlag == 0) {
                    if (protocolCommand.startsWith("start")) {
                        clientName = protocolCommand.substring(protocolCommand.indexOf("start") + 6);
                        localFlag = 1;
                    } else out.println("start <yourName>");
                }
                System.out.println("heh that thing is called " + clientName);

                while (true) {
                    if(client.isClosed()) break;
                    protocolCommand = inp.readLine();
                    if (protocolCommand.contains("quit")) {
                        System.out.println(clientId + clientName + " went off!!!");
                        break;//or break?
                    }

                    if (protocolCommand.contains("get")) {
                        System.out.println("got");
                        if (protocolCommand.contains("file")) {
                            if (protocolCommand.contains("--lastListened")) {
                                String lastListenedSongPath = "";
                                lastListenedSongPath = processRequest();
                                try {
                                    System.out.println("processed request:" + lastListenedSongPath);
                                    if (lastListenedSongPath.equals("") || lastListenedSongPath == null) {
                                        System.err.println("request o dard");
                                        client.close();
                                        numberOfClients--;
                                        return;
                                    }

                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                sendFile(fis, bis, os, lastListenedSongPath);
                            }
                            else if (protocolCommand.contains("--sharedPlaylist")) {
                                if (!new File("src/SharedPlaylist.txt").exists()) {
                                    try {
                                        client.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    numberOfClients--;
                                    return;
                                }
                                int count = 0;
                                try {
                                    Scanner sc = new Scanner(new FileReader("src/SharedPlaylist.txt"));
                                    while (sc.hasNext()) {
                                        sc.nextLine();
                                        count++;
                                    }
//                                    count++;
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                out.println(count);
                                try {
                                    Scanner sc = new Scanner(new FileReader("src/SharedPlaylist.txt"));
                                    while (sc.hasNext()) {
                                        sendFile(fis, bis, os, sc.nextLine());
                                    }
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                out.println("Invalid command");
                                out.println("See 'get --help'.");
                            }
                        }
                        else {
                            if (protocolCommand.contains("--lastListened")) {
//                                System.out.println(PlayerManager.getsP().getFileName());
//                                if (Main.getCurrentQueue().get(Main.getSongQueueIndex())!=null) {
//                                if (PlayerManager.getsP().getPlayerStatus().equals("PLAYING")) {
                                if (protocolCommand.contains("title")) {
                                    if (!Main.isJpotifyGUIWindowClosed()) {
//                                        System.out.println("in here1");
                                        out.println(Main.getCurrentQueue().get(Main.getSongQueueIndex()).getSongName());
//                                        System.out.println("sent");

                                    } else {
//                                        System.out.println("in here 2");
                                        try {
                                            Scanner sc = new Scanner(new FileReader("src/LastSongListened.txt"));
                                            Music music = new Music(sc.nextLine());
                                            out.println(music.getSongData().getSongName());
                                        } catch (FileNotFoundException e) {
                                            out.println("An error occurred while fetching file name.");
                                        }
                                    }

                                } else if (protocolCommand.contains("time")) {
                                    if (!Main.isJpotifyGUIWindowClosed()) {

                                        out.println("now");
                                    }else{
                                        Date dateListened = new Date(new File("src/LastSongListened.txt").lastModified());
                                        //mahvash was here
                                        String diff = dateToString(dateListened);
                                        out.println(diff);
                                    }

                                }

                            }
                            else if (protocolCommand.contains("--sharedPlaylist")) {
                                if (!new File("src/SharedPlaylist.txt").exists()) {
                                    try {
                                        client.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    numberOfClients--;
                                    return;
                                }
                                int count = 0;
                                try {
                                    Scanner sc = new Scanner(new FileReader("src/SharedPlaylist.txt"));
                                    while (sc.hasNext()) {
                                        sc.nextLine();
                                        count++;
                                    }
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                out.println(count);
                                try {
                                    Scanner sc = new Scanner(new FileReader("src/SharedPlaylist.txt"));
                                    while (sc.hasNext()) {
                                        out.println(sc.nextLine());
                                    }
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                            else if (protocolCommand.contains("--help")) {
                                out.println("HELP:");
                                //TODO guide the lost sheep to salivation .
                                out.println("These are common commands used in various situations:");
                                out.println("get    get data or file using appropriate switches.");
                                out.println("quit");
                                out.println("start <name>");
                                out.println("some switches: --myID, --myName, --LastListened, --sharedPlaylist, --help");

                            }
                            else if (protocolCommand.contains("--myName")) {
                                out.println(clientName);
                            } else if (protocolCommand.contains("--yourName")) {
                                out.println(Main.getMyName());
                            } else if (protocolCommand.contains("--myID")) {
                                out.println(clientId);
                            } else {
                                out.println("Invalid command");
                                out.println("See 'get --help'.");
                            }
                        }
                    } else {
                        out.println("Invalid command");
                        out.println("See 'get --help'.");
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                if (fis!=null) fis.close();
                if (bis!=null) bis.close();
                if (os!=null) os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
//                if (client != null) {
//                    try {
//                        client.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }

            }
        }

        public boolean isLastListenedIsWantedFlag() {
            return lastListenedIsWantedFlag;
        }

        public void setLastListenedIsWantedFlag(boolean lastListenedIsWantedFlag) {
            this.lastListenedIsWantedFlag = lastListenedIsWantedFlag;
        }

        public int getClientId() {
            return clientId;
        }

        public void setClientId(int clientId) {
            this.clientId = clientId;
        }

        /**
         * this methos takes a single file with the absolute path of "path"(fourth param) and sends it to the client
         *
         * @param fis  A file input stream that is constructed with String path(third param)
         * @param bis  A buffered input stream that is constructed with fis(first param)
         * @param os   An output stream connected to the client.
         * @param path Absolute path to the file that is being sent.
         */
        private void sendFile(FileInputStream fis, BufferedInputStream bis, OutputStream os, String path) {

            try {
                File myFile = new File(path);
                byte[] mybytearray = new byte[(int) myFile.length()];

                fis = new FileInputStream(myFile);
                bis = new BufferedInputStream(fis);

                bis.read(mybytearray, 0, mybytearray.length);
                os = client.getOutputStream();

                System.err.println("Sending " + path + "(" + mybytearray.length + " bytes)");
                os.write(mybytearray, 0, mybytearray.length);
                os.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    numberOfClients--;
                    if (bis != null) bis.close();
                    if (os != null) os.close();
                    if(fis!=null) fis.close();
//                    if (client != null) client.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }


        /**
         * @param d a Date that moust be compared to now.
         * @return the fomatted String indicating the time difference between Date d and now
         */
        public static String dateToString(Date d) {

            String date[] = d.toString().split(" ", 0);
            String now[] = Date.from(Instant.now()).toString().split(" ", 0);
            String dateTime[] = date[3].split(":",0);
            String nowTime[] = now[3].split(":");
            Integer dateValue[] = new Integer[5];
            Integer nowValue[] = new Integer[5];
            String week[] = new String[]{"Sat", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri"};
            String months[] = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};


            //converting year to int
            dateValue[4] = Integer.valueOf(date[5]);
            nowValue[4] = Integer.valueOf(now[5]);


            //converting week to int
            for (int i = 0; i < week.length; i++) {
                if (week[i].equals(date[0])) {
                    dateValue[0] = i;
                }
                if (week[i].equals(now[0])) {
                    nowValue[0] = i;
                }
            }

            //convering months to int
            for (int i = 0; i < months.length; i++) {
                if (months[i].equals(date[1])) {
                    dateValue[1] = i;
                }
                if (months[i].equals(now[1])) {
                    nowValue[1] = i;
                }
            }

            //converting days to int
            dateValue[2] = Integer.valueOf(date[2]);
            nowValue[2] = Integer.valueOf(now[2]);


            //convering time int
            Integer intDateTime[] = new Integer[]{Integer.getInteger(dateTime[0]), Integer.getInteger(dateTime[1]), Integer.getInteger(dateTime[2])};
            Integer intNowTime[] = new Integer[]{Integer.getInteger(nowTime[0]), Integer.getInteger(nowTime[1]), Integer.getInteger(nowTime[2])};

            //compare year
            if (!date[5].equals(now[5])) {
                int res = nowValue[4] = dateValue[4];
                return "" + res + " y";
            }
            //compare month
            else if (!date[1].equals(now[1])) {
                int res = nowValue[1] - dateValue[1];
                return "" + res + " Mon";
            }
            //compare day
            else if (!date[2].equals(now[2])) {
                int res = nowValue[2] - dateValue[2];
                return "" + res + " days";
            }
            //compare time
            else if (!date[3].equals(now[3])) {
                int res;
                //compare hour
                if (!dateTime[0].equals(nowTime[0])) {
                    res = intNowTime[0] - intDateTime[0];
                    return "" + res + " h";
                }
                //compare minute
                else if (!dateTime[1].equals(nowTime[1])) {
                    res = intNowTime[1] - intDateTime[1];
                    return "" + res + " m";
                }
                //second
                else {
                    res = intNowTime[2] - intDateTime[2];
                    return ""+ res + " s";
                }
            }
            return "Now";
        }
    }

}
