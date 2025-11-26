package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.loggedin.LoggedInViewModel;
import interface_adapter.loggedin.LoggedinState;
import interface_adapter.login.LoginState;
import interface_adapter.search.SearchViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for when the user is logged into the program.
 */
public class LoggedInView extends JPanel implements ActionListener , PropertyChangeListener {

    private final String viewName = "logged in";
    private final JButton searchButton;
    private final JLabel next_watch;
    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;
    private JLabel userid;


    public LoggedInView(ViewManagerModel viewManagerModel,  LoggedInViewModel loggedInViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        loggedInViewModel.addPropertyChangeListener(this);
        userid = new JLabel("Hi");
        userid.setAlignmentX(Component.CENTER_ALIGNMENT);

        next_watch = new JLabel();
        next_watch.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon imageIcon = new ImageIcon();

        // if no image shows placeholder
        if (imageIcon.getIconWidth() > 0) {
            next_watch.setIcon(imageIcon);
            next_watch.setPreferredSize(new Dimension(400, 650));
        } else {
            next_watch.setText("Movie placeholder");
            next_watch.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            next_watch.setPreferredSize(new Dimension(150, 150));
        }

        // Make the image clickable
        next_watch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("image clicked");
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
            userid.setText("hi" + "  "  + state.getUsername());
    }
}
