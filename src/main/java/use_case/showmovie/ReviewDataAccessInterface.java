package use_case.showmovie;
import entity.Review;

public interface ReviewDataAccessInterface {

    /** @param review the review to save
     */
    void save(Review review);

    /** @param rID the identifying code of the target review
     */
    Review retrieve(String rID);

    String getMasterID();

    void setMasterID();
}

