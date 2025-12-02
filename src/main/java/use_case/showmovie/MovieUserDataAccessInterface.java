package use_case.showmovie;

import java.util.Map;

public interface MovieUserDataAccessInterface {
    void saveWatchLater(String user, String movie, String url);

    String getCurrentUsername();
    Map<String,String> watchLater(String username);

}
