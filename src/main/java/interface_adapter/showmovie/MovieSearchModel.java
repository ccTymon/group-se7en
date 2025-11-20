package interface_adapter.showmovie;

import interface_adapter.ViewModel;

public class MovieSearchModel extends ViewModel<MovieState> {
    public MovieSearchModel() {
        super("movie");
        this.setState(new MovieState());
    }
}
