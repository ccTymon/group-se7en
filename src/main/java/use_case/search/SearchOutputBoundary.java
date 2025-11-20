package use_case.search;



public interface SearchOutputBoundary {
    void prepareSuccessView(SearchOutputData outputData);
    void prepareFailureView(String errorMessage);
}
