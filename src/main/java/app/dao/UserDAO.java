package app.dao;

import app.exception.AccountException;
import app.exception.BuildingSalesAppException;
import app.model.entity.User;
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
public class UserDAO extends GenericAbstractDAO <User>{

    @Inject
    public UserDAO(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    @Override
    public void create(User entity) throws BuildingSalesAppException {
        try {
            super.create(entity);
        }catch (PersistenceException e){
            if (e.getCause() instanceof DatabaseException && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw new AccountException(AccountException.EMAIL_WAS_USED);
            } else {
                throw e;
            }
        }
    }

    @Override
    public void update(User entity) throws BuildingSalesAppException {
        try{
            super.update(entity);
        }catch (OptimisticLockException e){
            throw new AccountException(AccountException.KEY_OPTIMISTIC_LOCK,e);
        }
    }
    public void delete(User entity) throws BuildingSalesAppException {
        try{
            super.delete(entity);
        }catch (OptimisticLockException e){
            throw new AccountException(AccountException.KEY_OPTIMISTIC_LOCK,e);
        }
    }
    @Override
    public List<User> findByID(int id) {
        TypedQuery<User> namedQuery = entityManager.createNamedQuery("User.findById", User.class);
        namedQuery.setParameter("id", id);
        return namedQuery.getResultList();
    }
    public List<User> findByEmail(String email){
        TypedQuery<User> namedQuery = entityManager.createNamedQuery("User.findByEmail", User.class);
        namedQuery.setParameter("email", email);
        return namedQuery.getResultList();
    }
    public List<User> findByName (String name){
        TypedQuery<User> namedQuery = entityManager.createNamedQuery("User.findBySurname", User.class);
        namedQuery.setParameter("surname", name);
        return namedQuery.getResultList();
    }
    public List<User> findByFirstName (String firstName){
        TypedQuery<User> namedQuery = entityManager.createNamedQuery("User.findByFirstName", User.class);
        namedQuery.setParameter("firstName", firstName);
        return namedQuery.getResultList();
    }

}
