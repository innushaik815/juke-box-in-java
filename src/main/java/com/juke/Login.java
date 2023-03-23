package com.juke;


import java.sql.*;

public class Login {

    public static int SINO =0 ;
    static Connection con;
    Statement stmt;
    static PreparedStatement prstm;
    static ResultSet rs;
    public static  String useRname;
    static DataBase d=new DataBase();

    public static boolean checkUser(String userName, String password) {
        useRname=userName;

        try {
            con = d.getConnection();
            prstm = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            prstm.setString(1, userName);
            prstm.setString(2, password);
            rs = prstm.executeQuery();
            if (rs.next()) {
                System.out.println("login successful!");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void createUser(String username, String password){
        try {
            con = d.getConnection();
            prstm = con.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            prstm.setString(1, username);
            prstm.setString(2, password);
            prstm.executeUpdate();
            System.out.println("Account created");
            System.out.println("Thanks for registering with us!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
