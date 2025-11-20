package interface_adapter.search;

public class SearchState {
    private String moviename = "";
    private String searchError;

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setSearchError(String searchError) {
        this.searchError = searchError;
    }

    public String getSearchError() {
        return searchError;
    }
}
