package interface_adapter.loggedin;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class LoggedinState {
    private String username = "";
    private Map<String, String> watchLater = new HashMap<>();
//    private BufferedImage next_watch_poster = null;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, String> getWatchLater() {
        return watchLater;
    }

    public void setWatchLater(Map<String, String> watchLater) {
        this.watchLater = watchLater;
    }

//    public BufferedImage getNext_watch_poster() { return this.next_watch_poster; }
//
//    public void setNext_watch_poster(BufferedImage next_watch_poster) {
//        this.next_watch_poster = next_watch_poster;
//    }
}
