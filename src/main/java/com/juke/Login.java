package com.juke;


import java.sql.*;

public class Login {

    static Connection con;
    Statement stmt;
    static PreparedStatement prstm;
    static ResultSet rs;
    static Login login = new Login();


    public static boolean checkValidation(String userName, String password) {
        DataBase d=new DataBase();
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

}
