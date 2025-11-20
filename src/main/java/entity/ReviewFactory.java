package entity;

public class ReviewFactory {

    public Review create(String rID, String uID, String movieID, Integer rating, String body){
        return new Review(rID, uID, movieID, rating, body);
    }
}
