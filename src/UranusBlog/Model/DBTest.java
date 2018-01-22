package UranusBlog.Model;

import java.sql.*;
import java.util.Properties;

public class DBTest {
    public static void main(String[] args) {
        // Forces the MySQL driver to load properly. There's nothing much we can do if this fails,
        // so I'm just converting it to an unchecked exception.
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Properties dbProps = new Properties();
        dbProps.setProperty("url", "jdbc:mysql://db.sporadic.nz/xliu617");
//      dbProps.setProperty("useSSL", "false");
        dbProps.setProperty("user", "xliu617");
        dbProps.setProperty("password", "123");
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps.getProperty("user"),dbProps.getProperty("password"))) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet r = stmt.executeQuery("SELECT * FROM account;")){
                    while (r.next()) {
                        int uid= r.getInt(1);
                        String userName = r.getString(3);
                        String firstName = r.getString(4);
                        String lastName= r.getString (5);
                        String middleName= r.getString (6);
                        String email= r.getString (7);
                        String birthDate= r.getString (8);
                        String nation= r.getString(9);
                        int role= r.getInt(11);
                        String roleName;
                        if (role==1){
                            roleName="Admin";
                        }
                        else if (role==2){
                            roleName="User";
                        }
                        System.out.println("User name: "+ userName+ " first name: " +firstName+ " lastName: "+ lastName + " middle name : " + middleName + " email:: "+ email + "dob: "+ birthDate + " nation: " + nation);
                    }

                }

            }
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
