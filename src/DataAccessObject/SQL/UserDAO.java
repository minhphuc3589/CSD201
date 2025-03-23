/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessObject.SQL;

import SQLConnection.SQLServerConnection;
import lobby.references.data.UserAccount;

/**
 *
 * @author Trương Đoàn Minh Phúc - CE190744
 */
public class UserDAO extends SQLServerConnection {
    private String URL;
    private String username;
    private String password;

    private UserAccount user;
    
    public UserDAO(String URL, String username, String password) {
        super(URL, username, password);
    }

    public void checkedLogin() {
    }
    
    public void setUser(UserAccount user) {
        this.user = user;
    }
    
    public UserAccount getUser() {
        return user;
    }
    
}
