package Controllers;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class controls the volume
 * @author Mahvash
 */
public class VolumeChangeListener implements ChangeListener {
    /**
     * This is a listener for the volume slider on the music slider panel
     * It sets the volume based on the sliders value
     * @param e
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider volumeSlider = (JSlider) e.getSource();
            float value = volumeSlider.getValue();
            setVolume(value);


    }

    /**
     * This method gets a number between 0 to 100 and sets the volume based on that
     * @param value the value that volume is set to
     */
    public static void setVolume(float value) {

        try {
            Mixer.Info[] infos = AudioSystem.getMixerInfo();
            for (Mixer.Info info : infos) {
                Mixer mixer = AudioSystem.getMixer(info);
                if (mixer.isLineSupported(Port.Info.SPEAKER)) {
                    Port port = (Port) mixer.getLine(Port.Info.SPEAKER);
                    port.open();
                    if (port.isControlSupported(FloatControl.Type.VOLUME)) {
                        FloatControl volume = (FloatControl) port.getControl(FloatControl.Type.VOLUME);
                        volume.setValue(value / 100);
                    }
                    port.close();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error\n" + ex);
        }
    }


}





