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
    private final JTextArea comment;
    private JButton backButton;
    private MovieController movieController;

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

        //JScrollPane scrollPane = new JScrollPane(Plot);
        //scrollPane.setBorder(BorderFactory.createTitledBorder("Plot Summary:"));
        //infoPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel user_inputPanel = new JPanel(new BorderLayout());
        user_inputPanel.setLayout(new BoxLayout(user_inputPanel, BoxLayout.Y_AXIS));

        comment = new JTextArea();
        user_inputPanel.add(comment, BorderLayout.WEST);

        infoPanel.add(user_inputPanel, BorderLayout.SOUTH);
        posterPanel.add(infoPanel, BorderLayout.CENTER);


        backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(movieName);
        this.add(posterPanel, BorderLayout.CENTER);
        //this.add(rating);
        // this.add(movieLabel);
        //this.add(Plot);
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
        Plot.setText("Plot: \n " +  movieState.getMoviePlot());
        try {
            URL imageUrl = new URL(movieState.getMovieIcon());
            BufferedImage img = ImageIO.read(imageUrl);
            movieIcon.setImage(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
        comment.setText("There are n comment");

    }

    public String getViewName() {
        return viewName;
    }

    public void  setMovieController(MovieController movieController) {
        this.movieController = movieController;
    }
}
