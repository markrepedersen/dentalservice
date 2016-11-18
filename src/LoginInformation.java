/**
 * - * Created by mark on 16-08-31.
 * -
 */
public class LoginInformation {
    private String username;
    private String hashPass;
    private String salt;
    private String type;
    private int    eid;

    public LoginInformation() {
    }

    //constructor
    public LoginInformation(String username, String hashPass, String salt, String type, int eid) {
        this.username = username;
        this.hashPass = hashPass;
        this.salt = salt;
        this.type = type;
        this.eid = eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public int getEid() {

        return eid;
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