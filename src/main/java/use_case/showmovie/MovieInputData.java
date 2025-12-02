package use_case.showmovie;
import java.awt.image.BufferedImage;

    public class MovieInputData {
        private String username;
        private String movieName;
        private String movieId;
        private String posterUrl;
        private int rating;
        private String reviewContent;
        private String action; // "save" or "review"

        public MovieInputData(String username, String movieName, String posterUrl) {
            this.username = username;
            this.movieName = movieName;
            this.posterUrl = posterUrl;
            this.action = "save";
        }

        // overload with review stuff
        public MovieInputData(String username, String movieId, int rating, String reviewContent) {
            this.username = username;
            this.movieId = movieId;
            this.rating = rating;
            this.reviewContent = reviewContent;
            this.action = "review";
        }

        // Getters
        public String getUsername() { return username; }
        public String getMovieName() { return movieName; }
        public String getMovieId() { return movieId; }
        public String getPosterUrl() { return posterUrl; }
        public int getRating() { return rating; }
        public String getReviewContent() { return reviewContent; }
        public String getAction() { return action; }
    }

