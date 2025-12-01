package interface_adapter.loggedin;

import interface_adapter.ViewModel;
import interface_adapter.login.LoginState;

/**
 * The View Model for the Logged In View.
 */
public class LoggedInViewModel extends ViewModel<LoggedinState> {

    public LoggedInViewModel() {
        super("logged in");
        setState(new LoggedinState());
    }
}
