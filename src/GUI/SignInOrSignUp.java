package GUI;

import Controllers.*;

import javax.swing.*;
import java.awt.*;

public class SignInOrSignUp extends JFrame {
    private static JPanel panel1;
    private static JPanel panelSignIn;
    private static JPanel panelSignUp;

    public SignInOrSignUp(){

        Image icon = Toolkit.getDefaultToolkit().getImage("src/GUI/Jpotify.jpg");
        this.setIconImage(icon);
        this.setTitle("Sign in or sign up please!!");
        this.setVisible(true);
        this.setSize(500, 100);
        this.setLayout(new FlowLayout());
//        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addWindowListener(new SignInOrSignUpWindowActionListener());

        panel1 = new JPanel();
        panelSignUp = new JPanel();
        panelSignIn = new JPanel();

        this.add(panel1);
        panel1.setVisible(true);
        this.add(panelSignIn);
        panelSignIn.setVisible(false);
        this.add(panelSignUp);
        panelSignUp.setVisible(false);


        JButton signIn = new JButton("Sign in");
        signIn.addActionListener(new ClickListenerForSignInOrSignUp());
        panel1.add(signIn);

        JButton signUp = new JButton("Sign up");
        signUp.addActionListener(new ClickListenerForSignInOrSignUp());
        panel1.add(signUp);

        JButton adminTest = new JButton("admin test");
        adminTest.addActionListener(new ClickListenerForAccountManager());
        panel1.add(adminTest);

    }

    public static void setPanel1(JPanel panel1) {
        SignInOrSignUp.panel1 = panel1;
    }

    public static void setPanelSignIn(JPanel panelSignIn) {
        SignInOrSignUp.panelSignIn = panelSignIn;
    }

    public static void setPanelSignUp(JPanel panelSignUp) {
        SignInOrSignUp.panelSignUp = panelSignUp;
    }

    public static JPanel getPanel1() {
        return panel1;
    }

    public static JPanel getPanelSignIn() {
        return panelSignIn;
    }

    public static JPanel getPanelSignUp() {
        return panelSignUp;
    }
}

