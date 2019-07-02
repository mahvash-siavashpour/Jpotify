package Controllers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ButtonListenerRepeat implements ActionListener {
    private static boolean repeatOn=false;
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jB = (JButton) (e.getSource());
        if(jB.getText().equals("Off")){
            repeatOn=true;
            jB.setText("On");
        }
        else if(jB.getText().equals("On")){
            repeatOn=false;
            jB.setText("Off");
        }

    }
    public static boolean isRepeatOn(){
        return repeatOn;
    }
}
