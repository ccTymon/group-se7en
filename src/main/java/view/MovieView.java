package view;

import entity.Review;
import interface_adapter.loggedin.LoggedInViewModel;
import interface_adapter.loggedin.LoggedinState;
import interface_adapter.showmovie.MovieController;
import interface_adapter.showmovie.MovieSearchModel;
import interface_adapter.showmovie.MovieState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MovieView extends JPanel implements ActionListener , PropertyChangeListener {
    private final String viewName = "movie";
    private final JLabel movieName;
    private final JTextArea rating;
    private final MovieSearchModel movieSearchModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ImageIcon movieIcon;
    private final JLabel movieLabel;
    private final JTextArea Plot;
    private JButton backButton;
    private JButton saveButton;
    private MovieController movieController;
    private LoggedinState loggedinState;
    private final JButton reviewButton;
    private final JTextField reviewField = new JTextField(15);
    private final JTextArea reviewsDisplay = new JTextArea(10, 20);
    private JComboBox<Integer> ratingDropdown;
    private String next_watch = "";
    private String next_watch_poster = null;
    private MovieState currentMovieState;

    public MovieView(MovieSearchModel movieSearchModel, LoggedInViewModel loggedInViewModel) throws IOException {
        this.movieSearchModel = movieSearchModel;
        this.loggedInViewModel = loggedInViewModel;
        this.loggedinState = loggedInViewModel.getState();
        movieSearchModel.addPropertyChangeListener(this);

        movieName = new JLabel("Movie Name");
        movieName.setAlignmentX(Component.CENTER_ALIGNMENT);
        movieName.setFont(new Font("SansSerif", Font.BOLD, 24));

        JPanel posterPanel = new JPanel(new BorderLayout(20, 0));

        movieIcon = new ImageIcon();
        movieLabel = new JLabel(movieIcon);
        movieLabel.setPreferredSize(new Dimension(400, 600));
        movieLabel.setHorizontalAlignment(SwingConstants.CENTER);
        posterPanel.add(movieLabel, BorderLayout.WEST);         // add poster to detail panel

        JPanel infoPanel = new JPanel(new BorderLayout(0, 10));

        rating = new JTextArea("Rating:");
        rating.setEditable(false);
        rating.setFocusable(false);
        rating.setOpaque(false);
        rating.setLineWrap(true);
        rating.setWrapStyleWord(true);
        rating.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(rating, BorderLayout.NORTH);

        Plot = new JTextArea();
        infoPanel.add(Plot, BorderLayout.CENTER);




        JPanel user_inputPanel = new JPanel(new BorderLayout());
        user_inputPanel.setLayout(new BoxLayout(user_inputPanel, BoxLayout.Y_AXIS));

        reviewButton = new JButton("Review");
        reviewButton.addActionListener(this);

        saveButton = new JButton("Save to watch later");
        saveButton.addActionListener(this);

        user_inputPanel.setOpaque(false);

        // user_inputPanel.add(reviewField);
        JPanel reviewInputRow = new JPanel();
        reviewInputRow.setLayout(new BoxLayout(reviewInputRow, BoxLayout.X_AXIS));

        reviewInputRow.add(new JLabel("Review: "));
        Integer[] values = {1,2,3,4,5,6,7,8,9,10};

        ratingDropdown = new JComboBox<>(values);
        reviewInputRow.add(ratingDropdown);
        reviewInputRow.add(reviewField);

        user_inputPanel.add(reviewInputRow);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(reviewButton, BorderLayout.WEST);
        buttonPanel.add(saveButton, BorderLayout.EAST);
        // user_inputPanel.add(reviewButton);
        // user_inputPanel.add(saveButton);
        user_inputPanel.add(buttonPanel);
        posterPanel.add(user_inputPanel, BorderLayout.SOUTH);

        JLabel reviewTitle = new JLabel("Reviews");
        reviewTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        user_inputPanel.add(reviewTitle);


        user_inputPanel.add(reviewsDisplay);


        infoPanel.add(user_inputPanel, BorderLayout.SOUTH);

        posterPanel.add(infoPanel, BorderLayout.CENTER);


        backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(movieName);
        this.add(posterPanel, BorderLayout.CENTER);


        this.add(backButton);

    }
    public void logReview(String username, Integer rating, String review) throws IOException {

        if (currentMovieState == null) return;

        String movieID = currentMovieState.getMovieId();
        String path = "reviewDB.json";
        Review currReview =  new Review("StrID", username, movieID, rating, review);
        // Read the existing data from the file (or create a new array if the file doesn't exist)
        JSONArray reviewsArray = new JSONArray();
        File file = new File(path);
        if (file.exists()) {
            // File exists, load existing reviews
            String content = new String(Files.readAllBytes(Paths.get(path)));
            reviewsArray = new JSONArray(content);
        }

        // Create a new review object
        JSONObject newReview = new JSONObject();
        newReview.put("username", username);
        newReview.put("rating", rating);
        newReview.put("review", review);

        // Add the new review to the list for the current movie
        boolean movieFound = false;
        for (int i = 0; i < reviewsArray.length(); i++) {
            JSONObject movieReview = reviewsArray.getJSONObject(i);
            if (movieReview.getString("movieId").equals(movieID)) {
                movieReview.getJSONArray("reviews").put(newReview);
                movieFound = true;
                break;
            }
        }

        if (!movieFound) {
            // If the movie doesn't exist in the file, create a new movie entry
            JSONObject movieData = new JSONObject();
            movieData.put("movieId", movieID);
            JSONArray movieReviews = new JSONArray();
            movieReviews.put(newReview);
            movieData.put("reviews", movieReviews);
            reviewsArray.put(movieData);
        }

        // Write the updated array back to the file
        try (FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.write(reviewsArray.toString(4)); // Indentation level for pretty-printing
        }
    }



    public void actionPerformed(ActionEvent e) {
        if  (e.getSource() == backButton) {
            movieController.goBack();

        }

        if (e.getSource() == reviewButton) {
            String reviewText = reviewField.getText().trim();

            if (reviewText.equals("") || reviewText.equals("Write your review...")) {
                return;
            }

            // Fetchable:  currentState.getUsername(), reviewText, ratingNum,
            Integer ratingNum = (Integer) ratingDropdown.getSelectedItem();
            LoggedinState currentState = loggedInViewModel.getState();
            reviewsDisplay.append(currentState.getUsername() + ": (" + ratingNum + "/10) " + reviewText + "\n");

            reviewField.setText("");

            try {
                logReview(currentState.getUsername(), ratingNum, reviewText);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == saveButton) {
            LoggedinState currentState = loggedInViewModel.getState();
            String username = currentState.getUsername();
            // loggedinState.setNext_watch(next_watch);
            // loggedinState.setNext_watch_poster(next_watch_poster);
            movieController.saveInternal(username, next_watch, next_watch_poster);
            loggedInViewModel.setState(loggedinState);
//            loggedInViewModel.firePropertyChange();
//
//            if (currentMovieState != null) {
//                saveUserWatchLater(
//                        username,
//                        next_watch,
//                        currentMovieState.getMovieIcon()
//                );
            if (currentMovieState != null) {
                movieController.saveInternal(
                        username,
                        currentMovieState.getMovieName(),
                        currentMovieState.getMovieIcon()
                );

                System.out.println("Saved to watch later: " + currentMovieState.getMovieName());
            }
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {

        final MovieState movieState = (MovieState) evt.getNewValue();
        currentMovieState = movieState;
        movieName.setText("Movie Name:  " + movieState.getMovieName());
        rating.setText("Rating:  \n" + movieState.getMovieRate());
        Plot.setText("Plot:\n" +  movieState.getMoviePlot());
        next_watch = movieState.getMovieName();

        // START SAMPLE COMMENT SECTION
        String path = "reviewDB.json";
        JSONArray reviewsArray;

        if (Files.exists(Paths.get(path))) {
            String content = null;
            try {
                content = new String(Files.readAllBytes(Paths.get(path)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            reviewsArray = new JSONArray(content);
        } else {
            reviewsArray = new JSONArray(); // file missing → empty database
        }
        List<List<Object>> reviewList = new ArrayList<>();

        for (int i = 0; i < reviewsArray.length(); i++) {
            JSONObject movieObj = reviewsArray.getJSONObject(i);



            if (movieObj.getString("movieId").equals(movieState.getMovieId())) {
                JSONArray reviewArray = movieObj.getJSONArray("reviews");

                for (int j = 0; j < reviewArray.length(); j++) {
                    JSONObject r = reviewArray.getJSONObject(j);

                    List<Object> entry = new ArrayList<>();
                    entry.add(r.getString("username"));
                    entry.add(r.getInt("rating"));
                    entry.add(r.getString("review"));

                    reviewList.add(entry);
                }

                break;
            }
        }


        reviewsDisplay.setText("");
        for (List<Object> entry : reviewList) {
            String username = (String) entry.get(0);
            int rating = (int) entry.get(1);
            String comment = (String) entry.get(2);

            reviewsDisplay.append(username + ": (" + rating + "/10) " + comment + "\n");
        }
        // END

        try {
            URL imageUrl = new URL(movieState.getMovieIcon());
            HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();  // actually send the request

            BufferedImage img;
            if (connection.getResponseCode() == 404) {
                // Fallback image if the main image is missing
                URL fallbackUrl = new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxuutX8HduKl2eiBeqSWo1VdXcOS9UxzsKhQ&s");
                img = ImageIO.read(fallbackUrl);
            } else {
                img = ImageIO.read(imageUrl); // only read if the URL exists
            }

            next_watch_poster = movieState.getMovieIcon();
            movieIcon.setImage(img);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getViewName() {
        return viewName;
    }

    private void saveUserWatchLater(String username, String movieName, String posterUrl) {
        String path = "userDataDB.json";
        JSONArray usersArray = new JSONArray();

        File file = new File(path);
        if (file.exists()) {
            try {
                String content = new String(Files.readAllBytes(Paths.get(path)));
                usersArray = new JSONArray(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Find and update user's data, or create new entry
        boolean userFound = false;
        for (int i = 0; i < usersArray.length(); i++) {
            JSONObject userObj = usersArray.getJSONObject(i);
            if (userObj.getString("username").equals(username)) {
                userObj.put("nextWatch", movieName);
                userObj.put("posterUrl", posterUrl);
                userFound = true;
                break;
            }
        }

        if (!userFound) {
            JSONObject newUser = new JSONObject();
            newUser.put("username", username);
            newUser.put("nextWatch", movieName);
            newUser.put("posterUrl", posterUrl);
            usersArray.put(newUser);
        }

        // Write to file
        try (FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.write(usersArray.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void  setMovieController(MovieController movieController) {
        this.movieController = movieController;
    }
}
