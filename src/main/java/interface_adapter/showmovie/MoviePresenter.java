package interface_adapter.showmovie;

import interface_adapter.ViewManagerModel;
import interface_adapter.loggedin.LoggedInViewModel;
import interface_adapter.loggedin.LoggedinState;
import use_case.showmovie.MovieOutputBoundary;
import use_case.showmovie.MovieOutputData;

import java.util.Map;

public class MoviePresenter implements MovieOutputBoundary {
    private final MovieSearchModel movieSearchModel;
    private final LoggedInViewModel loggedInViewModel;

    public MoviePresenter(ViewManagerModel viewManagerModel, MovieSearchModel movieSearchModel, LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.movieSearchModel = movieSearchModel;
    }

    @Override
    public void prepareSuccessSaveView(MovieOutputData outputData) {
        // Update LoggedIn State (Watch Later)
        Map<String, String> watchLater = outputData.getWatchLaterMap();
        LoggedinState loggedInState = loggedInViewModel.getState();
        loggedInState.setWatchLater(watchLater);
        loggedInState.setUsername(outputData.getUsername());
        loggedInViewModel.setState(loggedInState);
        loggedInViewModel.firePropertyChange();

    }

    @Override
    public void prepareSuccessReviewView(MovieOutputData outputData) {
        // Update Movie State (Reviews List)




        MovieState state = movieSearchModel.getState();
        // state.setReviews(outputData.getReviews()); // IMPLEMENT THIS in MovieState

        movieSearchModel.setState(state);
        movieSearchModel.firePropertyChange();
    }

    @Override
    public void prepareSuccessView(MovieOutputData movie) {

    }
}