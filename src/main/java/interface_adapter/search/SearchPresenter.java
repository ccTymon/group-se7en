package interface_adapter.search;

import interface_adapter.ViewManagerModel;
import interface_adapter.showmovie.MovieSearchModel;
import interface_adapter.showmovie.MovieState;
import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

public class SearchPresenter implements SearchOutputBoundary {
    private final SearchViewModel searchViewModel;
    private final MovieSearchModel movieSearchModel;
    private final ViewManagerModel viewManagerModel;

    public SearchPresenter(SearchViewModel searchViewModel,  MovieSearchModel movieSearchModel, ViewManagerModel viewManagerModel) {
        this.searchViewModel = searchViewModel;
        this.movieSearchModel = movieSearchModel;
        this.viewManagerModel = viewManagerModel;
    }

    public void prepareSuccessView(SearchOutputData outputData) {
        final MovieState movieState = new MovieState();
        movieState.setMovieName(outputData.getMovieName());
        movieState.setMovieRate(outputData.getRate());
        movieState.setMovieIcon(outputData.getIcon());
        movieState.setMoviePlot(outputData.getPlot());
        movieState.setMovieId(outputData.getId());

        movieSearchModel.setState(movieState);
        movieSearchModel.firePropertyChange();

        searchViewModel.setState(new SearchState());

        viewManagerModel.setState(movieSearchModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    public void prepareFailureView(String error) {
        final SearchState searchState = searchViewModel.getState();
        searchState.setSearchError(error);
        searchViewModel.firePropertyChange();
    }

    public void switchToLoggedInView() {
        viewManagerModel.setState("logged in");
        viewManagerModel.firePropertyChange();
    }
}