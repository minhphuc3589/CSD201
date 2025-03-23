/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessObject.File;

import FileConnection.FileReader;
import java.util.UUID;
import lobby.references.data.UserProfile;
import lobby.references.data.UserAccount;

/**
 *
 * @author PhucTDMCE190744
 */
public class UserDAO {

    private final FileReader fileReader;

    public static UserProfile userProfile;
    public static UserAccount userAccount;

    private boolean statusOfLogin;

    /**
     * The constructor.
     */
    public UserDAO() {
        fileReader = new FileReader();

        statusOfLogin = false;
    }

    /**
     * Register the new account.
     *
     * @param userName The user's name is username of account, which will check
     * in database
     * @param userPassword The user's password is password of account, which
     * will check in database
     */
    public void register(String userName, String userPassword) {
        if (fileReader.write("src/data/accounts.txt", userName, userPassword)) {
            System.out.println("Signed up successfully");
        }
    }

    /**
     * Checks the existed account duplicating with the user's account signed in.
     *
     * @param userName The user's name is username of account, which will check
     * in database
     * @param userPassword The user's password is password of account, which
     * will check in database
     */
    public void login(String userName, String userPassword) {
        String existedData = fileReader.read("src/data/accounts.txt");
        String loggedData = userName + ";" + userPassword;

        if (existedData.contains(loggedData)) {
            UUID uuid = UUID.randomUUID();

            this.userAccount = new UserAccount(uuid.toString(), userName, userPassword);

            System.out.println("Signed in successfully");

            statusOfLogin = true;
        }
    }

    /**
     * True if user signed in successfully.
     *
     * @return True if logged in
     */
    public boolean isLogin() {
        return statusOfLogin;
    }

    /**
     * Getter of user's profile.
     *
     * @return The user's profile
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }

    /**
     * Getter of user's account.
     *
     * @return The user's account
     */
    public UserAccount getUserAccount() {
        return userAccount;
    }
}
