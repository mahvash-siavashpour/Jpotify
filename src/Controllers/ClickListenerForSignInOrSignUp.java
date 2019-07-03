package Controllers;

import GUI.JpotifyGUI;
import Logic.Main;
import Network.*;
import GUI.SignInOrSignUp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

public class ClickListenerForSignInOrSignUp implements ActionListener {
    private static JTextField usernameTextField;
    private static JTextField passwordTextField;
    private static JPanel addDialog;


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        SignInOrSignUp.getPanel1().setVisible(false);
        SignInOrSignUp.getPanelSignIn().setVisible(true);


        boolean visible = true;
        //Todo Correct this both here and in inout class!
        addDialog =SignInOrSignUp.getPanelSignIn();
        SignInOrSignUp.getFrames()[0].setSize(500, 200);
        addDialog.setLayout(new GridLayout(3, 1));
        addDialog.setSize(400, 300);
        addDialog.setBackground(Color.DARK_GRAY);
        addDialog.setVisible(true);

        usernameTextField = new JTextField("Username:  ");
        usernameTextField.setSelectedTextColor(Color.blue);
        usernameTextField.setVisible(true);
        passwordTextField = new JTextField("Password:  ");
        passwordTextField.setVisible(true);

        addDialog.add(usernameTextField);
        addDialog.add(passwordTextField);

        usernameTextField.setBackground(Color.white);
        passwordTextField.setBackground(Color.white);

        JPanel choice = new JPanel();
        choice.setVisible(true);

        JButton signInButton = new JButton("Sign in");
        signInButton.setBackground(Color.blue);
        signInButton.setVisible(true);
        JButton signUpButton = new JButton("Sign up");
        signUpButton.setBackground(Color.blue);
        signUpButton.setVisible(true);

        choice.add(signInButton);
        choice.add(signUpButton);
        addDialog.add(choice);

        signInButton.addActionListener(new ClickListenerForSignInButton());
        signUpButton.addActionListener(new ClickListenerForSignUpButton());
    }
    public static void setUsernameTextField(JTextField usernameTextField) {
        ClickListenerForSignInOrSignUp.usernameTextField = usernameTextField;
    }




    public static JTextField getPasswordTextField() {
        return passwordTextField;
    }

    public static JTextField getUsernameTextField() {
        return usernameTextField;
    }

    public static JPanel getAddDialog() {
        return addDialog;
    }
}

class ClickListenerForSignInButton implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String password = ClickListenerForSignInOrSignUp.getPasswordTextField().getText();
        String username = ClickListenerForSignInOrSignUp.getUsernameTextField().getText();

        boolean checked = UserPassVerification.checkUserPass(username.substring(11), password.substring(11));
        if (checked == true) {
            Main.setMyName(username.substring(11));
            System.out.println(Main.getMyName());
            SignInOrSignUp.getFrames()[0].dispose();
        } else{
            System.out.println("wrong password or user doesnt exist");
            System.out.println(username.substring(11)+ password.substring(11));
            SignInOrSignUp.getPanel1().setVisible(false);
            SignInOrSignUp.getPanelSignIn().setVisible(true);
//            SignInOrSignUp.getFrames()[0].dispose();
            //todo try again
        }
    }
}
class ClickListenerForSignUpButton implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String password = ClickListenerForSignInOrSignUp.getPasswordTextField().getText();
        String username = ClickListenerForSignInOrSignUp.getUsernameTextField().getText();

        boolean checked = UserPassVerification.addUserPass(username.substring(11), password.substring(11));
        if (checked == true) {
            Main.setMyName(username.substring(11));
            System.out.println(Main.getMyName());
            SignInOrSignUp.getFrames()[0].dispose();
        } else{
            System.out.println("username taken");
            SignInOrSignUp.getPanel1().setVisible(false);
            SignInOrSignUp.getPanelSignIn().setVisible(true);
            //todo try again
        }
    }
}
