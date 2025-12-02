package data_access;

import entity.User;
import entity.UserFactory;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.showmovie.MovieUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DAO for user data implemented using a File to persist the data.
 */
public class UserDataAccessInterface implements SignupUserDataAccessInterface,
                                                LoginUserDataAccessInterface,
                                                LogoutUserDataAccessInterface,
                                                MovieUserDataAccessInterface
                                                  {

    private static final String HEADER = "username,password,movie,url";

    private final File csvFile;
    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private final Map<String, User> accounts = new HashMap<>();
    private final Map<String, Map<String,String>> watchLater = new HashMap<>();

    private String currentUsername;

    /**
     * Construct this DAO for saving to and reading from a local file.
     * @param csvPath the path of the file to save to
     * @param userFactory factory for creating user objects
     * @throws RuntimeException if there is an IOException when accessing the file
     */
    public UserDataAccessInterface(String csvPath, UserFactory userFactory) {

        csvFile = new File(csvPath);
        headers.put("username", 0);
        headers.put("password", 1);
        headers.put("movie", 2);
        headers.put("url", 3);

        if (csvFile.length() == 0) {
            save();
        }
        else {

            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                final String header = reader.readLine();

                if (!header.equals(HEADER)) {
                    throw new RuntimeException(String.format("header should be%n: %s%n but was:%n%s", HEADER, header));
                }

                String row;
                while ((row = reader.readLine()) != null) {
                    final String[] col = row.split(",");
                    final String username = String.valueOf(col[headers.get("username")]);
                    final String password = String.valueOf(col[headers.get("password")]);
                    final String movie = String.valueOf(col[headers.get("movie")]);
                    final String url = String.valueOf(col[headers.get("url")]);
                    final User user = userFactory.create(username, password);
                    Map<String,String> profile = new HashMap<>();

                    if(movie.equals("none")){
                        profile.put("movie", null);
                        profile.put("url", null);
                    } else {
                        profile.put("movie", movie);
                        profile.put("url", url);
                    }

                    watchLater.put(username, profile);
                    accounts.put(username, user);
                }
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void save() {
        final BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(csvFile));
            writer.write(String.join(",", headers.keySet()));
            writer.newLine();

            for (User user : accounts.values()) {
                String movie;
                String url;
                // Check if next movie has been set
                if (this.watchLater.get(user.getName()).get("movie") == null){
                    movie = "none";
                    url = "none";
                } else {
                    movie = this.watchLater(user.getName()).get("movie");
                    url = this.watchLater(user.getName()).get("url");
                }

                final String line = String.format("%s,%s,%s,%s",
                        user.getName(),
                        user.getPassword(),
                        movie,
                        url);
                writer.write(line);
                writer.newLine();
            }

            writer.close();

        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void save(User user) {
        accounts.put(user.getName(), user);
        HashMap<String,String> nextMovie = new HashMap<>();
        nextMovie.put("movie", "none");
        nextMovie.put("url", "none");
        watchLater.put(user.getName(), nextMovie);
        this.save();
    }

    @Override
    public User get(String username) {
        return accounts.get(username);
    }

    @Override
    public void setCurrentUsername(String name) {
        currentUsername = name;
    }

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public void saveWatchLater(String user, String movie, String url) {
        watchLater(user).put("movie", movie);
        watchLater(user).put("url", url);
    }

    @Override
    public boolean existsByName(String identifier) {
        return accounts.containsKey(identifier);
    }

    public Map<String,String> watchLater(String username) {
        return watchLater.get(username);}

}
