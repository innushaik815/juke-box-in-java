package com.juke;
import java.util.ArrayList;
import java.util.List;

public class Song{
    private String path;
    private int id;
    private String songName;
    private String artistName;
    private double duration;
    private String genre;

    public Song(List<Song> path, int id, String songName, String artistName, double duration, String genre) {
        this.path = String.valueOf(new ArrayList<>(path));
        this.id = id;
        this.songName = songName;
        this.artistName = artistName;
        this.duration = duration;
        this.genre = genre;

    }
    public Song(){

    }

    public String getPath() {
        return path.toString();
    }

    public String setPath(String path) {
        this.path = path.toString();
        return path;
    }

    public int getId() {
        return id;
    }

    public int setId(int id) {
        this.id = id;
        return id;
    }

    public String getSongName() {
        return songName;
    }

    public String setSongName(String songName) {
        this.songName = songName;
        return songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String setArtistName(String artistName) {
        this.artistName = artistName;
        return artistName;
    }

    public double getDuration() {
        return duration;
    }

    public double setDuration(double duration) {
        this.duration = duration;
        return duration;
    }

    public String getGenre() {
        return genre;
    }

    public String setGenre(String genre) {
        this.genre = genre;
        return genre;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songs=" + path +
                ", id=" + id +
                ", songName='" + songName + '\'' +
                ", artistName='" + artistName + '\'' +
                ", duration=" + duration +
                ", genre='" + genre + '\'' +
                '}';
    }
}
