package interface_adapter.showmovie;

import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchViewModel;
import use_case.showmovie.MovieInputBoundary;
import use_case.showmovie.MovieInputData;

public class MovieController {
    private final ViewManagerModel viewManagerModel;
    private final SearchViewModel searchViewModel;
    final MovieInputBoundary movieInteractor;

    public MovieController(ViewManagerModel viewManagerModel,
                           SearchViewModel searchViewModel,
                           MovieInputBoundary movieInteractor) {
        this.viewManagerModel = viewManagerModel;
        this.searchViewModel = searchViewModel;
        this.movieInteractor = movieInteractor;
    }

    public void goBack() {
        searchViewModel.clear();
        viewManagerModel.setState("search");
        viewManagerModel.firePropertyChange();
    }

    public void saveInternal(String username, String movieName, String posterUrl) {
        MovieInputData data = new MovieInputData(username, movieName, posterUrl);
        movieInteractor.execute(data);
    }

    public void leaveReview(String username, String movieId, int rating, String reviewText) {
        MovieInputData data = new MovieInputData(username, movieId, rating, reviewText);
        movieInteractor.execute(data);
    }
}