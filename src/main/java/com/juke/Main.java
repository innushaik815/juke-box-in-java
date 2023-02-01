package com.juke;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException, SQLException  {
        DataBase d = new DataBase();
        Juke juke=new Juke();
        Connection con = d.getConnection();
        Scanner scanner = new Scanner(System.in);
        Statement stmt = con.createStatement();
        ResultSet rs ;
        /*rs= stmt.executeQuery("SELECT path FROM songs;");
        ArrayList<String> songPaths = new ArrayList<String>();
        while (rs.next()) {
            songPaths.add(rs.getString("path"));
        }
        String[] songPathsArr = songPaths.toArray(new String[songPaths.size()]);*/
        int ask;
        String response = "";
        do {
            do{
                System.out.println("" +
                        "\nselect the options" +
                        "\n1 search audio from catalog" +
                        "\n2 play songs" +
                        "\n3 insert a music in catalog" +
                        "\n4 create a playlist and insert a song into play list" +
                        "\n5 delete a song from playlist" +
                        "\n6 sort by genre" +
                        "\n7 sort by artist");
                ask = scanner.nextInt();
                //create switch till exit
                switch(ask){
                    case 1: {
                        do {
                            d.getConnection();
                            d.displaySongs();
                            System.out.println("enter the name of song");
                            String songName = scanner.next();
                            //d.searchAudio(songName);       this searchAudio method of database class will play the song searched by passing the string song name in a while loop , d is the object of database class
                            d.searchAudio(songName);
                            ask=scanner.nextInt();
                        }while(ask==1);
                        System.out.println("thanks for listening!");
                        break;
                    }
                    case 2:{
                        int option;
                        System.out.println("" +
                                "\nif wanna play continiously from catalog ? then press 1 " +
                                "\nif wanna play from your playlist? then press 2" +
                                "\nif wanna shuffle from catalog? then its 3 ");
                        option =scanner .nextInt();
                        if (option == 1) {
                            juke.playSongCont();
                            break;
                        }else if(option==2) {
                            d.getConnection();
                            d.displayplaylist();
                            System.out.println("enter the users name ");
                            String playListUserName = scanner.nextLine();
                            playListUserName = scanner.nextLine();
                            PreparedStatement pstmt = con.prepareStatement("SELECT path FROM playlists where user like ?;");
                            pstmt.setString(1, "%" + playListUserName + "%");
                            rs = pstmt.executeQuery();
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
                                                System.out.println("This is the last song");
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
                                        default:{
                                            System.out.println("Not a valid response");
                                            break;
                                        }
                                    }
                                }
                                break;
                            }
                            break;
                        }else if(option==3){
                            d.getConnection();
                            d.displaySongs();
                            rs= stmt.executeQuery("SELECT path FROM songs;");
                            ArrayList<String> songPaths = new ArrayList<String>();
                            while (rs.next()) {
                                songPaths.add(rs.getString("path"));
                                //shuffle using Collections package
                                Collections.shuffle(songPaths);
                                //System.out.println("the shufflesd list"+songPaths);
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
                                                System.out.println("This is the last song");
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
                            break;
                        }else{
                            System.out.println("wrong option entered , so breaking out of loop!");
                        }
                        break;
                    }
                    case 3:{
                        do {
                            con = d.getConnection();
                            // String title, String artist,String genre,double duration, String path
                            System.out.println("enter title");
                            String title = scanner.nextLine();
                            scanner.nextLine();
                            System.out.println("enter artist name");
                            String artist = scanner.nextLine();
                            System.out.println("enter genre ");
                            String genre = scanner.nextLine();
                            System.out.println("enter the song path ");
                            String path = scanner.nextLine();
                            System.out.println("enter song duration");
                            double duration = scanner.nextDouble();
                            d.insertAudioFile(title, artist, genre, duration, path);
                            con.close();
                            System.out.println("wanna add another song to catalog? then press 1 else 0");
                            ask=scanner.nextInt();
                        }while(ask==1);
                        break;
                    }
                    case 4:{
                        do {
                            con=d.getConnection();
                            d.createPlaylistTable();
                            // String songName, String artist, String user,String path, Date date_created
                            //d.createPlaylist("dard","asim","innushaik815","Songs/Dard-Asim-Azhar(PagalWorld).wav" ,Date.valueOf("2023-01-28"));
                            scanner.nextLine();
                            System.out.println("enter artist name");
                            String artist=scanner.nextLine();
                            System.out.println("press enter");
                            scanner.nextLine();
                            System.out.println("enter your user name");
                            String user=scanner.nextLine();
                            System.out.println("press enter");
                            scanner.nextLine();
                            System.out.println("enter path");
                            String path=scanner.nextLine();
                            System.out.println("press enter");
                            scanner.nextLine();
                            System.out.println("enter song name");
                            String songName=scanner.nextLine();
                            System.out.println("press enter");
                            scanner.nextLine();
                            System.out.println("enter date created (YYYY-MM-DD): ");
                            LocalDate date = LocalDate.parse(scanner.nextLine());
                            d.createPlaylist(songName,artist,user,path, Date.valueOf(date));
                            System.out.println("wanna add another song to playlist? then press 1 else 0");
                            ask=scanner.nextInt();
                            con.close();
                        }while(ask==1);
                        break;
                    }
                    case 5:{
                        do {
                            con = d.getConnection();
                            d.displayplaylist();
                            System.out.println("enter the song name");
                            String sName=scanner.nextLine();
                            sName=scanner.nextLine();
                            d.removeSongFromPlaylist(sName);
                            System.out.println("wanna delete another song to playlist? then press 1 else 0");
                            ask=scanner.nextInt();
                        }while (ask==1);
                        break;
                    }
                    case 6:{
                        do{
                            d.getConnection();
                            d.displaySongs();
                            System.out.println("enter the genre of song");
                            String genre = scanner.next();
                            d.sortByGenre(genre);
                            PreparedStatement pstmt = con.prepareStatement("SELECT path FROM songs where genre like ?");
                            pstmt.setString(1, "%"+genre+"%");
                            rs = pstmt.executeQuery();
                            ArrayList <String> songPaths = new ArrayList<String>();
                            while (rs.next()) {
                                songPaths.add(rs.getString("path"));
                            }
                            String[] songPathsArr = songPaths.toArray(new String[songPaths.size()]);
                            for (String path : songPathsArr) {
                                File file = new File(path);
                                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                                Clip clip = AudioSystem.getClip();
                                clip.open(audioStream);
                                clip.start();
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
                                                System.out.println("This is the last song");
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
                            System.out.println("wanna try search and play again? then press 1 else 0 to exit");
                            ask=scanner.nextInt();
                        }while(ask==1);
                        System.out.println("thanks for listening!");
                        break;
                    }
                    case 7:{
                        do{
                            d.getConnection();
                            d.displaySongs();
                            System.out.println("enter the artist name");
                            String artist = scanner.next();
                            d.searchByArtist(artist);
                            PreparedStatement pstmt = con.prepareStatement("SELECT path FROM songs where artist like ?");
                            pstmt.setString(1, "%"+artist+"%");
                            rs = pstmt.executeQuery();
                            ArrayList <String> songPaths = new ArrayList<String>();
                            while (rs.next()) {
                                songPaths.add(rs.getString("path"));
                            }
                            String[] songPathsArr = songPaths.toArray(new String[songPaths.size()]);
                            for (String path : songPathsArr) {
                                File file = new File(path);
                                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                                Clip clip = AudioSystem.getClip();
                                clip.open(audioStream);
                                clip.start();
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
                                                System.out.println("This is the last song");
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
                            System.out.println("wanna try search and play again? then press 1 else 0 to exit");
                            ask=scanner.nextInt();
                        }while(ask==1);
                        System.out.println("thanks for listening!");
                        break;
                    }
                }
                System.out.println("wanna repeat the catalog? then press 1 else 0");
                ask=scanner.nextInt();
            }while(ask==1);
        } while (ask == 1);
    }
}
