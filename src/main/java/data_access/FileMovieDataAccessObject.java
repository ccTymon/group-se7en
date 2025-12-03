package data_access;

import entity.Review;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.showmovie.MovieDataAccessInterface;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileMovieDataAccessObject implements MovieDataAccessInterface {
    private final File movieDB;
    private final Map<String, List<String>> movies = new HashMap<>();

    public FileMovieDataAccessObject(String moviePath){
        movieDB = new File(moviePath);

        if (movieDB.length() == 0) {
            save();
        } else {
            try {
                String jsonString = Files.readString(Path.of(moviePath));
                JSONArray jsonArray = new JSONArray(jsonString);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String movieID = jsonObject.keys().next();

                    JSONArray arr = jsonObject.getJSONArray(movieID);
                    List<String> commentIDs = new ArrayList<>();

                    for (int j = 0; j < arr.length(); j++) {
                        commentIDs.add(arr.getString(j));
                    }

                    movies.put(movieID, commentIDs);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void save() {
        try (FileWriter fw = new FileWriter(movieDB)) {
            JSONArray arr = new JSONArray();

            for (String movieID : movies.keySet()) {
                JSONObject obj = new JSONObject();
                obj.put(movieID, movies.get(movieID));
                arr.put(obj);
            }

            fw.write(arr.toString(4));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Review review) {
        movies.computeIfAbsent(review.getMovieID(), k -> new ArrayList<>());
        movies.get(review.getMovieID()).add(review.getrID());
        save();
    }

    @Override
    public List<String> getReviews(String movieId) {
        return List.of();
    }

    public Map<String, List<String>> getMovies() {
        return movies;
    }

    public List<String> getCommentIDs(String movieID) {
        return movies.get(movieID);
    }
}
