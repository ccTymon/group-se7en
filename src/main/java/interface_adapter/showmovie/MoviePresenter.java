package interface_adapter.showmovie;

import interface_adapter.ViewManagerModel;
import interface_adapter.loggedin.LoggedInViewModel;
import use_case.showmovie.MovieOutputBoundary;
import use_case.showmovie.MovieOutputData;

public class MoviePresenter implements MovieOutputBoundary {
    private final MovieSearchModel movieSearchModel;

    public MoviePresenter(ViewManagerModel viewManagerModel, MovieSearchModel movieSearchModel) {

        this.movieSearchModel = movieSearchModel;
    }

    @Override
    public void prepareSuccessSaveView(MovieOutputData outputData) {
        // Update LoggedIn State (Watch Later)
        //LoggedinState state = loggedInViewModel.getState();
        // state.setNext_watch(outputData.getMovieName());

        //loggedInViewModel.setState(state);
        //loggedInViewModel.firePropertyChange();

    }

    @Override
    public void prepareSuccessReviewView(MovieOutputData outputData) {
        // Update Movie State (Reviews List)

        // MATT AND MUSTAFA TO IMPLEMENT


        MovieState state = movieSearchModel.getState();
        // state.setReviews(outputData.getReviews()); // IMPLEMENT THIS in MovieState

        movieSearchModel.setState(state);
        movieSearchModel.firePropertyChange();
    }
}