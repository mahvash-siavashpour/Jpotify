package Controllers;

import GUI.DialogBoxChooseName;
import GUI.DialogBoxList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionL implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        DialogBoxChooseName addIP=new DialogBoxChooseName("Add IP",new DialogListenerAddIP());
    }
}
