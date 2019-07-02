package GUI;

import Controllers.ClickListenerForSignInOrSignUp;
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
    private static JTextArea userName;

    public HomePanel() {
        super();
        jScrollPane = new JScrollPane(this);
        jScrollPane.setViewportView(this);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setViewportBorder(new LineBorder(Color.white));
        jScrollPane.updateUI();
        jScrollPane.setVisible(true);

        String user=ClickListenerForSignInOrSignUp.getUsernameTextField().getText();
        userName=new JTextArea("Hi,"+ user.substring(9,user.length()));
        this.add(userName,BorderLayout.CENTER);
//        userName.setBackground();
        userName.setFont(new Font("M",1,80));
        userName.setBackground(null);


    }
    public static JTextArea getUserName(){
        return userName;
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        Graphics2D g2 = (Graphics2D) g.create();
        int w = this.getWidth();
        int h = this.getHeight();
        //this line
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
        g2.setPaint(new GradientPaint(0, 0, Color.white, 0, h, Color.gray));
        g2.fillRect(0, 0, w, h);
        g2.dispose();
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

