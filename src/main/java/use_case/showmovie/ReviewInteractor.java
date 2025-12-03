package use_case.showmovie;

import entity.ReviewFactory;

public abstract class ReviewInteractor {
    private MovieDataAccessInterface movieDataAccessObject;
    private ReviewFactory reviewFactory;
    protected ReviewInteractor(MovieDataAccessInterface movieDataAccessObject){
        this.movieDataAccessObject = movieDataAccessObject;
    }
}
