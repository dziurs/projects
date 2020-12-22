package app.dao;

import app.exception.BuildingSalesAppException;
import app.exception.GeneralApplicationException;
import app.model.entity.Review;
import app.model.enums.BuildingType;
import org.eclipse.persistence.exceptions.DatabaseException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Transactional(value = Transactional.TxType.MANDATORY)
public class ReviewDAO extends GenericAbstractDAO<Review> {

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

    @Override
    public void create(Review entity) throws BuildingSalesAppException {
        try {
            super.create(entity);
        }catch (PersistenceException ex){
            if (ex.getCause() instanceof DatabaseException && ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw new GeneralApplicationException(GeneralApplicationException.KEY_OPTIMISTIC_LOCK,ex);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public void update(Review entity) throws BuildingSalesAppException {
        try{
            super.update(entity);
        }catch (OptimisticLockException e){
            throw new GeneralApplicationException(GeneralApplicationException.KEY_OPTIMISTIC_LOCK,e);
        }
    }
    public void delete(Review entity) throws BuildingSalesAppException {
        try{
            super.delete(entity);
        }catch (OptimisticLockException e){
            throw new GeneralApplicationException(GeneralApplicationException.KEY_OPTIMISTIC_LOCK,e);
        }
    }

    @Override
    public List<Review> readAll() {
        joinTransaction();
        TypedQuery<Review> namedQuery = entityManager.createNamedQuery("Review.findAll", Review.class);
        return namedQuery.setFirstResult(0).setMaxResults(50).getResultList();

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
        joinTransaction();
        TypedQuery<Review> namedQuery = entityManager.createNamedQuery("Review.findByBuildingType", Review.class);
        namedQuery.setParameter("buildingType", buildingType);
        return namedQuery.setFirstResult(0).setMaxResults(50).getResultList();
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
        joinTransaction();
        TypedQuery<Review> namedQuery = entityManager.createNamedQuery("Review.findByCity", Review.class);
        namedQuery.setParameter("city", city);
        return namedQuery.setFirstResult(0).setMaxResults(50).getResultList();
    }
    public List<Review> findByCityAndBuildingType (String city, BuildingType buildingType){
        joinTransaction();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Review> criteria = criteriaBuilder.createQuery(Review.class);
        Root<Review> root = criteria.from(Review.class);
        Predicate predicate = criteriaBuilder.and(
                criteriaBuilder.equal(root.get("city"), city),
                criteriaBuilder.equal(root.get("buildingType"), buildingType));
        criteria.select(root).where(predicate);
        criteria.orderBy(criteriaBuilder.desc(root.get("creationDate")));
        TypedQuery<Review> query = entityManager.createQuery(criteria);
        return query.setFirstResult(0).setMaxResults(50).getResultList();
    }
}
