package use_case.showmovie;

import java.util.List;

public interface MovieDataAccessInterface {

    void saveUserWatchLater(String username, String movieName, String posterUrl);

    void saveReview(String username, String movieId, int rating, String reviewContent);
    List<String> getReviews(String movieId);

    boolean existsByName(String identifier);

}