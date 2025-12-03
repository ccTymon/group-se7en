package use_case.showmovie;

import data_access.FileReviewDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.ReviewFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieInteractorTest {

    @Test
    void successTest() {
        // Arrange
        MovieInputData inputData = new MovieInputData("TestUser", "Inception", "poster_url_stub");


        // 1. Create a DAO that successfully returns the movie
        MovieUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject() {

            @Override
            public boolean existsByName(String identifier) {
                return true;
            }
        };
        // 2. Create a Presenter that asserts success
        final MovieOutputBoundary successPresenter = new MovieOutputBoundary() {
            @Override
            public void prepareSuccessSaveView(MovieOutputData movie) {
                assertEquals("TestUser", movie.getUsername());
                assertEquals("Inception", movie.getMovieName());
            }

            @Override
            public void prepareSuccessReviewView(MovieOutputData outputData) {
            }

            @Override
            public void prepareSuccessView(MovieOutputData movie) {
            }

        };

        // Act
        MovieInputBoundary interactor = new MovieInteractor(successPresenter, userRepository,
                null, new ReviewFactory()) {
        };

        interactor.execute(inputData);
    }

    ;

}





