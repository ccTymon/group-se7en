package use_case.showmovie;

import java.util.List;

public class MovieOutputData {
    private final String movieName;
    private final List<String> reviews;
    private final boolean useCaseSuccess;

    public MovieOutputData(String movieName, List<String> reviews, boolean useCaseSuccess) {
        this.movieName = movieName;
        this.reviews = reviews;
        this.useCaseSuccess = useCaseSuccess;
    }

    public String getMovieName() { return movieName; }
    public List<String> getReviews() { return reviews; }
}