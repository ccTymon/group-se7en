package use_case.showmovie;

import data_access.FileUserDataAccessObject;


public abstract class MovieInteractor implements MovieInputBoundary {
    final MovieOutputBoundary moviePresenter;
    final FileUserDataAccessObject fileUserDataAccessObject;

    public MovieInteractor(MovieOutputBoundary movieOutputBoundary, FileUserDataAccessObject fileUserDataAccessObject) {
        this.moviePresenter = movieOutputBoundary;
        this.fileUserDataAccessObject = fileUserDataAccessObject;
    }

    @Override
    public void execute(MovieInputData inputData) {
        if (inputData.getAction().equals("save")) {
            // Save to JSON via DAO
            fileUserDataAccessObject.saveUserWatchLater(
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
            fileUserDataAccessObject.saveUserReview(
                    inputData.getUsername(),
                    inputData.getMovieId(),
                    inputData.getRating(),
                    inputData.getReviewContent());


            fileUserDataAccessObject.getUserReview(
                    // review object
            );

        }

        }
    }
