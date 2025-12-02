package use_case.showmovie;

import entity.Review;
import entity.ReviewFactory;


public abstract class MovieInteractor implements MovieInputBoundary {
    final MovieOutputBoundary moviePresenter;
    final MovieUserDataAccessInterface fileUserDataAccessObject;
    final ReviewDataAccessInterface fileReviewDataAccessObject;
    private final ReviewFactory reviewFactory;

    protected MovieInteractor(MovieOutputBoundary movieOutputBoundary,
                              MovieUserDataAccessInterface fileUserDataAccessObject,
                              ReviewDataAccessInterface fileReviewDataAccessObject,
                              ReviewFactory reviewFactory) {
        this.moviePresenter = movieOutputBoundary;
        this.fileUserDataAccessObject = fileUserDataAccessObject;
        this.fileReviewDataAccessObject = fileReviewDataAccessObject;
        this.reviewFactory = reviewFactory;
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

            // Prepare Success View
            MovieOutputData outputData = new MovieOutputData(
                    inputData.getMovieName(),
                    null,
                    true
            );
            moviePresenter.prepareSuccessSaveView(outputData);

        } else if (inputData.getAction().equals("review")) {
            String rID = fileReviewDataAccessObject.getMasterID();
            Review review = reviewFactory.create(
                    rID,
                    inputData.getUsername(),
                    inputData.getMovieId(),
                    inputData.getRating(),
                    inputData.getReviewContent()
                    );
            fileReviewDataAccessObject.save(review);


        }

        }
    }
