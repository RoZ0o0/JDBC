package JavaFX;

import java.sql.*;

public class Connect {
public Connection connection;
    public Connection getConnection(){

        String url = "jdbc:mysql://localhost:3306/projekt";
        String user = "root";
        String password = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,user,password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}

