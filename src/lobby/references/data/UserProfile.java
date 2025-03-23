package lobby.references.data;

/**
 * CSD201 - User Profile extends the basis of User
 * 
 * @author PhucTDMCE190744
 */
public class UserProfile extends User {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    /**
     * The constructor
     *
     * @param firstName The first name of user
     * @param lastName The last name of user
     * @param email The email of user
     * @param phoneNumber The phone number of user
     * @param UUID The UUID of player
     * @param userName The user's name is username of account, which will check
     * in database
     */
    public UserProfile(String firstName, String lastName, String email, String phoneNumber, String UUID, String userName) {
        super(UUID, userName);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
