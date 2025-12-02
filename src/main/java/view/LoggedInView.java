package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.loggedin.LoggedInViewModel;
import interface_adapter.loggedin.LoggedinState;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;
import interface_adapter.logout.LogoutController;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * The View for when the user is logged into the program.
 */
public class LoggedInView extends JPanel implements ActionListener , PropertyChangeListener {

    private final String viewName = "logged in";
    private final JButton searchButton;
    private JLabel next_watch;
    private String next_watch_string;
    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;
    private final SearchViewModel searchViewModel;
    private LogoutController logoutController;
    private JLabel userid;
    private SearchController searchController;
    private BufferedImage image = null;
    private ImageIcon imageIcon;
    private final JButton logOut;


    public LoggedInView(ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel, SearchViewModel searchViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.searchViewModel = searchViewModel;
        this.searchViewModel.addPropertyChangeListener(this);
        loggedInViewModel.addPropertyChangeListener(this);
        userid = new JLabel("Hi");
        userid.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.imageIcon = new ImageIcon();


        next_watch = new JLabel();
        next_watch.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel title = new JLabel("Your next watch");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        boolean b = image != null;
        //if (b) {
        //    next_watch.setText(next_watch_string);
        //    next_watch.setIcon(imageIcon);
        //    next_watch.setPreferredSize(new Dimension(400, 650));
        //} else {
        //    next_watch.setText("Movie placeholder");
        //    next_watch.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //    next_watch.setPreferredSize(new Dimension(150, 150));
        //}
        next_watch.setText("Movie placeholder");
        next_watch.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        next_watch.setPreferredSize(new Dimension(150, 150));

        // Make the image clickable
        next_watch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("image clicked");
                String movieName = next_watch_string;
                // String movieName = "The Shawshank Redemption";
                final SearchState currentState = searchViewModel.getState();
                currentState.setMoviename(movieName);
                searchViewModel.setState(currentState);


                if (searchController != null) {
                    searchController.execute(movieName);
                }
            }
        });


        final JPanel buttons = new JPanel();
        searchButton = new JButton("search");
        logOut = new JButton("Log Out");
        buttons.add(logOut);
        buttons.add(searchButton);
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);

        searchButton.addActionListener(this);
        logOut.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        this.add(userid);
        this.add(buttons);
        this.add(title);
        this.add(next_watch);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();

        if (source == logOut) {
            logoutController.execute();
            System.out.println("Logged out!");
        }
        else if (source == searchButton) {
            viewManagerModel.setState("search");
            viewManagerModel.firePropertyChange();
            System.out.println("Search clicked!");
        }
    }




    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoggedinState state = (LoggedinState) evt.getNewValue();
        userid.setText("You are logged in as: \"" + state.getUsername() + "\"");

        Map<String, String> watchLater = state.getWatchLater();

        if (watchLater != null) {
            String savedMovie = watchLater.get("movie");
            String savedUrl = watchLater.get("url");

            if (savedMovie != null && !savedMovie.equals("none")) {
                // Load the poster image
                loadPosterImage(savedMovie, savedUrl);
            } else {
                // No movie saved - show placeholder
                displayPlaceholder();
            }
        } else {
            // No watch later data - show placeholder
            displayPlaceholder();
        }
    }

    private void displayPlaceholder() {
        next_watch.setText("No movie saved");
        next_watch.setIcon(null);
        next_watch.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        next_watch.setPreferredSize(new Dimension(150, 150));
        revalidate();
        repaint();
    }

//    public void loadUserWatchLater(String username) {
//        String path = "userDataDB.json";
//
//        if (!Files.exists(Paths.get(path))) {
//            return;
//        }
//
//        try {
//            String content = new String(Files.readAllBytes(Paths.get(path)));
//            JSONArray usersArray = new JSONArray(content);
//
//            for (int i = 0; i < usersArray.length(); i++) {
//                JSONObject userObj = usersArray.getJSONObject(i);
//                if (userObj.getString("username").equals(username)) {
//                    String movieName = userObj.optString("nextWatch", null);
//                    String posterUrl = userObj.optString("posterUrl", null);
//
//                    if (movieName != null && posterUrl != null) {
//                        // Load the image
//                        loadPosterImage(movieName, posterUrl);
//                    }
//                    break;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void loadPosterImage(String movieName, String posterUrl) {
        SwingWorker<BufferedImage, Void> worker = new SwingWorker<>() {
            @Override
            protected BufferedImage doInBackground() throws Exception {
                URL imageUrl = new URL(posterUrl);
                return ImageIO.read(imageUrl);
            }

            @Override
            protected void done() {
                try {
                    BufferedImage img = get();
                    next_watch_string = movieName;
                    image = img;
//                    LoggedinState currentState = loggedInViewModel.getState();
//                    currentState.setNext_watch(movieName);
//                    currentState.setNext_watch_poster(img);
//                    loggedInViewModel.setState(currentState);
//                    loggedInViewModel.firePropertyChange();

                    if (img != null) {
                        next_watch.setText(movieName);
                        imageIcon.setImage(img);
                        next_watch.setIcon(imageIcon);
                        next_watch.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
                        next_watch.setBorder(null);}
                    else {
                        displayPlaceholder();
                    }
                    revalidate();
                    repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }


    public void setSearchController(SearchController searchController) {
        this.searchController = searchController;
    }
}