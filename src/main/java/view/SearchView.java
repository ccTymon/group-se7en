package view;

import interface_adapter.search.SearchController;
import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SearchView extends JPanel implements ActionListener , PropertyChangeListener {
    private final String viewName = "search";
    private final SearchViewModel searchViewModel;

    private final JButton searchButton;
    private final JTextField searchField = new JTextField(15);

    private SearchController searchController;

    public SearchView(SearchViewModel searchViewModel) {
        this.searchViewModel = searchViewModel;
        this.searchViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("MovieSearch Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel movieSearchPanel = new JPanel();
        searchButton = new JButton("Search");

        searchButton.addActionListener(this);

        searchField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SearchState currentState = searchViewModel.getState();
                currentState.setMoviename(searchField.getText());
                searchViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        movieSearchPanel.add(searchField);
        movieSearchPanel.add(searchButton);

        this.add(title);
        this.add(movieSearchPanel);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(searchButton)) {
            final SearchState currentState = searchViewModel.getState();
            searchController.execute(currentState.getMoviename());
        }
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SearchState currentState = searchViewModel.getState();
        if (currentState.getSearchError() != null) {
            JOptionPane.showMessageDialog(this, currentState.getSearchError());
        }
    }

    public void setSearchController(SearchController searchController) {
        this.searchController = searchController;
    }
}
