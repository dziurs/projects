package app.dao;

import app.exception.AppDataBaseException;
import app.exception.BuildingSalesAppException;
import app.exception.GeneralApplicationException;
import app.model.entity.Developer;
import app.model.entity.Review;
import app.model.entity.Meeting;
import app.model.entity.User;
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
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

@Transactional(value = Transactional.TxType.MANDATORY)
public class MeetingDAO extends GenericAbstractDAO {

    @Inject
    public MeetingDAO(EntityManager entityManager) {
        super(Meeting.class, entityManager);
    }


    @Override
    public void create(Object entity) throws BuildingSalesAppException {
        try {
            super.create(entity);
        }catch (ConstraintViolationException e){
            throw new GeneralApplicationException(GeneralApplicationException.CONSTRAINT_VIOLATION,e);
        }catch (PersistenceException ex){
            if (ex.getCause() instanceof DatabaseException && ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw new AppDataBaseException(ex);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public void update(Object entity) throws BuildingSalesAppException {
        try{
            super.update(entity);
        }catch (ConstraintViolationException e){
            throw new GeneralApplicationException(GeneralApplicationException.CONSTRAINT_VIOLATION,e);
        }catch (OptimisticLockException e){
            throw new AppDataBaseException(e);
        }catch (PersistenceException ex){
            if (ex.getCause() instanceof DatabaseException && ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw new AppDataBaseException(ex);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public void delete(Object entity) throws BuildingSalesAppException {
        try{
            super.delete(entity);
        }catch (OptimisticLockException e){
            throw new AppDataBaseException(e);
        }
    }

    @Override
    public List<Meeting> findByID(int id) {
        joinTransaction();
        TypedQuery<Meeting> namedQuery = entityManager.createNamedQuery("Meeting.findById", Meeting.class);
        namedQuery.setParameter("id", id);
        return namedQuery.getResultList();
    }
    public List<Meeting> findByDate(Date date){
        TypedQuery<Meeting> namedQuery = entityManager.createNamedQuery("Meeting.findByDate", Meeting.class);
        namedQuery.setParameter("date", date);
        return namedQuery.getResultList();
    }
    public List<Meeting> findByAddedByUser(boolean isAddedByUser){
        TypedQuery<Meeting> namedQuery = entityManager.createNamedQuery("Meeting.findByAddedByUser", Meeting.class);
        namedQuery.setParameter("added", isAddedByUser);
        return namedQuery.getResultList();
    }
    public List<Meeting> findByUser(User user){
        TypedQuery<Meeting> namedQuery = entityManager.createNamedQuery("Meeting.findByUser", Meeting.class);
        namedQuery.setParameter("user", user);
        return namedQuery.getResultList();
    }
    public List<Meeting> findByReview(Review review){
        joinTransaction();
        TypedQuery<Meeting> namedQuery = entityManager.createNamedQuery("Meeting.findByReview", Meeting.class);
        namedQuery.setParameter("review", review);
        return namedQuery.getResultList();
    }
    public List<Meeting> findDevelopersMeetingsAcceptedByUser (Developer developer){
        joinTransaction();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Meeting> criteria = criteriaBuilder.createQuery(Meeting.class);
        Root<Meeting> root = criteria.from(Meeting.class);
        Predicate predicate = criteriaBuilder.and(
                criteriaBuilder.equal(root.get("review").get("developer"), developer),
                criteriaBuilder.equal(root.get("addedByUser"), true));
        criteria.select(root).where(predicate);
        criteria.orderBy(criteriaBuilder.desc(root.get("creationDate")));
        TypedQuery<Meeting> query = entityManager.createQuery(criteria);
        return query.getResultList();
    }
}
