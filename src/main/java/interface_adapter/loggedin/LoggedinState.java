package interface_adapter.loggedin;

public class LoggedinState {
    private String username = "";

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    private String next_watch = "tt0816692";
    public String getNextWatch() {
        return next_watch;
    }
    public void setNextWatch(String nextWatch) {
        this.next_watch = next_watch;
    }

}
