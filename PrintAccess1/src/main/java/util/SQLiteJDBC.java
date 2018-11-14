package util;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.sql.*;

public class SQLiteJDBC {

    public static void addUser(String username, String password, String salt) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:printaccess1.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "INSERT INTO Users (username, password, salt) " +
                    "VALUES ('" + username + "','" + password + "','" + salt + "');";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static String getPassword(String username) {
        Connection c = null;
        Statement stmt = null;
        String password = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:printaccess1.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "SELECT password FROM Users " +
                    "WHERE username='" + username + "';";
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                password = rs.getString("password");
            }

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return password;
    }

    public static String getSalt(String username) {
        Connection c = null;
        Statement stmt = null;
        String salt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:printaccess1.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "SELECT salt FROM Users " +
                    "WHERE username='" + username + "';";
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                salt = rs.getString("salt");
            }

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return salt;
    }

    public static Boolean hasAccess(String username, String access) {
        Connection c = null;
        Statement stmt = null;
        boolean hasAccess = false;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:printaccess1.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "SELECT EXISTS(SELECT 1 FROM Permissions WHERE username= '" + username + "' and permission= '"
                     + access + "') AS bool;";
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                hasAccess = rs.getBoolean("bool");
            }

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return hasAccess;
    }
}