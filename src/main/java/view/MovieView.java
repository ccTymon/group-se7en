package view;

import interface_adapter.showmovie.MovieController;
import interface_adapter.showmovie.MovieSearchModel;
import interface_adapter.showmovie.MovieState;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URL;

public class MovieView extends JPanel implements ActionListener , PropertyChangeListener {
    private final String viewName = "movie";
    private final JLabel movieName;
    private final JTextArea rating;
    private final MovieSearchModel movieSearchModel;
    private final ImageIcon movieIcon;
    private final JLabel movieLabel;
    private final JTextArea Plot;
    private JButton backButton;
    private JButton saveButton;
    private MovieController movieController;
    private final JButton reviewButton;
    private final JTextField reviewField = new JTextField(15);
    private final JTextArea reviewsDisplay = new JTextArea(10, 20);

    public MovieView(MovieSearchModel movieSearchModel) {
        this.movieSearchModel = movieSearchModel;
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

        user_inputPanel.add(reviewField);

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
    public void actionPerformed(ActionEvent e) {
        if  (e.getSource() == backButton) {
            movieController.goBack();

        }

        if (e.getSource() == reviewButton) {
            String text = reviewField.getText().trim();

            if (text.equals("") || text.equals("Write your review...")) {
                return;
            }

            reviewsDisplay.append("- " + text + "\n");

            reviewField.setText("");
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        final MovieState movieState = (MovieState) evt.getNewValue();
        movieName.setText("Movie Name:  " + movieState.getMovieName());
        rating.setText("Rating:  \n" + movieState.getMovieRate());
        Plot.setText("Plot: \n " +  movieState.getMoviePlot());
        try {
            URL imageUrl = new URL(movieState.getMovieIcon());
            BufferedImage img = ImageIO.read(imageUrl);
            movieIcon.setImage(img);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getViewName() {
        return viewName;
    }

    public void  setMovieController(MovieController movieController) {
        this.movieController = movieController;
    }
}
