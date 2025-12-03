package data_access;

import entity.Review;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;
import use_case.showmovie.MovieDataAccessInterface;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FileMovieDataAccessObject implements MovieDataAccessInterface {
    private final File movieDB;
    private final Map<String, ArrayList<String>> movies = new HashMap<>();

    public FileMovieDataAccessObject(String moviePath) {
        movieDB = new File(moviePath);

        if (movieDB.length() == 0){save();}

        else{
            try{
                String jsonString = Files.readString(Path.of(moviePath));
                JSONArray jsonArray  = new JSONArray(jsonString);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Set<String> keys = jsonObject.keySet();
                    String movieID = keys.toArray()[0].toString();
                    ArrayList<String> commentID = new ArrayList<>();

                    for (int j = 0; j < jsonObject.getJSONArray(movieID).length(); j++) {
                        commentID.add(jsonObject.getJSONArray(movieID).getString(j));
                    }

                    movies.put(movieID, commentID);
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
        if(movies.get(review.getMovieID()) != null) {
            movies.get(review.getMovieID()).add(review.getrID());
        }
        else{
            ArrayList<String> comments = new ArrayList<>();
            comments.add(review.getrID());
            movies.put(review.getMovieID(), comments);
        }
        this.save();
    }

    public Map<String, ArrayList<String>> getMovies() {
        return movies;
    }

    public List<String> getComments(String movieID) {
        return getMovies().get(movieID);
    }

    @Override
    public Boolean checkMovie(String movieID) {
        return this.movies.containsKey(movieID);
    }
}

