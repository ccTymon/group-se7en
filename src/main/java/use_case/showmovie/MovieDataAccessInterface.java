package use_case.showmovie;

import entity.Review;

import java.util.List;

public interface MovieDataAccessInterface {
    void save(Review review);

    List<String> getReviews(String movieId);
}