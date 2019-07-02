package Controllers;

import Logic.Main;
import Network.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class SignInOrSignUpWindowActionListener implements WindowListener {
    @Override
    public void windowOpened(WindowEvent windowEvent) {
        UserPassVerification.getCurrentUsers();
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        Main.setFlag(1);

    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {
        Main.setFlag(1);
    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }
}
