/*
package com.juke;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MusicPlayer{

    public static void main(String[] args) throws Exception {


        public class MusicPlayer {
            public static void main(String[] args) throws Exception {
                // ... your existing code here ...
                int currentSong = 0;
                final long[] currentTime = {0};
                Timer timer = new Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        currentTime[0]++;
                        System.out.println("Current time: " + currentTime[0] + " seconds");
                    }
                });
                while (!response.equals("Q")) {
                    // ... your existing code here ...
                    switch (response) {
                        case ("P"):
                            clip.start();
                            timer.start();
                            break;
                        case ("S"):
                            clip.stop();
                            timer.stop();
                            currentTime[0] = 0;
                            break;
                        case ("R"):
                            clip.setMicrosecondPosition(0);
                            timer.stop();
                            currentTime[0] = 0;
                            break;
                        case ("Q"):
                            clip.close();
                            timer.stop();
                            currentTime[0] = 0;
                            break;
                        case ("N"):
                            if (currentSong < songPathsArr.length-1) {
                                currentSong++;
                                clip.stop();
                                clip.close();
                                file = new File(songPathsArr[currentSong]);
                                audioStream = AudioSystem.getAudioInputStream(file);
                                clip.open(audioStream);
                                timer.stop();
                                currentTime[0] = 0;
                            } else {
                                System.out.println("This is the last song");
                            }
                            break;
                        case ("B"):
                            if (currentSong > 0) {
                                currentSong--;
                                clip.stop();
                                clip.close();
                                file = new File(songPathsArr[currentSong]);
                                audioStream = AudioSystem.getAudioInputStream(file);
                                clip.open(audioStream);
                                timer.stop();
                                currentTime[0] = 0;
                            } else {
                                System.out.println("This is the first song");
                            }
                            break;
                        case ("X"):
                            if (clip.isRunning()) {
                                clip.stop();
                                timer.stop();
                            } else {
                                System.out.println("The song is already paused");
                            }
                            break;
                        default:
                            System.out.println("Not a valid response");
                            break;
                    }
                }
                int currentSong = 0;
                long currentTime = 0;
                Timer timer = new Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        currentTime++;
                        System.out.println("Current time: " + currentTime + " seconds");
                    }
                });
                while (!response.equals("Q")) {
                    // ... your existing code here ...
                    switch (response) {
                        case ("P"):
                            clip.start();
                            timer.start();
                            break;
                        case ("S"):
                            clip.stop();
                            timer.stop();
                            currentTime = 0;
                            break;
                        case ("R"):
                            clip.setMicrosecondPosition(0);
                            timer.stop();
                            currentTime = 0;
                            break;
                        case ("Q"):
                            clip.close();
                            timer.stop();
                            currentTime = 0;
                            break;
                        case ("N"):
                            if (currentSong < songPathsArr.length-1) {
                                currentSong++;
                                clip.stop();
                                clip.close();
                                file = new File(songPathsArr[currentSong]);
                                audioStream = AudioSystem.getAudioInputStream(file);
                                clip.open(audioStream);
                                timer.stop();
                                currentTime = 0;
                            } else {
                                System.out.println("This is the last song");
                            }
                            break;
                        case ("B"):
                            if (currentSong > 0) {
                                currentSong--;
                                clip.stop();
                                clip.close();
                                file = new File(songPathsArr[currentSong]);
                                audioStream = AudioSystem.getAudioInputStream(file);
                                clip.open(audioStream);
                                timer.stop();
                                currentTime = 0;
                            } else {
                                System.out.println("This is the first song");
                            }
                            break;
                        case ("X"):
                            if (clip.isRunning()) {
                                clip.stop();
                                timer.stop();
                            } else {
                                System.out.println("The song is already paused");
                            }
                            break;
                        default:
                            System.out.println("Not a valid response");
                            break;
                    }
                }
            }
        }
    }
}

*/
