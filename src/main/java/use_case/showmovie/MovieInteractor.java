package use_case.showmovie;

import entity.Review;
import entity.ReviewFactory;
import interface_adapter.loggedin.LoggedInViewModel;
import interface_adapter.loggedin.LoggedinState;

import java.util.Map;


public abstract class MovieInteractor implements MovieInputBoundary {
    final MovieOutputBoundary moviePresenter;
    final MovieUserDataAccessInterface fileUserDataAccessObject;
    final ReviewDataAccessInterface fileReviewDataAccessObject;
    final MovieDataAccessInterface fileMovieDataAccessObject;
    private final ReviewFactory reviewFactory;
    private final LoggedInViewModel loggedInViewModel;

    protected MovieInteractor(MovieOutputBoundary movieOutputBoundary,
                              MovieUserDataAccessInterface fileUserDataAccessObject,
                              ReviewDataAccessInterface fileReviewDataAccessObject, MovieDataAccessInterface fileMovieDataAccessObject,
                              ReviewFactory reviewFactory, LoggedInViewModel loggedInViewModel) {
        this.moviePresenter = movieOutputBoundary;
        this.fileUserDataAccessObject = fileUserDataAccessObject;
        this.fileReviewDataAccessObject = fileReviewDataAccessObject;
        this.fileMovieDataAccessObject = fileMovieDataAccessObject;
        this.reviewFactory = reviewFactory;
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void execute(MovieInputData inputData) {
        if (inputData.getAction().equals("save")) {
            // Save to JSON via DAO
            fileUserDataAccessObject.saveWatchLater(
                    inputData.getUsername(),
                    inputData.getMovieName(),
                    inputData.getPosterUrl()
            );
            Map<String, String> watchLater = fileUserDataAccessObject.watchLater(inputData.getUsername());
            LoggedinState loggedInState = loggedInViewModel.getState();
            loggedInState.setWatchLater(watchLater);
            loggedInState.setUsername(inputData.getUsername());
            loggedInViewModel.setState(loggedInState);
            loggedInViewModel.firePropertyChange();


            // Prepare Success View
            MovieOutputData outputData = new MovieOutputData(
                    inputData.getMovieName(),
                    null,
                    true
            );
            moviePresenter.prepareSuccessSaveView(outputData);

        } else if (inputData.getAction().equals("review")) {
            String rID = fileReviewDataAccessObject.getMasterID();
            // fileReviewDataAccessObject.setMasterID();
            Review review = reviewFactory.create(
                    rID,
                    inputData.getUsername(),
                    inputData.getMovieId(),
                    inputData.getRating(),
                    inputData.getReviewContent()
                    );
            fileReviewDataAccessObject.save(review);
            fileMovieDataAccessObject.save(review);

        }

        }
    }
