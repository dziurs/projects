package app.dao;

import app.model.entity.Review;
import app.model.enums.BuildingType;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional(value = Transactional.TxType.MANDATORY)
public class ReviewDAO extends GenericAbstractDAO {

    @Inject
    public ReviewDAO(EntityManager entityManager) {
        super(Review.class, entityManager);
    }

    @Override
    public List<Review> findByID(int id) {
        TypedQuery<Review> namedQuery = entityManager.createNamedQuery("Review.findById", Review.class);
        namedQuery.setParameter("id", id);
        return namedQuery.getResultList();
    }
    public Review merge(Review review){
        Review merge = getEntityManager().merge(review);
        return merge;
    }
    public List<Review> findByTitle (String title){
        TypedQuery<Review> namedQuery = entityManager.createNamedQuery("Review.findByTitle", Review.class);
        namedQuery.setParameter("title", title);
        return namedQuery.getResultList();
    }
    public List<Review> findByArea (int area){
        TypedQuery<Review> namedQuery = entityManager.createNamedQuery("Review.findByArea", Review.class);
        namedQuery.setParameter("area", area);
        return namedQuery.getResultList();
    }
    public List<Review> findByBuildingType (BuildingType buildingType){
        TypedQuery<Review> namedQuery = entityManager.createNamedQuery("Review.findByBuildingType", Review.class);
        namedQuery.setParameter("buildingType", buildingType);
        return namedQuery.getResultList();
    }
    public List<Review> findByLivingSpace (int livingSpace){
        TypedQuery<Review> namedQuery = entityManager.createNamedQuery("Review.findByLivingSpace", Review.class);
        namedQuery.setParameter("livingSpace", livingSpace);
        return namedQuery.getResultList();
    }
    public List<Review> findByGarage (boolean garage){
        TypedQuery<Review> namedQuery = entityManager.createNamedQuery("Review.findByGarage", Review.class);
        namedQuery.setParameter("garage", garage);
        return namedQuery.getResultList();
    }
    public List<Review> findByCity (String city){
        TypedQuery<Review> namedQuery = entityManager.createNamedQuery("Review.findByCity", Review.class);
        namedQuery.setParameter("city", city);
        return namedQuery.getResultList();
    }
}
