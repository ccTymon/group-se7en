package data_access;

import entity.Review;
import entity.ReviewFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;
import use_case.showmovie.ReviewDataAccessInterface;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FileReviewDataAccessObject implements ReviewDataAccessInterface {

    private final File reviewDB;
    private final Map<String, Review> reviews = new HashMap<>();
    private static String masterID = null;
    private static Integer counter = 1;

    public FileReviewDataAccessObject(String primaryDBPath, ReviewFactory reviewFactory) {
        reviewDB = new File(primaryDBPath);

        if (reviewDB.length() == 0) {
            setMasterID("a1");
            saveToFile();
        }
        else {
            try {
                String jsonString = Files.readString(Path.of(primaryDBPath));
                JSONArray jsonArray = new JSONArray(jsonString);

                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject working = jsonArray.getJSONObject(i);
                    String rID = working.keySet().toArray()[0].toString();
                    Review product = reviewFactory.create(
                            rID,
                            working.getJSONObject(rID).getString("uID"),
                            working.getJSONObject(rID).getString("movieID"),
                            working.getJSONObject(rID).getInt("rating"),
                            working.getJSONObject(rID).getString("body"));
                    reviews.put(rID, product);

                    // retrieve numerical value of comment ID and compare to counter
                    // set counter and masterID to value of greatest present review ID + 1
                    Integer numVal = Integer.parseInt(rID.substring(1));
                    if (numVal > counter) {
                        setCounter(numVal);
                        setMasterID(rID);
                    }

                }
            }
            catch (IOException ex){
                throw new RuntimeException(ex);
            }
        }
    }

    private void saveToFile() {
        try (FileWriter fw = new FileWriter(reviewDB)) {
            JSONWriter jsonWriter = new JSONWriter(fw);
            jsonWriter.array();

            for (Review value : reviews.values()) {
                JSONObject jsonReview = new JSONObject();
                jsonReview.put("uID", value.getuID());
                jsonReview.put("movieID", value.getMovieID());
                jsonReview.put("rating", value.getRating());
                jsonReview.put("body", value.getBody());

                jsonWriter.object()
                        .key(value.getrID())
                        .value(jsonReview)
                        .endObject();
            }

            jsonWriter.endArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // save review to hashmap, then save to file.
    public void save(Review review) {
        reviews.put(review.getrID(), review);
        this.saveToFile();
    }

    public static void setCounter(Integer counter) {
        FileReviewDataAccessObject.counter = counter;
    }

    public static void setMasterID(String masterID){
        FileReviewDataAccessObject.masterID = masterID;
    }

    public Review getReview(String rID) {return this.reviews.get(rID);}

    public static Integer getCounter() {
        return counter;
    }

    public String getMasterID() {
        return masterID;
    }

    @Override
    public Review retrieve(String rID) {
        return this.reviews.get(rID);
    }
}
