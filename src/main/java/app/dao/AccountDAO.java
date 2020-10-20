package app.dao;

import app.security.Account;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional(value = Transactional.TxType.MANDATORY)
public class AccountDAO extends GenericAbstractDAO<Account> {

    @Inject
    public AccountDAO(EntityManager entityManager) {
        super(Account.class, entityManager);
    }

    @Override
    protected List<Account> findByID(int id) {
        TypedQuery<Account> namedQuery = entityManager.createNamedQuery("Account.findById", Account.class);
        namedQuery.setParameter("id", id);
        return namedQuery.getResultList();
    }

    public List<Account> findByEmail(String email) {
        TypedQuery<Account> namedQuery = entityManager.createNamedQuery("Account.findByEmail", Account.class);
        namedQuery.setParameter("login", email);
        return namedQuery.getResultList();
    }
}
