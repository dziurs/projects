package app.managers;

import app.converter.ConverterEntityToDTO;
import app.dao.DeveloperDAO;
import app.dao.ReviewDAO;
import app.dto.ReviewDTO;
import app.exception.BuildingSalesAppException;
import app.model.entity.Developer;
import app.model.entity.Review;
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

    public void addReview(Developer developer, Review review) throws BuildingSalesAppException {
        review.setDeveloper(developer);
        developer.addReview(review);
        reviewDAO.create(review);
        developerDAO.update(developer);
    }
    public List<ReviewDTO> getDeveloperReviews(String developerLogin) {
        List<Developer> list = developerDAO.findByEmail(developerLogin);
        Developer developer = list.get(0);
        Set<Review> reviews = developer.getReviews();
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        Iterator<Review> iterator = reviews.iterator();
        if (iterator.hasNext()) {
            Review review = iterator.next();
            ReviewDTO reviewDTO = ConverterEntityToDTO.convertReviewToReviewDTO(review);
            reviewDTOList.add(reviewDTO);
        }
        return reviewDTOList;
    }
    public void reviewDelete(Review review) throws BuildingSalesAppException {
        Developer developer = review.getDeveloper();
        developer.removeReview(review);
        developerDAO.update(developer);
        reviewDAO.delete(review);
    }
}
