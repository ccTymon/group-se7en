package use_case.search;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class SearchInteractor implements SearchInputBoundary {
    private final SearchOutputBoundary searchPresenter;
    private static final String API_KEY = "ebcbe61c";
    private static final String URL = "http://www.omdbapi.com/?apikey=" + API_KEY + "&t=";
    private final OkHttpClient client = new OkHttpClient();

    public SearchInteractor(SearchOutputBoundary searchPresenter) {
        this.searchPresenter = searchPresenter;
    }

    public void execute(SearchInputData searchInputData) {
        String movie = searchInputData.getMovie().toLowerCase().trim();
        if (movie.equals("")) {
            searchPresenter.prepareFailureView("cannot be empty");
            return;
        }

        Request request = new Request.Builder().url(URL + movie).build();


        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            JSONObject jsonObject = new JSONObject(responseBody);

            if (jsonObject.getString("Response").equals("False")) {
                searchPresenter.prepareFailureView("Movie not found");
                return;
            }

            String movieName = jsonObject.getString("Title");
            StringBuilder rate = new StringBuilder();

            JSONArray ratings = jsonObject.getJSONArray("Ratings");
            for (int i = 0; i < ratings.length(); i++) {
                JSONObject rating = ratings.getJSONObject(i);
                String source = rating.getString("Source");
                String value = rating.getString("Value");

                rate.append(source).append(":   ").append(value);

                if (i < ratings.length() - 1) {
                    rate.append("\n");
                }
            }

            String rateString = rate.toString();

            String iconString = jsonObject.getString("Poster");
            String Plot = jsonObject.getString("Plot");
            String formattedPlot = Plot.replaceAll("\\.\\s*", ".\n");
            String Id = jsonObject.getString("imdbID");

            SearchOutputData searchOutputData = new SearchOutputData(movieName, rateString, iconString, formattedPlot, Id);

            searchPresenter.prepareSuccessView(searchOutputData);
        } catch (Exception e) {
            searchPresenter.prepareFailureView(e.getMessage());
        }
    }

    public void switchToLoggedInView() {
        searchPresenter.switchToLoggedInView();
    }
}