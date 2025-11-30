package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoggedInViewModel;
import interface_adapter.loggedin.LoggedinState;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;

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
import java.net.MalformedURLException;
import java.net.URL;

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
    private JLabel userid;
    private SearchController searchController;
    private BufferedImage image = null;
    private ImageIcon imageIcon;


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
        buttons.add(searchButton);
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);

        searchButton.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        this.add(userid);
        this.add(buttons);
        this.add(next_watch);
    }

    public void actionPerformed(ActionEvent evt) {
        viewManagerModel.setState("search");
        viewManagerModel.firePropertyChange();
    }


    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoggedinState state = (LoggedinState) evt.getNewValue();
        userid.setText("You are logged in as: \"" + state.getUsername() + "\"");

        next_watch_string = state.getNext_watch();
        image = state.getNext_watch_poster();
        //imageIcon.setImage(image);
        //next_watch.setIcon(imageIcon);
        if (image != null) {
            imageIcon.setImage(image);
            next_watch.setIcon(imageIcon);
            next_watch.setText(next_watch_string); // Set text to movie name
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


    public void setSearchController(SearchController searchController) {
        this.searchController = searchController;
    }
}