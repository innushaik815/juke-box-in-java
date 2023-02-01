package com.juke;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Playlist extends Juke {
    private List<Song>jukeSong;

    public Playlist(List<Song> path, int id, String songName, String artistName, double duration, String genre, List<Song> songs, List<Song> jukeSong) {
        super(path, id, songName, artistName, duration, genre, songs);
        this.jukeSong = jukeSong;
    }

    public Playlist(List<Song> songs, List<Song> jukeSong) {
        super(songs);
        this.jukeSong = jukeSong;
    }

    public Playlist(List<Song> jukeSong) {
        this.jukeSong = jukeSong;
    }

    public Playlist() {

    }

    public List<Song> getJukeSong() {
        return jukeSong;
    }

    public void setJukeSong(List<Song> jukeSong) {
        this.jukeSong = jukeSong;
    }




    //create playlist
    //show playlist
    //deleted song from playlist

}
