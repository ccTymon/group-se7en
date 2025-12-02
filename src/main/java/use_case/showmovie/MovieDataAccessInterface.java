package use_case.showmovie;

import entity.Review;

import java.util.List;

public interface MovieDataAccessInterface {
    //Request list of commentIDs for given movie
   List<String> getComments(String movieID);

   //Check to see if movie has comments
    Boolean checkMovie(String movieID);

    //Save review to movie comment json
    void save(Review review);
}