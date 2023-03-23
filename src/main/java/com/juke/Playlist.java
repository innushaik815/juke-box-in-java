package com.juke;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
public class Playlist extends Juke {
    private List<Song>jukeSong;
    Date date = new Date();

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


    public void insertSongIntoPlaylist(String songName) throws SQLException {
        d.getConnection();

        Login login = new Login();
        String user = login.useRname;
        if (user == null) {
            System.out.println("No user is currently logged in");
            return;
        }
        String artist = getArtistBySongName(songName);
        if (artist == null) {
            System.out.println("Artist not found for the song");
            return;
        }
        String path = getPathBySongName(songName);
        if (path == null) {
            System.out.println("Path not found for the song");
            return;
        }
        java.util.Date today = new java.util.Date();
        long time = today.getTime();
        java.sql.Date sqlDate = new java.sql.Date(time);

        Connection conn = getConnection();
        try {
            conn.setAutoCommit(false);
            String insertPlaylistSql = "INSERT INTO playlists (songName, artist, user, path, date_created) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertPlaylistSql)) {
                pstmt.setString(1, songName);
                pstmt.setString(2, artist);
                pstmt.setString(3, user);
                pstmt.setString(4, path);
                pstmt.setDate(5, sqlDate);
                pstmt.executeUpdate();
                conn.commit();
                System.out.println("Song added to playlist successfully");
            }
        } catch (SQLException e) {
            System.out.println("Error adding song to playlist: " + e.getMessage());
            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }
    public String getArtistBySongName(String songName) throws SQLException {
        String artist = null;
        String selectArtistSql = "SELECT artist FROM songs WHERE title = ?";
        Connection conn = getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(selectArtistSql)) {
            pstmt.setString(1, songName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                artist = rs.getString("artist");
            }
        } catch (SQLException e) {
            System.out.println("Error getting artist for song: " + e.getMessage());
        } finally {
            conn.close();
        }
        return artist;
    }
    public String getPathBySongName(String songName) throws SQLException {
        String path = null;
        Connection conn = d.getConnection();
        String selectSql = "SELECT path FROM songs WHERE title = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
            pstmt.setString(1, songName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    path = rs.getString("path");
                }
            }
        }
        conn.close();
        return path;
    }


}
