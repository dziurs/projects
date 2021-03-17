package app.repositories;



import app.exceptions.AppDatabaseException;
import app.loggers.LogApp;
import app.model.User;
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
@Transactional(propagation = Propagation.MANDATORY)
public class UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(User user) throws DataIntegrityViolationException, DataAccessResourceFailureException {
        entityManager.persist(user);
        entityManager.flush();
    }

    public User read(long id) throws DataAccessResourceFailureException{
        User user = entityManager.find(User.class, id);
        entityManager.flush();
        return user;

    }
    public List<User> readAllUsers(){
        CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
        Root root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public User read(String email) throws AppDatabaseException {
        try {
            TypedQuery<User> namedQuery = entityManager.createNamedQuery("User.findByEmail", User.class);
            namedQuery.setParameter("email", email);
            return namedQuery.getSingleResult();
        }catch (NoResultException ex){
            throw new AppDatabaseException(AppDatabaseException.NO_RESULT_FOUND);
        }
    }


    public void update(User user) throws DataIntegrityViolationException, DataAccessResourceFailureException, OptimisticLockingFailureException {
        entityManager.merge(user);
        entityManager.flush();
    }


    public void delete(User user) throws DataAccessResourceFailureException, OptimisticLockingFailureException{
        entityManager.remove(entityManager.merge(user));
        entityManager.flush();
    }
}
