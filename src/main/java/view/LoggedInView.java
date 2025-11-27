package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.loggedin.LoggedInViewModel;
import interface_adapter.loggedin.LoggedinState;
import interface_adapter.login.LoginState;
import interface_adapter.logout.LogoutController;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;

/**
 * The View for when the user is logged into the program.
 */
public class LoggedInView extends JPanel implements ActionListener , PropertyChangeListener {

    private final String viewName = "logged in";
    private final JButton searchButton;
    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;
    private LogoutController logoutController;

    private JLabel userid;
    private JLabel next_watch;
    private final JButton logOut;




    public LoggedInView(ViewManagerModel viewManagerModel,  LoggedInViewModel loggedInViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.setOpaque(true);
        this.setBackground(new Color(235, 245, 255));

        loggedInViewModel.addPropertyChangeListener(this);
        userid = new JLabel("Hi");
        userid.setFont(new Font("Monospaced", Font.BOLD, 22));
        userid.setAlignmentX(Component.CENTER_ALIGNMENT);


        next_watch = new JLabel("Your next watch: ");
        next_watch.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttons = new JPanel();
        searchButton = new JButton("Search");

        searchButton.setBackground(new Color(100, 149, 237));
        searchButton.setForeground(Color.WHITE);
        searchButton.setOpaque(true);
        searchButton.setBorderPainted(false);
        searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                searchButton.setBackground(new Color(120, 170, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                searchButton.setBackground(new Color(100, 149, 237));
            }
        });



        logOut = new JButton("Log Out");

        logOut.setBackground(new Color(100, 149, 237));
        logOut.setForeground(Color.WHITE);
        logOut.setOpaque(true);
        logOut.setBorderPainted(false);
        logOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logOut.setBackground(new Color(120, 170, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                logOut.setBackground(new Color(100, 149, 237));
            }
        });


        buttons.add(logOut);
        buttons.add(searchButton);

        buttons.setOpaque(true);

        buttons.setBackground(new Color(235, 245, 255));

        searchButton.addActionListener(this);
        logOut.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(userid);
        this.add(next_watch);
        this.add(buttons);
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

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }




    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoggedinState state = (LoggedinState) evt.getNewValue();
            userid.setText("Hi" + " "  + state.getUsername() + "!");
            next_watch.setText("Your next watch:" + " "  + state.getNextWatch());
    }
}
