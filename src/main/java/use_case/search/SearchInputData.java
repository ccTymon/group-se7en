package use_case.search;

public class SearchInputData {
    private String movie;

    public SearchInputData(String movie) {
        this.movie = movie;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }
}
