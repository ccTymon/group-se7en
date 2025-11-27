package interface_adapter.search;


import use_case.search.SearchInputBoundary;
import use_case.search.SearchInputData;

public class SearchController {
    private final SearchInputBoundary searchUseCaseInteractor;

    public SearchController(SearchInputBoundary searchUseCaseInteractor) {
        this.searchUseCaseInteractor = searchUseCaseInteractor;
    }

    public void execute(String movie){
        SearchInputData searchInputData = new SearchInputData(movie);
        searchUseCaseInteractor.execute(searchInputData);
    }

    public void switchToLoggedInView(){
        searchUseCaseInteractor.switchToLoggedInView();
    }
}
