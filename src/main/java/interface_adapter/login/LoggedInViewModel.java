package interface_adapter.login;

import interface_adapter.ViewModel;
import interface_adapter.loggedin.LoggedinState;

public class LoggedInViewModel extends ViewModel<LoggedinState> {
    public LoggedInViewModel() {
        super("logged in");
        setState(new LoggedinState());
    }
}
