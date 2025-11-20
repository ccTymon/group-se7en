package interface_adapter.showmovie;

public class MovieState {
    private String movieName;
    private String movieRate;
    private String movieIcon;
    private String moviePlot;
    private String movieId;

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
    public void setMovieIcon(String movieIcon) { this.movieIcon = movieIcon; }
    public String getMovieIcon() { return movieIcon; }
    public void setMoviePlot(String moviePlot) { this.moviePlot = moviePlot; }
    public String getMoviePlot() { return moviePlot; }
    public void setMovieId(String movieId) { this.movieId = movieId; }
    public String getMovieId() { return movieId; }
}