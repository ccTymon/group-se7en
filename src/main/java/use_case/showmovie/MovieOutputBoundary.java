package use_case.showmovie;

public interface MovieOutputBoundary {
    void prepareSuccessSaveView(MovieOutputData outputData);
    void prepareSuccessReviewView(MovieOutputData outputData);

    void prepareSuccessView(MovieOutputData movie);
}
