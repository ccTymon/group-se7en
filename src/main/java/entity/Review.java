package entity;

public class Review {

    private String rID;
    private String uID;
    private String movieID;
    private Integer rating;
    private String body;

    /**
     * Creates a new user with the given non-empty name and non-empty password.
     * @param rID the identifying string
     * @param uID the user ID of the reviewer
     * @param movieID the IMDB ID of the associated movie
     * @param rating the user's rating of the movie
     * @param body the text of the review

     */
    public Review(String rID, String uID, String movieID, Integer rating, String body) {

        this.rID = rID;
        this.uID = uID;
        this.movieID = movieID;
        this.rating = rating;
        this.body = body;

    }

    public String getrID() {
        return rID;
    }

    public String getuID() {
        return uID;
    }

    public String getMovieID() {
        return movieID;
    }

    public Integer getRating() {
        return rating;
    }

    public String getBody() {
        return body;
    }
}
