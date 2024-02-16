package javadatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcConnection {
    public static String URL="jdbc:mysql://localhost:3306/java_nccs";
    public static String USERNAME="root";
    public static String PASSWORD="#20021024#";
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.getConnection(URL, USERNAME, PASSWORD);
       Statement statement = connection.createStatement(); 
       statement.execute(sql:"Insert into students(id,name,phone)values(1,'Ram','982033456')");
    }
}