package GUI;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;

public class MyLayerUI extends LayerUI<Component> {
    public MyLayerUI() {
        super();
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);

        Graphics2D g2 = (Graphics2D) g.create();

        int w = c.getWidth();
        int h = c.getHeight();
        //this line
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));

        g2.setPaint(new GradientPaint(0, 0, Color.white, 0, h, Color.gray));
        g2.fillRect(0, 0, w, h);

        g2.dispose();
    }

}
