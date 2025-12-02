package interface_adapter.login;

import java.util.Map;

/**
 * The state for the Login View Model.
 */
public class LoginState {
    private String username = "";
    private String loginError;
    private String password = "";
    private Map<String, String> watchLater;

    public String getUsername() {
        return username;
    }

    public String getLoginError() {
        return loginError;
    }

    public String getPassword() {
        return password;
    }

    public Map<String, String> getWatchLater() { return watchLater; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLoginError(String usernameError) {
        this.loginError = usernameError;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWatchLater(Map<String, String> watchLater) { this.watchLater = watchLater; }

}
