package Logic;

import javazoom.jl.decoder.*;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MyEqualizer {
    public MyEqualizer(String path){
        try {
            AdvancedPlayer  aP = new AdvancedPlayer(new FileInputStream(new File(path)));
            Equalizer equalizer = new Equalizer();
            aP.getDecoder().setEqualizer(equalizer);
            aP.play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        try {
//             Bitstream bitstream = new Bitstream(new FileInputStream(new File()));
//            Equalizer equalizer = new Equalizer();
//            Decoder decoder = new Decoder();
//            decoder.setEqualizer(equalizer);
//
//            AudioDevice audio = FactoryRegistry.systemRegistry().createAudioDevice();
//            audio.open(decoder);
//            for(int i = quick_positions[0]; i > 0; i--) {
//                Header h = bitstream.readFrame();
//                if (h == null) {
//                    return;
//                }
//                bitstream.closeFrame();
//            }
//    } catch (BitstreamException e) {
//            e.printStackTrace();
//        } catch (JavaLayerException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}
