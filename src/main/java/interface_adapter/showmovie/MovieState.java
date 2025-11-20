package interface_adapter.showmovie;

public class MovieState {
    private String movieName;
    private String movieRate;

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public String  getMovieName() {
        return movieName;
    }
    public void setMovieRate(String movieRate) {
        this.movieRate = movieRate;
    }
    public String getMovieRate() {
        return movieRate;
    }
}
