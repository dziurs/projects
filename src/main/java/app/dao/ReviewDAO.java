package app.dao;

import app.exception.AccountException;
import app.exception.BuildingSalesAppException;
import app.exception.GeneralAplicationException;
import app.model.entity.Developer;
import app.model.entity.Review;
import app.model.enums.BuildingType;
import org.eclipse.persistence.exceptions.DatabaseException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
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
                throw new GeneralAplicationException(GeneralAplicationException.KEY_OPTIMISTIC_LOCK,ex);
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
            throw new GeneralAplicationException(GeneralAplicationException.KEY_OPTIMISTIC_LOCK,e);
        }
    }
    public void delete(Review entity) throws BuildingSalesAppException {
        try{
            super.delete(entity);
        }catch (OptimisticLockException e){
            throw new GeneralAplicationException(GeneralAplicationException.KEY_OPTIMISTIC_LOCK,e);
        }
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
