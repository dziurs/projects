package app.dao;

import app.exception.AccountException;
import app.exception.BuildingSalesAppException;
import app.exception.GeneralApplicationException;
import app.model.enums.UserType;
import app.security.Account;
import org.eclipse.persistence.exceptions.DatabaseException;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Transactional(value = Transactional.TxType.MANDATORY)
public class AccountDAO extends GenericAbstractDAO<Account> {

    @Inject
    public AccountDAO(EntityManager entityManager) {
        super(Account.class, entityManager);
    }


    @Override
    public void create(Account entity) throws BuildingSalesAppException {
        try {
            super.create(entity);
        }catch (ConstraintViolationException e){
            throw new GeneralApplicationException(GeneralApplicationException.CONSTRAINT_VIOLATION,e);
        }catch (PersistenceException ex){
            if (ex.getCause() instanceof DatabaseException && ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw new AccountException(AccountException.EMAIL_WAS_USED);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public void update(Account entity) throws BuildingSalesAppException {
        try{
            super.update(entity);
        }catch (ConstraintViolationException e){
            throw new GeneralApplicationException(GeneralApplicationException.CONSTRAINT_VIOLATION,e);
        }catch (OptimisticLockException e){
            throw new AccountException(AccountException.KEY_OPTIMISTIC_LOCK,e);
        }
    }
    @Override
    public void delete(Account entity) throws BuildingSalesAppException {
        try{
            super.delete(entity);
        }catch (OptimisticLockException e){
            throw new AccountException(AccountException.KEY_OPTIMISTIC_LOCK,e);
        }
    }

    @Override
    protected List<Account> findByID(int id) {
        joinTransaction();
        TypedQuery<Account> namedQuery = entityManager.createNamedQuery("Account.findById", Account.class);
        namedQuery.setParameter("id", id);
        return namedQuery.getResultList();
    }

    public List<Account> findByEmail(String email) {
        joinTransaction();
        TypedQuery<Account> namedQuery = entityManager.createNamedQuery("Account.findByEmail", Account.class);
        namedQuery.setParameter("login", email);
        return namedQuery.getResultList();
    }
    public List<Account> findByRole(String role) {
        joinTransaction();
        TypedQuery<Account> namedQuery = entityManager.createNamedQuery("Account.findByRole", Account.class);
        UserType type = UserType.valueOf(role);
        namedQuery.setParameter("role", type);
        return namedQuery.getResultList();
    }
}
