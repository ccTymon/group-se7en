package use_case.showmovie;

import java.util.List;

public interface MovieDataAccessInterface {
    // We declare the method here
    void saveUserWatchLater(String username, String movieName, String posterUrl);

    // You'll likely need these for the reviews feature too
    void saveReview(String username, String movieId, int rating, String reviewContent);
    List<String> getReviews(String movieId);
}