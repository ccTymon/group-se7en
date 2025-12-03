package app;

import data_access.FileMovieDataAccessObject;
import data_access.FileReviewDataAccessObject;
import data_access.UserDataAccessInterface;
import entity.ReviewFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.loggedin.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchPresenter;
import interface_adapter.search.SearchViewModel;
import interface_adapter.showmovie.MovieController;
import interface_adapter.showmovie.MoviePresenter;
import interface_adapter.showmovie.MovieSearchModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.search.SearchInputBoundary;
import use_case.search.SearchInteractor;
import use_case.search.SearchOutputBoundary;
import use_case.showmovie.MovieInputBoundary;
import use_case.showmovie.MovieInputData;
import use_case.showmovie.MovieInteractor;
import use_case.showmovie.MovieOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final UserFactory userFactory = new UserFactory();
    static final ReviewFactory reviewFactory = new ReviewFactory();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // set which data access implementation to use, can be any
    // of the classes from the data_access package

    // DAO version using local file storage
    final UserDataAccessInterface userDataAccessObject = new UserDataAccessInterface("users.csv", userFactory);

    // DAO for local review storage
    public static FileReviewDataAccessObject fileReviewDataAccessObject = new FileReviewDataAccessObject(
            "reviews.json", reviewFactory);

    public static FileMovieDataAccessObject fileMovieDataAccessObject = new FileMovieDataAccessObject("movieDB.json");

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private SearchViewModel  searchViewModel;
    private MovieSearchModel  movieSearchModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
    private SearchView searchView;
    private MovieView movieView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    public AppBuilder addSearchView() {
        searchViewModel = new SearchViewModel();
        searchView = new SearchView(searchViewModel, viewManagerModel);
        cardPanel.add(searchView, searchView.getViewName());
        return this;
    }

    public AppBuilder addMovieView() throws IOException {
        movieSearchModel = new MovieSearchModel();
        movieView = new MovieView(movieSearchModel, loggedInViewModel);
        cardPanel.add(movieView, movieView.getViewName());
        return this;
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        SearchViewModel SearchViewModel = new SearchViewModel();
        loggedInView = new LoggedInView(viewManagerModel, loggedInViewModel, SearchViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addSearchUseCase() {
        final SearchOutputBoundary searchOutputBoundary = new SearchPresenter(searchViewModel,
                movieSearchModel, viewManagerModel);
        final SearchInputBoundary searchInteractor = new SearchInteractor(searchOutputBoundary);

        SearchController searchController = new SearchController(searchInteractor);

        // Existing line for SearchView
        searchView.setSearchController(searchController);

        // NEW LINE: Set the controller for LoggedInView as well
        loggedInView.setSearchController(searchController);

        return this;
    }

    public AppBuilder addMovieUseCase() {
        final MovieOutputBoundary movieOutputBoundary = new MoviePresenter(viewManagerModel, movieSearchModel);

        // 2. Create the Interactor
        // We inject the DAOs (for saving data) and the Presenter (for outputting data).
        final MovieInputBoundary movieInteractor = new MovieInteractor(
                movieOutputBoundary,
                userDataAccessObject,
                fileReviewDataAccessObject,
                fileMovieDataAccessObject,
                reviewFactory,
                loggedInViewModel
        ) {
            @Override
            public void executeSave(MovieInputData movieInputData) {

            }
        };
        MovieController movieController = new MovieController(
                viewManagerModel,
                searchViewModel,
                movieInteractor
        );
        movieView.setMovieController(movieController);
        return this;
    }

    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);

        final LogoutInputBoundary logoutInteractor =
                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor);
        loggedInView.setLogoutController(logoutController);
        return this;
    }


    public JFrame build() {
        final JFrame application = new JFrame("Movie Search");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }


}
