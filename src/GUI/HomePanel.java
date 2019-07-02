package GUI;

import Logic.SongData;
import Logic.SongPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The center panel of the main frame
 * @author Mahvash
 */
public class HomePanel extends JPanel {
    private static boolean visible = true;
    private static JScrollPane jScrollPane;

    public HomePanel() {
        super();
        jScrollPane = new JScrollPane(this);
        jScrollPane.setViewportView(this);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setViewportBorder(new LineBorder(Color.pink));
        jScrollPane.updateUI();
        jScrollPane.setVisible(true);

    }

    public JScrollPane getjScrollPane(){
        return jScrollPane;
    }


    public static boolean ifPanelVisible() {
        return visible;
    }

    public static void setPanelVisible() {
        visible = true;
    }

    public static void setPanelNotVisible() {
        visible = false;
    }
}

