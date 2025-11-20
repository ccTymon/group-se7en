package view;

import interface_adapter.showmovie.MovieController;
import interface_adapter.showmovie.MovieSearchModel;
import interface_adapter.showmovie.MovieState;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MovieView extends JPanel implements ActionListener , PropertyChangeListener {
    private final String viewName = "movie";
    private final JLabel movieName;
    private final JTextArea rating;
    private final MovieSearchModel movieSearchModel;
    private JButton backButton;
    private MovieController movieController;

    public MovieView(MovieSearchModel movieSearchModel) {
        this.movieSearchModel = movieSearchModel;
        movieSearchModel.addPropertyChangeListener(this);

        movieName = new JLabel("Movie Name");
        movieName.setAlignmentX(Component.CENTER_ALIGNMENT);

        rating = new JTextArea("Rating:");
        rating.setEditable(false);
        rating.setFocusable(false);
        rating.setOpaque(false);
        rating.setLineWrap(true);
        rating.setWrapStyleWord(true);
        rating.setAlignmentX(Component.CENTER_ALIGNMENT);

        backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(movieName);
        this.add(rating);
        this.add(backButton);
    }
    public void actionPerformed(ActionEvent e) {
        if  (e.getSource() == backButton) {

        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        final MovieState movieState = (MovieState) evt.getNewValue();
        movieName.setText("Movie Name:  " + movieState.getMovieName());
        rating.setText("Rating:  \n" + movieState.getMovieRate());
    }

    public String getViewName() {
        return viewName;
    }

    public void  setMovieController(MovieController movieController) {
        this.movieController = movieController;
    }
}
