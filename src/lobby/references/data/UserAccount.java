package lobby.references.data;

/**
 * CSD201 - User Account extends the basis of User
 *
 * @author Trương Đoàn Minh Phúc - CE190744
 */
public class UserAccount extends User {

    private String playerName; // The name will be display in the game

    /**
     * The constructor sets the player's information
     *
     *
     * @param playerName The name will be display in the game
     * @param UUID The UUID of player
     * @param userName The user's name is username of account, which will check
     * in database
     */
    public UserAccount(String playerName, String UUID, String userName) {
        super(UUID, userName);
        this.playerName = playerName;
    }

    /**
     * Getter of player's name
     *
     * @return The string player's name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Setter of player's name
     *
     * @param playerName A new string player's name
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
