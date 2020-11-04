package app.dao;

import app.exception.AccountException;
import app.exception.BuildingSalesAppException;
import app.model.entity.Developer;
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
public class DeveloperDAO extends GenericAbstractDAO <Developer>{

    @Inject
    public DeveloperDAO(EntityManager entityManager) {
        super(Developer.class, entityManager);
    }

    @Override
    public void create(Developer entity) throws BuildingSalesAppException {
        try {
            super.create(entity);
        }catch (PersistenceException ex){
            if (ex.getCause() instanceof DatabaseException && ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw new AccountException(AccountException.EMAIL_WAS_USED);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public void update(Developer entity) throws BuildingSalesAppException {
        try{
            super.update(entity);
        }catch (OptimisticLockException e){
            throw new AccountException(AccountException.KEY_OPTIMISTIC_LOCK,e);
        }
    }
    public void delete(Developer entity) throws BuildingSalesAppException {
        try{
            super.delete(entity);
        }catch (OptimisticLockException e){
            throw new AccountException(AccountException.KEY_OPTIMISTIC_LOCK,e);
        }
    }

    @Override
    public List<Developer> findByID(int id) {
        TypedQuery<Developer> namedQuery = entityManager.createNamedQuery("Developer.findById", Developer.class);
        namedQuery.setParameter("id", id);
        return namedQuery.getResultList();
    }
    public List<Developer> findByEmail(String email){
        TypedQuery<Developer> namedQuery = entityManager.createNamedQuery("Developer.findByEmail", Developer.class);
        namedQuery.setParameter("email", email);
        return namedQuery.getResultList();
    }
    public List<Developer> findByName (String name){
        TypedQuery<Developer> namedQuery = entityManager.createNamedQuery("Developer.findBySurname", Developer.class);
        namedQuery.setParameter("surname", name);
        return namedQuery.getResultList();
    }
    public List<Developer> findByFirstName (String firstName){
        TypedQuery<Developer> namedQuery = entityManager.createNamedQuery("Developer.findByFirstName", Developer.class);
        namedQuery.setParameter("firstName", firstName);
        return namedQuery.getResultList();
    }
}
