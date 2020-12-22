package app.dao;

import app.model.entity.Email;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional(value = Transactional.TxType.MANDATORY)
public class EmailDAO extends GenericAbstractDAO {

    @Inject
    public EmailDAO(EntityManager entityManager) {
        super(Email.class, entityManager);
    }

    @Override
    public List<Email> findByID(int id){
        joinTransaction();
        TypedQuery<Email> namedQuery = entityManager.createNamedQuery("Email.findById", Email.class);
        namedQuery.setParameter("id", id);
        return namedQuery.getResultList();
    }

    public List<Email> findByEmail(String email){
        joinTransaction();
        TypedQuery<Email> namedQuery = entityManager.createNamedQuery("Email.findByEmail", Email.class);
        namedQuery.setParameter("email", email);
        return namedQuery.getResultList();
    }
}
