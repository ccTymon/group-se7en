package use_case.login;

import java.util.Map;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;
    private final Map<String, String> watchLater;
    private final boolean useCaseFailed;

    public LoginOutputData(String username, Map<String,String> watchLater, boolean useCaseFailed) {
        this.username = username;
        this.watchLater = watchLater;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }
    public Map<String, String> getWatchLater() { return watchLater; }
    public boolean isUseCaseFailed() { return useCaseFailed; }



}
