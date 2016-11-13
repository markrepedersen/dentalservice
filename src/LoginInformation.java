/**
 * - * Created by mark on 16-08-31.
 * -
 */
public class LoginInformation {
    private String username;
    private String hashPass;
    private String salt;
    private String type;

    public LoginInformation() {
    }

    //constructor
    public LoginInformation(String username, String hashPass, String salt, String type) {
        this.username = username;
        this.hashPass = hashPass;
        this.salt = salt;
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {

        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getHashPass() {
        return hashPass;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHashPass(String hashPass) {
        this.hashPass = hashPass;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String s) {
        this.salt = s;
    }

}