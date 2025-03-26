package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BBDD_Connection {
    
    private static final String URL = "jdbc:mysql://be45khmg53yfnc88gkcm-mysql.services.clever-cloud.com:3306/be45khmg53yfnc88gkcm";
    private static final String USER = "u42yrql2u1mpyb4e";
    private static final String PASSWORD = "2GdQrQFW5buCZiQeoUqe";
    
    private static Connection conn = null;
    
    public static Connection conectar() throws SQLException {
        
        if(conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return conn;
        
    }
    
    public  static void closeConnection() {
        try {
            if(conn != null && ! conn.isClosed()) {
                conn.close();
                conn = null;
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
