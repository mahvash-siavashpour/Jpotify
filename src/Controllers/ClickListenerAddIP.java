package Controllers;

import GUI.DialogBoxChooseName;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClickListenerAddIP implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
       new DialogBoxChooseName("Add IP",new DialogListenerAddIP());
    }
}
