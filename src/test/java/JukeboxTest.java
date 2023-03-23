import com.juke.DataBase;
import com.juke.Login;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class JukeboxTest {
    DataBase data=new DataBase();
    @Test
    public void testCheckValidationSuccess() {
        String userName = "innushaik815";
        String password = "123";
        boolean expectedResult = true;

        boolean result = Login.checkUser(userName, password);

        assertEquals(expectedResult, result);
    }
    @Test
    public void testCheckValidationSuccess1() {
        String userName = "1";
        String password = "123";
        boolean expectedResult = false;

        boolean result = Login.checkUser(userName, password);

        assertEquals(expectedResult, result);
    }
    public boolean checkSongExistsForArtist(String artist) {
        try {
            PreparedStatement prstm = null;
            Connection con= data.getConnection();
            String sql="select * from songs where artist like ?;";
            prstm =con.prepareStatement(sql);
            prstm.setString(1,"%"+artist+"%");
            ResultSet rs=prstm.executeQuery();
            if (rs.next()) {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean checkSongExistsForGenre(String genre) {
        try {
            PreparedStatement prstm = null;
            Connection con= data.getConnection();
            String sql="select * from songs where genre like ?;";
            prstm =con.prepareStatement(sql);
            prstm.setString(1,"%"+genre+"%");
            ResultSet rs=prstm.executeQuery();
            if (rs.next()) {
                return true;
            }else if (!rs.next()) {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean checkSongOfSearched(String songName) {
        try {
            PreparedStatement prstm = null;
            Connection con= data.getConnection();
            String sql="select * from songs where title like ?;";
            prstm =con.prepareStatement(sql);
            prstm.setString(1,"%"+songName+"%");
            ResultSet rs=prstm.executeQuery();
            if (rs.next()) {
                return true;
            }else if (!rs.next()) {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    @Test
    public void testSearchByArtist() {
        data.searchByArtist("arjit singh");
        assertTrue(checkSongExistsForArtist("arjit singh"));
        assertTrue(checkSongExistsForArtist("tesher"));
    }
    @Test
    public void testSearchByArtist1() {
        data.getConnection();
        data.searchByArtist("teash");
        assertFalse(checkSongExistsForArtist("teash"));
    }
    @Test
    public void testSearchByGenre() throws Exception {
        data.getConnection();
        data.sortByGenre("love");
        assertTrue(checkSongExistsForGenre("sad"));
        assertTrue(checkSongExistsForGenre("romance"));
    }
    @Test
    public void testSearchByGenre1() throws Exception {
        data.getConnection();
        data.sortByGenre("jazz");
        assertFalse(checkSongExistsForGenre("rock"));
    }
    @Test
    public void testSearchAudio() {
        data.getConnection();
        data.searchAudioTest("dard");
        assertTrue(checkSongOfSearched("dard"));
    }
    @Test
    public void testSearchAudio1() {
        data.getConnection();
        data.searchAudioTest("desi");
        assertFalse(checkSongOfSearched("desi"));
    }
}