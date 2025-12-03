package use_case.showmovie;

import java.util.List;
import java.util.Map;

public class MovieOutputData {
    private final String movieName;
    private final List<String> reviews;
    private final boolean useCaseSuccess;
    private final Map<String, String> watchLaterMap;
    private final String username;

    public MovieOutputData(String movieName, List<String> reviews,
                           Map<String, String> watchLaterMap, String username, boolean useCaseSuccess) {
        this.movieName = movieName;
        this.reviews = reviews;
        this.watchLaterMap = watchLaterMap;
        this.username = username;
        this.useCaseSuccess = useCaseSuccess;
    }



    public String getMovieName() { return movieName; }
    public Map<String, String> getWatchLaterMap() {
        System.out.println(watchLaterMap);
        return watchLaterMap; }
    public String getUsername() { return username; }
    public List<String> getReviews() { return reviews; }
    public boolean getUseCaseSuccess() { return useCaseSuccess; }
}