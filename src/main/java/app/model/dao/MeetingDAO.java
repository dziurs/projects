package app.model.dao;

import app.model.entity.Review;
import app.model.entity.Meeting;
import app.model.entity.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional(value = Transactional.TxType.MANDATORY)
public class MeetingDAO extends GenericAbstractDAO {

    @Inject
    public MeetingDAO(EntityManager entityManager) {
        super(Meeting.class, entityManager);
    }


    @Override
    public Meeting findByID(int id) {
        TypedQuery<Meeting> namedQuery = entityManager.createNamedQuery("Meeting.findById", Meeting.class);
        namedQuery.setParameter("id", id);
        return namedQuery.getSingleResult();
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
        TypedQuery<Meeting> namedQuery = entityManager.createNamedQuery("Meeting.findByReview", Meeting.class);
        namedQuery.setParameter("review", review);
        return namedQuery.getResultList();
    }
}
