package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * This class is a dialog box that is used for choosing names
 * or renaming playlists
 * @author Mahvash
 */
public class DialogBoxChooseName extends JDialog {
    private static JTextField textField;
    private JButton addButton;

    /**
     * The button on the dialog box does the action it's listener
     * contains.
     * @param action The button's text
     * @param listener The button's action listener
     */
    public DialogBoxChooseName(String action,ActionListener listener){
        JDialog addDialog=new JDialog();
        addDialog.setLayout(new GridLayout(1,2));
        addDialog.setSize(200, 100);
        addDialog.setBackground(Color.gray);
        addDialog.setVisible(true);
        addDialog.getDefaultCloseOperation();
        textField=new JTextField();
        textField.setVisible(true);
        addDialog.add(textField);
        textField.setBackground(Color.lightGray);
        addButton=new JButton(action);
        addButton.setBackground(Color.gray);
        addButton.setVisible(true);
        addButton.addActionListener(listener);
        addDialog.add(addButton);

    }
    public static JTextField getTextField() {
        return textField;
    }

}
