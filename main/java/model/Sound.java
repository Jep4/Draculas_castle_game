package model;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundtracks[] = new URL[7];

    public Sound() {
        try {
            soundtracks[0] = getClass().getResource("/backgroundMusic.wav");
            soundtracks[1] = getClass().getResource("/StartMusic.wav");
            soundtracks[2] = getClass().getResource("/laugh.wav");
            soundtracks[3] = getClass().getResource("/coin2.wav");
            soundtracks[4] = getClass().getResource("/spike.wav");
            soundtracks[5] = getClass().getResource("/door.wav");
            soundtracks[6] = getClass().getResource("/WinMusic.wav");
        } catch (Exception e) {
            System.out.println("couldnt load sounds");
        }
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundtracks[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        if (this.clip == null){
            return;
        }
        clip.stop();
    }
}
