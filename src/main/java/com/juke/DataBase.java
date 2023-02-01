package com.juke;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataBase  extends Song{
    public DataBase(List<Song> path, int id, String songName, String artistName, double duration, String genre, Connection con, Statement stmt, PreparedStatement prstm) {
        super(path, id, songName, artistName, duration, genre);
        this.con = con;
        this.stmt = stmt;
        this.prstm = prstm;
    }
    public DataBase(){
    }
    Connection con;
    Statement stmt;
    PreparedStatement prstm;
    Connection getConnection() {
        con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("driver loaded");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trailjuke", "user", "admin");
            System.out.println("connection established");
        }catch (Exception e){
            System.out.println(e);
        }
        return con;
    }
    public Song createTable() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS songs (id int NOT NULL AUTO_INCREMENT PRIMARY KEY,title VARCHAR(255), artist VARCHAR(255),genre varchar(30),duration double, path VARCHAR(255))";
            stmt=con.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("table created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Song insertAudioFile(String title, String artist,String genre,double duration, String path) {
        try {
            String sql = "INSERT INTO songs (title, artist,genre,duration, path) VALUES(?,?,?,?,?);";
            prstm = con.prepareStatement(sql);
            prstm.setString(1,setSongName(title));
            prstm.setString(2,setArtistName(artist));
            prstm.setString(3,setGenre(genre));
            prstm.setDouble(4,setDuration(duration));
            prstm.setString(5,setPath(path));
            prstm.executeUpdate();
            System.out.println("inserted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void sortByGenre(String genreName) {
        try {
            String sql = "SELECT * FROM songs where genre like? ORDER BY genre ASC;";
            prstm =con.prepareStatement(sql);
            prstm.setString(1,"%"+genreName+"%");
            ResultSet rs=prstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String artist = rs.getString("artist");
                String genre = rs.getString("genre");
                double duration = rs.getDouble("duration");
                String path = rs.getString("path");
                System.out.println(id + " | " + title + " | " + artist + " | " + genre + " | " + duration + " | " + path);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet displaySongs() {
        try {
            stmt= con.createStatement();
            String sql = "SELECT * FROM songs order by id;";
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next()){
                System.out.println(rs.getInt(1)+"\t"+rs.getString(3)+"\t\t"+rs.getString(4)+"\t\t"+rs.getString(2));
            }
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet displayplaylist() {
        try {
            stmt= con.createStatement();
            String sql = "SELECT * FROM playlists order by id;";
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next()){
                System.out.println(rs.getString("songName")+"\t\t"+rs.getString("artist")+"\t\t"+rs.getString("user")+"\t"+rs.getString("date_created"));
            }
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*public void searchAudio(String songName){
        try {
            String sql="select * from songs where title like ?;";
            prstm =con.prepareStatement(sql);
            prstm.setString(1,"%"+songName+"%");
            ResultSet rs=prstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String songTitle = rs.getString("title");
                String artist = rs.getString("artist");
                String genre = rs.getString("genre");
                double duration = rs.getDouble("duration");
                String path = rs.getString("path");

                System.out.println("ID: " + id + ", Title: " + songTitle+ ", Artist: " + artist+ ", Genre: " + genre+ ", Duration: " + duration+ ", Path: " + path);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/
    public void searchAudio(String songName){
        try {
            DataBase d = new DataBase();
            Connection con = d.getConnection();
            Scanner scanner = new Scanner(System.in);
            Statement stmt = con.createStatement();
            ResultSet rs ;
            int ask;
            String response = "";
            String sql="select * from songs where title like ?;";
            prstm =con.prepareStatement(sql);
            prstm.setString(1,"%"+songName+"%");
            rs=prstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String songTitle = rs.getString("title");
                String artist = rs.getString("artist");
                String genre = rs.getString("genre");
                double duration = rs.getDouble("duration");
                String path = rs.getString("path");
                System.out.println("ID: " + id + ", Title: " + songTitle+ ", Artist: " + artist+ ", Genre: " + genre+ ", Duration: " + duration+ ", Path: " + path);
                PreparedStatement pstmt = con.prepareStatement("SELECT path FROM songs where title like ?");
                pstmt.setString(1, "%"+songName+"%");
                rs = pstmt.executeQuery();
                ArrayList <String> songPaths = new ArrayList<String>();
                while (rs.next()) {
                    songPaths.add(rs.getString("path"));
                }
                String[] songPathsArr = songPaths.toArray(new String[songPaths.size()]);
                for (String pathh : songPathsArr) {
                    File file = new File(pathh);
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
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
    }
    public void searchByArtist(String artistName){
        try {
            String sql="select * from songs where artist like ?;";
            prstm =con.prepareStatement(sql);
            prstm.setString(1,"%"+artistName+"%");
            ResultSet rs=prstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String songTitle = rs.getString("title");
                String artist = rs.getString("artist");
                String genre = rs.getString("genre");
                double duration = rs.getDouble("duration");
                String path = rs.getString("path");

                System.out.println("ID: " + id + ", Title: " + songTitle+ ", Artist: " + artist+ ", Genre: " + genre+ ", Duration: " + duration+ ", Path: " + path);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void createPlaylistTable() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS playlists (id int NOT NULL AUTO_INCREMENT PRIMARY KEY, songName VARCHAR(30),artist VARCHAR(30), user VARCHAR(30), path VARCHAR(255), date_created DATE)";
            stmt=con.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("playlist table created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createPlaylist( String songName, String artist, String user,String path, Date date_created) {
        try {
            String sql = "INSERT INTO playlists (songName,artist, user, path, date_created) VALUES(?,?,?,?,?);";
            prstm = con.prepareStatement(sql);
            prstm.setString(1, songName);
            prstm.setString(2,artist);
            prstm.setString(3,user);
            prstm.setString(4,path);
            prstm.setDate(5,date_created);
            prstm.executeUpdate();
            System.out.println("song "+songName+" has been added to playlist");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeSongFromPlaylist(String song) {
        try {
            String sql = " delete from playlists where songName=?;";
            prstm = con.prepareStatement(sql);
            prstm.setString(1, song);
            prstm.executeUpdate();
            System.out.println("deleted the song: "+song);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            DataBase d=new DataBase();
            d.getConnection();
            d.createTable();
//            d.insertAudioFile("sanam teri kasam","sachin","romance",3.6,"Songs/Sanam-Teri-Kasam_PagalWorld_.wav");
//            d.insertAudioFile("dard","asim","sad",2.6,"Songs/Dard-Asim-Azhar_PagalWorld_.wav");
//            d.insertAudioFile("hamari adhuri kahani","arjit singh","sad",4.2,"Songs/Hamari-Adhuri-Kahani_PagalWorld_.wav");
//            d.insertAudioFile("jalebi baby","tesher","party",3.2,"Songs/Jalebi-Baby_PagalWorld_.wav");
//            d.insertAudioFile("o jane jaan dono jahan","atif aslam","love",4.5,"Songs/O-Jane-Ja-Dono-Jahan_PagalWorld_.wav");
//            d.insertAudioFile("tera hone laga hu","atif aslam","love",3.8,"Songs/Tera-Hone-Laga-Hoon_PagalWorld_.wav");
//            d.insertAudioFile("fitoor","arjit singh","romance",4.1,"Songs/Tera-Yeh-Ishq-Mera-Fitoor_PagalWorld_.wav");
            d.displaySongs();
            System.out.println(":::::::::::::::searched song::::::::::::::::");
            d.searchAudio("dard");
            d.sortByGenre("sad");
            d.searchByArtist("atif aslam");
           //d.createPlaylistTable();
//            d.createPlaylist("dard","asim","innushaik815","Songs/Dard-Asim-Azhar(PagalWorld).wav" ,Date.valueOf("2023-01-28"));
//            d.removeSongFromPlaylist("dard");
            d.displayplaylist();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}