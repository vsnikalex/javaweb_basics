package accounts;


public class UserProfile {
    private String login;
    private String password;
    private String email;

    public UserProfile(String login, String pass, String email) {
        this.login = login;
        this.password = pass;
        this.email = email;
    }

    public UserProfile(String login) {
        this.login = login;
        this.password = login;
        this.email = login;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return password;
    }

    public void setPass(String pwd) { this.password = pwd; }

    public String getEmail() {
        return email;
    }
}
