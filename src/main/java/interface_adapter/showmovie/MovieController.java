package interface_adapter.showmovie;

import interface_adapter.ViewManagerModel;
import interface_adapter.search.SearchViewModel;

public class MovieController {
    private final ViewManagerModel viewManagerModel;
    private final SearchViewModel searchViewModel;

    public MovieController(ViewManagerModel viewManagerModel, SearchViewModel searchViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.searchViewModel = searchViewModel;
    }

    public void goBack() {
        searchViewModel.clear();
        viewManagerModel.setState("search");
        viewManagerModel.firePropertyChange();
    }
}
