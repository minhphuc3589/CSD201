/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lobby.references.data;

/**
 * CSD201 - User
 * 
 * @author PhucTDMCE190744
 */
public class User {

    private final String UUID; // The UUID of player
    private final String userName; // The username is username of account, which will check in database

    /**
     * The constructor
     *
     * @param UUID The UUID of player
     * @param userName The user's name is username of account, which will check
     * in database
     */
    public User(String UUID, String userName) {
        this.UUID = UUID;
        this.userName = userName;
    }

    /**
     * Getter of player's UUID
     *
     * @return The string UUID
     */
    public String getUUID() {
        return UUID;
    }

    /**
     * Getter of user's name
     *
     * @return The string user's name
     */
    public String getUserName() {
        return userName;
    }

}
