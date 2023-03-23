package com.juke;

import java.sql.*;
import java.util.List;

public class PlayListName extends Playlist{


    public PlayListName() {
    }

    /*
        public void insertSongIntoPlaylist(String songName, String playlistName) throws SQLException {
            d.getConnection();

            Login login = new Login();
            String user = login.useRname;
            if (user == null) {
                System.out.println("No user is currently logged in");
                return;
            }
            int userId = login.SINO;
            if (userId == -1) {
                System.out.println("No user with the username: " + user + " found");
                return;
            }
            int playlistId = getPlaylistIdByName(playlistName);
            if (playlistId == -1) {
                System.out.println("No playlist with the name: " + playlistName + " found for the user");
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
    */
    public int insertPlaylistName(String playlistName, int userId) throws SQLException {
        int playlistId = 0;
        // SQL query to insert a new playlist name and user ID into the playlistName table
        String insertPlaylistNameSql = "INSERT INTO playlistNames (playlistName, userId) VALUES (?,?)";

        // Establish a connection to the database
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertPlaylistNameSql, Statement.RETURN_GENERATED_KEYS)) {

            // Set the values for the placeholders in the SQL query
            preparedStatement.setString(1, playlistName);
            preparedStatement.setInt(2, userId);

            // Execute the SQL query
            preparedStatement.executeUpdate();

            // Get the generated playlist ID
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                playlistId = resultSet.getInt(1);
            }
        }

        // Return the generated playlist ID
        return playlistId;
    }

    public int getPlaylistIdByName(String playlistName) throws SQLException {
        int playlistId = -1;
        d.getConnection();
        Login login = new Login();
        String user = login.useRname;
        if (user == null) {
            System.out.println("No user is currently logged in");
            return playlistId;
        }

        Connection conn = getConnection();
        try {
            String getPlaylistIdSql = "SELECT playlistNameId FROM playlistNames WHERE playlistName = ? AND userId = (SELECT userId FROM users WHERE userName = ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(getPlaylistIdSql)) {
                pstmt.setString(1, playlistName);
                pstmt.setString(2, user);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        playlistId = rs.getInt("playlistNameId");
                    }
                }
            }
        } finally {
            conn.close();
        }
        return playlistId;
    }
   /*public void createPlaylistName(String playlistName) throws SQLException {
       Login login = new Login();
       int userId = login.SINO;
       if (userId == -1) {
           System.out.println("No user is currently logged in");
           return;
       }
       Connection conn = getConnection();
       try {
           conn.setAutoCommit(false);
           String checkPlaylistNameSql = "SELECT playlistName FROM playlistNames WHERE playlistName=? and userId=?";
           try (PreparedStatement pstmt = conn.prepareStatement(checkPlaylistNameSql)) {
               pstmt.setString(1, playlistName);
               pstmt.setInt(2, userId);
               ResultSet rs = pstmt.executeQuery();
               if (rs.next()) {
                   System.out.println("Playlist name already exists for the current user. Please choose a different name.");
                   return;
               }
               String insertPlaylistNameSql = "INSERT INTO playlistNames (playlistName, userId) VALUES (?, ?)";
               try (PreparedStatement insertPstmt = conn.prepareStatement(insertPlaylistNameSql)) {
                   insertPstmt.setString(1, playlistName);
                   insertPstmt.setInt(2, userId);
                   insertPstmt.executeUpdate();
                   conn.commit();
                   System.out.println("Playlist name added successfully");
               }
           }
       } catch (SQLException e) {
           System.out.println("Error adding playlist name: " + e.getMessage());
           conn.rollback();
       } finally {
           conn.setAutoCommit(true);
       }
   }*/
   public void createPlaylistName(String playListName) throws SQLException {
       Login login = new Login();
       int userId = login.SINO;
       if (userId == -1) {
           System.out.println("No user is currently logged in");
           return;
       }
       Connection conn = getConnection();
       try {
           conn.setAutoCommit(false);
           String insertPlaylistNameSql = "INSERT INTO playlistNames (playlistName, userId) VALUES (?, ?)";
           try (PreparedStatement insertPstmt = conn.prepareStatement(insertPlaylistNameSql)) {
               insertPstmt.setString(1, playListName);
               insertPstmt.setInt(2, userId);
               insertPstmt.executeUpdate();
               conn.commit();
               System.out.println("Playlist name added successfully");
           }
       } catch (SQLException e) {
           System.out.println("Error adding playlist name: " + e.getMessage());
           conn.rollback();
       } finally {
           conn.setAutoCommit(true);
       }
   }



    public ResultSet displayPlaylistNames() {
        Login l = new Login();
        try {
            Connection con = getConnection();
            int userId = l.SINO;
            if (userId == -1) {
                System.out.println("No user is currently logged in");
                return null;
            }
            String sql = "SELECT playlistName FROM playlistNames " +
                    "WHERE userId = ?";

            PreparedStatement prstmt = con.prepareStatement(sql);
            prstmt.setInt(1, userId);

            ResultSet rs = prstmt.executeQuery();
            System.out.println("-----------------------------");
            System.out.println("Playlist Name");
            System.out.println("-----------------------------");
            while (rs.next()) {
                System.out.println(rs.getString("playlistName"));
            }
            System.out.println("-----------------------------");

            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
