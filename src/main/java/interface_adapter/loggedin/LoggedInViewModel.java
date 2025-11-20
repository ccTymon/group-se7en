package interface_adapter.loggedin;

import interface_adapter.ViewModel;

public class LoggedInViewModel extends ViewModel<LoggedinState> {
    public LoggedInViewModel() {
        super("logged in");
        setState(new LoggedinState());
    }
}
