package interface_adapter.loggedin;

import java.awt.image.BufferedImage;

public class LoggedinState {
    private String username = "";
    private String next_watch;
    private BufferedImage next_watch_poster = null;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getNext_watch() { return  this.next_watch; }
    public void setNext_watch(String next_watch) { this.next_watch = next_watch; }

    public BufferedImage getNext_watch_poster() { return this.next_watch_poster; }

    public void setNext_watch_poster(BufferedImage next_watch_poster) {
        this.next_watch_poster = next_watch_poster;
    }
}
