package Network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

class Client{
    private Socket socket;
    Client(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
    }

    public Socket getSocket() {
        return socket;
    }

    public void fetchData(){

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try{
            Client a = new Client("127.0.0.1", 8080);
            System.out.println("Connected!");

            BufferedReader inp = new BufferedReader(new InputStreamReader(a.socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(a.socket.getOutputStream()), true);

            ObjectInputStream ois = new ObjectInputStream(a.socket.getInputStream());


            while (true) {
                String s = sc.next();
                //System.out.println("your input was taken!");
                out.println(s);
                if(s.equals("quit")) break;
                Package received = (Package) ois.readObject();

            }
        }catch (IOException e){
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
