package use_case.showmovie;

import java.util.List;

public abstract class ReviewInteractor {
    private MovieDataAccessInterface movieDataAccessObject;
    protected ReviewInteractor(MovieDataAccessInterface movieDataAccessObject){
        this.movieDataAccessObject = movieDataAccessObject;
    }

    public List<String> getComments(String movieID){
        return movieDataAccessObject.getComments(movieID);
    }
}
