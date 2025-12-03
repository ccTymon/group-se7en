package data_access;

import entity.User;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.showmovie.MovieUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * In-memory implementation of the DAO for storing user data. This implementation does
 * NOT persist data between runs of the program.
 */
public class InMemoryUserDataAccessObject implements SignupUserDataAccessInterface,
                                                     LoginUserDataAccessInterface, LogoutUserDataAccessInterface, MovieUserDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();

    private String currentUsername;

    @Override
    public boolean existsByName(String identifier) {
        return users.containsKey(identifier);
    }

    @Override
    public void save(User user) {
        users.put(user.getName(), user);
    }

    @Override
    public User get(String username) {
        return users.get(username);
    }

    @Override
    public void setCurrentUsername(String name) {
        currentUsername = name;
    }

    @Override
    public void saveWatchLater(String user, String movie, String url) {

    }
    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public Map<String, String> watchLater(String username) {
        return Map.of();
    }
}
