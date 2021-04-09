package app.repositories;

import app.model.Email;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class EmailRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Email email) throws DataIntegrityViolationException, DataAccessResourceFailureException {
        entityManager.persist(email);
        entityManager.flush();
    }

    public Email read(long id) throws DataAccessResourceFailureException{
        Email email = entityManager.find(Email.class, id);
        entityManager.flush();
        return email;
    }

    public List<Email> readAll(){
        CriteriaQuery<Email> query = entityManager.getCriteriaBuilder().createQuery(Email.class);;
        Root<Email> from = query.from(Email.class);
        CriteriaQuery<Email> selectAll = query.select(from);
        return entityManager.createQuery(selectAll).getResultList();
    }

    public Email read(String email) throws NoResultException {
        TypedQuery<Email> query = entityManager.createNamedQuery("Email.findByEmail", Email.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    public void update (Email email) throws DataIntegrityViolationException, DataAccessResourceFailureException, OptimisticLockingFailureException {
        entityManager.merge(email);
        entityManager.flush();
    }

    public void delete(Email email) throws DataIntegrityViolationException, DataAccessResourceFailureException, OptimisticLockingFailureException{
        entityManager.remove(entityManager.merge(email));
        entityManager.flush();
    }

}
