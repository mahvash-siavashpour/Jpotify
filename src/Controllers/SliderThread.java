package Controllers;

import GUI.MusicSliderBar;
import Logic.Main;

import java.util.concurrent.TimeUnit;

public class SliderThread extends Thread {
    private long currentTime = 0;
    private int flag = 0;//0 if paused, 1 otherwise.

    @Override
    public void run() {
        while (currentTime <= MusicSliderBar.getMusicLenght()) {
            MusicSliderBar.getJSlider().setValue((int) currentTime);
            MusicSliderBar.getShowTime().setText(formatText(currentTime));
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            moveSlider();
            if (currentTime == MusicSliderBar.getMusicLenght()){
                int newIndex= Main.getSongQueueIndex()+1;
                QueueIndexController.setIndex(newIndex);
                currentTime=0;
                ButtonListenerPauseAndPlay.setSongToPlay();
            }

        }
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void moveSlider() {
        if (flag == 1) {
            //it is being played
            currentTime++;
        }
    }

    public String formatText(long curr){
        long min =  curr/60;
        long sec = curr - min*60;
        long minAll =  MusicSliderBar.getMusicLenght()/60;
        long secAll = MusicSliderBar.getMusicLenght() - minAll*60;
        return ((min>=10)?""+min:"0"+min)+":"+ ((sec>=10)?""+sec:"0"+sec)+"/"+
                ((minAll>=10)?""+minAll:"0"+minAll)+":"+ ((secAll>=10)?""+secAll:"0"+secAll);
    }
}
