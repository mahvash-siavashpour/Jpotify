package GUI;

import Controllers.*;

import javax.swing.*;
import java.awt.*;

public class SignInOrSignUp extends JFrame {
    private static JPanel panel1;
    private static JPanel panelSignIn;
    private static JPanel panelSignUp;

    public SignInOrSignUp(){

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Image icon = Toolkit.getDefaultToolkit().getImage("src/GUI/Jpotify.jpg");
        this.setIconImage(icon);
        this.setTitle("Sign in or sign up please!");
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

        JButton signIn = new JButton("log in/sign up");
        signIn.setPreferredSize(new Dimension(100,50));
        signIn.setBackground(Color.CYAN);
        signIn.addActionListener(new ClickListenerForSignInOrSignUp());
        panel1.add(signIn);

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

