package Network;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class UserPassVerification {
    private static HashMap<String, String> userPasses = new HashMap<>();
    private static PrintWriter fr;
    private static Scanner sc;

    UserPassVerification(){
        renew();
    }

    private static void renew(){
        try {
          fr = new PrintWriter(new FileWriter("src/UserPasses.txt"), true);
          sc = new Scanner(new FileReader("src/UserPasses.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkUserPass(String username, String password){
//        if(userPasses.containsKey(username) & userPasses.get(username).equals(password)) return true;
        getCurrentUsers();
        if(userPasses.containsKey(username)){
            System.out.println("bo");
            if (userPasses.get(username).equals(password)){
                return true;
            }
        }
        System.out.println(username +"|"+password);
         return false;
    }

    /**
     * @return 1 if user was successfully added, 0 otherwise;
     */
    public static boolean addUserPass(String username, String password){
        renew();
        if(userPasses.containsKey(username)) return false;
        fr.println(username);
        fr.println(password);
        fr.flush();
        userPasses.put(username, password);
        return true;
    }


    public static void getCurrentUsers(){
        try {
            sc =  new Scanner(new FileReader("src/UserPasses.txt"));
            while (sc.hasNext()){
                String tmp1 = sc.nextLine();
                String tmp2 = sc.nextLine();
                userPasses.put(tmp1, tmp2);
                System.out.println(tmp1+"!"+ tmp2);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
