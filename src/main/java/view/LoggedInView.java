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

        if (state.getNext_watch() == null || state.getNext_watch().isEmpty()) {
            loadUserWatchLater(state.getUsername());
        }

        next_watch_string = state.getNext_watch();
        image = state.getNext_watch_poster();
        System.out.println(state.getNext_watch_poster());
        //imageIcon.setImage(image);
        //next_watch.setIcon(imageIcon);
        if (image != null) {
            next_watch.setText(next_watch_string);
            imageIcon.setImage(image);
            next_watch.setIcon(imageIcon);
             // Set text to movie name
            // Set the preferred size for the actual image display
            next_watch.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
            next_watch.setBorder(null); // Remove placeholder border
        } else {
            // Handle case where state updates but image is still null (e.g., loading state)
            next_watch.setText("Movie placeholder");
            next_watch.setIcon(null); // Clear icon
            next_watch.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            next_watch.setPreferredSize(new Dimension(150, 150));
        }

        // Must revalidate and repaint the container to reflect size/icon changes
        revalidate();
        repaint();
    }

    public void loadUserWatchLater(String username) {
        String path = "userDataDB.json";

        if (!Files.exists(Paths.get(path))) {
            return;
        }

        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            JSONArray usersArray = new JSONArray(content);

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject userObj = usersArray.getJSONObject(i);
                if (userObj.getString("username").equals(username)) {
                    String movieName = userObj.optString("nextWatch", null);
                    String posterUrl = userObj.optString("posterUrl", null);

                    if (movieName != null && posterUrl != null) {
                        // Load the image
                        loadPosterImage(movieName, posterUrl);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                    LoggedinState currentState = loggedInViewModel.getState();
                    currentState.setNext_watch(movieName);
                    currentState.setNext_watch_poster(img);
                    loggedInViewModel.setState(currentState);
                    loggedInViewModel.firePropertyChange();
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