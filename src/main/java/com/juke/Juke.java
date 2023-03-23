package com.juke;
import javax.sound.sampled.*;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Juke extends Song {
    Connection con;
    Statement stmt;
    PreparedStatement prstm;
    private List<Song> songs;
    DataBase d = new DataBase();
    Scanner scanner = new Scanner(System.in);
    ResultSet rs ;
    int ask;
    String response = "";

    public Juke(List<Song> path, int id, String songName, String artistName, double duration, String genre, List<Song> songs) {
        super(path, id, songName, artistName, duration, genre);
        this.songs = songs;
    }

    public Juke(List<Song> songs) {
        this.songs = songs;
    }

    public Juke() {
    }
    Connection getConnection() {
        con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //System.out.println("driver loaded");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trailjuke", "user", "admin");
            //System.out.println("connection established");
        }catch (Exception e){
            System.out.println(e);
        }
        return con;
    }
    public void playSongCont() throws SQLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        DataBase d = new DataBase();
        Connection con = d.getConnection();
        Scanner scanner = new Scanner(System.in);
        Statement stmt = con.createStatement();
        ResultSet rs ;
        int ask;
        String response = "";
        rs= stmt.executeQuery("SELECT path FROM songs;");
        ArrayList<String> songPaths = new ArrayList<String>();
        while (rs.next()) {
            songPaths.add(rs.getString("path"));
        }
        String[] songPathsArr = songPaths.toArray(new String[songPaths.size()]);
        for (String path : songPathsArr) {
            File file = new File(path);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            response = "";
            response = response.toUpperCase();
            int currentSong = 0;
            while (!response.equals("Q")) {
                System.out.println("" +
                        "\n P = play" +
                        "\n S = Stop" +
                        "\n R = Reset" +
                        "\n Q = Quit" +
                        "\n N = Next" +
                        "\n B = Previous" +
                        "\n X = Pause");
                System.out.print("Enter your choice: ");
                response = scanner.next();
                response = response.toUpperCase();
                switch (response) {
                    case ("P"): {
                        clip.start();
                        break;
                    }
                    case ("S"): {
                        clip.stop();
                        break;
                    }
                    case ("R"): {
                        clip.setMicrosecondPosition(0);
                        break;
                    }
                    case ("Q"): {
                        clip.close();
                        break;
                    }
                    case ("N"): {
                        if (currentSong < songPathsArr.length - 1) {
                            currentSong++;
                            clip.stop();
                            clip.close();
                            file = new File(songPathsArr[currentSong]);
                            audioStream = AudioSystem.getAudioInputStream(file);
                            clip.open(audioStream);
                            clip.start();
                        } else {
                            currentSong = 0;
                            clip.stop();
                            clip.close();
                            file = new File(songPathsArr[currentSong]);
                            audioStream = AudioSystem.getAudioInputStream(file);
                            clip.open(audioStream);
                            clip.start();
                        }
                        break;
                    }
                    case ("B"): {
                        if (currentSong > 0) {
                            currentSong--;
                            clip.stop();
                            clip.close();
                            file = new File(songPathsArr[currentSong]);
                            audioStream = AudioSystem.getAudioInputStream(file);
                            clip.open(audioStream);
                            clip.start();
                        } else {
                            System.out.println("This is the first song");
                        }
                        break;
                    }
                    case ("X"): {
                        if (clip.isRunning()) {
                            clip.stop();
                        } else {
                            System.out.println("The song is already paused");
                        }
                        break;
                    }
                    default:
                        System.out.println("Not a valid response");
                        break;
                }
            }
            break;
        }
    }
}

