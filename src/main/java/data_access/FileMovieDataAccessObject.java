package data_access;

import entity.Review;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FileMovieDataAccessObject {
    private final File movieDB;
    private final Map<String, List> movies = new HashMap<>();

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
                    List commentID = new List();

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
        final JSONWriter jsonWriter;
        try {
            jsonWriter = new JSONWriter(new FileWriter(movieDB));
            jsonWriter.array();

            for(String key : movies.keySet()){
                jsonWriter.key(key);

                JSONArray jsonArray = new JSONArray();
                List comments = movies.get(key);

                for (int i = 0; i < comments.getItemCount(); i++) {
                    jsonArray.put(comments.getItem(i));
                }

                jsonWriter.value(jsonArray);

                jsonWriter.endObject();

            }

            jsonWriter.endArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void save(Review review) {
        if(movies.get(review.getMovieID()) != null) {
            movies.get(review.getMovieID()).add(review.getrID());
        }
        else{
            List comments = new List();
            comments.add(review.getrID());
            movies.put(review.getMovieID(), comments);
        }
        this.save();
    }

    public Map<String, List> getMovies() {
        return movies;
    }

    public List getCommentIDs(String movieID) {
        return getMovies().get(movieID);
    }
}

