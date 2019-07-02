package Controllers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseListenerForSliders implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        JSlider jSlider = (JSlider)mouseEvent.getSource();

        Point p = mouseEvent.getPoint();
        double percent = p.x / ((double) jSlider.getWidth());
        int range = jSlider.getMaximum() - jSlider.getMinimum();
        double newVal = range * percent;
        int result = (int)(jSlider.getMinimum() + newVal);
        jSlider.setValue(result);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
