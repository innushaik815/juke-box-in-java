package com.juke;

import java.sql.ResultSet;
import java.util.List;

public class Artist extends Juke {
    public Artist(List<Song> path, int id, String songName, String artistName, double duration, String genre, List<Song> songs) {
        super(path, id, songName, artistName, duration, genre, songs);
    }

    public Artist(List<Song> songs) {
        super(songs);
    }

    public Artist() {
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
}
