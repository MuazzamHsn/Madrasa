package com.madrasa;

import java.sql.*;

public class DBcon {
    Connection conn;
    Statement stmnt,scrollstmnt;
    PreparedStatement ps;

    public DBcon(){
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Madrasa","root","Muazzam");

            stmnt = conn.createStatement();
            scrollstmnt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

        }
        
        catch (Exception e) {
            System.out.println(e);
        }

    }


    public PreparedStatement prepareStatement(String query) throws SQLException {
        ps = conn.prepareStatement(query);
        return ps;
    }
}
