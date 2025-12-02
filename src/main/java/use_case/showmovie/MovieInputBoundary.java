package use_case.showmovie;

public interface MovieInputBoundary {
    void executeSave(MovieInputData movieInputData);

    void execute(MovieInputData inputData);
}
