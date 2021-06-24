package Sapper;

import javafx.scene.media.Media;

import java.io.File;

public class MediaPlayer extends Thread{

    javafx.scene.media.MediaPlayer mediaPlayer;
    @Override
    public void run(){
        String backgroundSong = "src/music/music.mp3";
        Media hit = new Media(new File(backgroundSong).toURI().toString());
        mediaPlayer = new javafx.scene.media.MediaPlayer(hit);
        mediaPlayer.play();
    }
}
