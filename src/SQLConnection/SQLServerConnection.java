package SQLConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * SQL Server Connection
 * 
 * @author Trương Đoàn Minh Phúc - CE190744
 */
public class SQLServerConnection {
    private Connection connection = null;

    /**
     * Connect to SQL Server
     * 
     * @param URL: URL of Database
     * @param username: Used to login Database
     * @param password: Used to login Database
     */
    public SQLServerConnection(String URL, String username, String password) {
        try {
            this.connection = DriverManager.getConnection(URL, username, password);
            System.out.println("Connected Successfully");
        } catch (SQLException ex) {
            System.out.println("Connected Failure");
            ex.printStackTrace();
        }
    }
    
    
    /**
     * Executes query of SQL syntax
     * 
     * @param syntax The string SQL syntax
     * @return A ResultSet after executed if existed
     */
    public ResultSet executeQuery(String syntax) {
        ResultSet result = null;
        
        try {
            Statement state = this.connection.createStatement();
            result = state.executeQuery(syntax);
        } catch (SQLException ex) {
            System.out.println("Error in Query Syntax");
            ex.printStackTrace();
        }
        
        return result;
    }
    
    public ResultSet[] executeQuery(String... syntax) {
        ResultSet[] result = null;

        try {
            Statement state = this.connection.createStatement();
            result = new ResultSet[syntax.length];
            for (int i = 0; i < syntax.length; i++) {
                result[i] = state.executeQuery(syntax[i]);
            }
        } catch (SQLException ex) {
            System.out.println("Error in Query Syntax");
            ex.printStackTrace();
        }

        return result;
    }
    
    public void close() {
        try {
            this.connection.close();
        } catch (SQLException ex) {
            System.out.println("Error in Close");
            ex.printStackTrace();
        }
    }
}
