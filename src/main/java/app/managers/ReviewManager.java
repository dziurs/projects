package app.managers;

import app.converter.ConverterEntityToDTO;
import app.dao.DeveloperDAO;
import app.dao.ReviewDAO;
import app.dto.ReviewDTO;
import app.exception.BuildingSalesAppException;
import app.exception.GeneralApplicationException;
import app.interceptor.Log;
import app.model.entity.Developer;
import app.model.entity.Review;
import app.model.enums.BuildingType;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Stateless
@Local
@Transactional(value = Transactional.TxType.MANDATORY)
public class ReviewManager {

    @Inject
    private ReviewDAO reviewDAO;

    @Inject
    private DeveloperDAO developerDAO;

    @Log
    public void addReview(Developer developer, Review review) throws BuildingSalesAppException {
        review.setDeveloper(developer);
        developer.addReview(review);
        reviewDAO.create(review);
        developerDAO.update(developer);
    }

    @Log
    public List<ReviewDTO> getDeveloperReviews(String developerLogin) {
        List<Developer> list = developerDAO.findByEmail(developerLogin);
        Developer developer = list.get(0);
        Set<Review> reviews = developer.getReviews();
        List<Review> reviewList = new ArrayList<>(reviews);
        return addReviewDTO(reviewList);
    }

    @Log
    public void reviewDelete(Review review) throws BuildingSalesAppException {
        Developer developer = review.getDeveloper();
        developer.removeReview(review);
        developerDAO.update(developer);
//        reviewDAO.delete(review);
    }

    @Log
    public ReviewDTO findReviewByID(int id) throws BuildingSalesAppException {
        List<Review> list = reviewDAO.findByID(id);
        if (list.size()==0)throw new GeneralApplicationException(GeneralApplicationException.KEY_OPTIMISTIC_LOCK);
        Review review = list.get(0);
        ReviewDTO reviewDTO = ConverterEntityToDTO.convertReviewToReviewDTO(review);
        return reviewDTO;
    }

    @Log
    public void editReview(Review review) throws BuildingSalesAppException {
        reviewDAO.update(review);
    }

    @Log
    public List<ReviewDTO> findAll(){
        List<Review> reviewList = reviewDAO.readAll();
        return addReviewDTO(reviewList);
    }

    @Log
    public List<ReviewDTO> findAllByCity(String city){
        List<Review> reviewList = reviewDAO.findByCity(city);
        return addReviewDTO(reviewList);
    }

    @Log
    public List<ReviewDTO> findAllByBuildingType(BuildingType buildingType){
        List<Review> reviewList = reviewDAO.findByBuildingType(buildingType);
        return addReviewDTO(reviewList);
    }

    @Log
    public List<ReviewDTO> findAllByCityAndBuildingType(String city, BuildingType buildingType){
        List<Review> reviewList = reviewDAO.findByCityAndBuildingType(city,buildingType);
        return addReviewDTO(reviewList);
    }


    private List<ReviewDTO> addReviewDTO(List<Review> reviewList){
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        Iterator<Review> iterator = reviewList.iterator();
        while (iterator.hasNext()) {
            ReviewDTO reviewDTO = ConverterEntityToDTO.convertReviewToReviewDTO(iterator.next());
            reviewDTOList.add(reviewDTO);
        }
        return reviewDTOList;
    }

}
