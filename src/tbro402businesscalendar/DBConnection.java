/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tbro402businesscalendar;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    
    private static String driver = "com.mysql.jdbc.Driver";
    private static String db = "U05628";
    private static String url = "jdbc:mysql://52.206.157.109/" + db;
    private static String user = "U05628";
    private static String pass = "53688426632";
    public static Connection conn = null;
    
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception{
        Class.forName(driver);
        conn = DriverManager.getConnection(url,user,pass);
    }
    
    public static void breakConnection() throws ClassNotFoundException, SQLException, Exception
    {
        conn.close();
    }
}

