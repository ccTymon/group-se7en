package use_case.login;

/**
 * The Input Data for the Login Use Case.
 */
public class LoginInputData {

    private final String username;
    private final String password;
    private final String nextWatch;

    public LoginInputData(String username, String password) {
        this.username = username;
        this.password = password;
        this.nextWatch = "tt0816692";
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    String getNextWatch() {return nextWatch; }

}
