package app.model.dao;

import app.model.entity.Email;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Transactional(value = Transactional.TxType.MANDATORY)
public class EmailDAO extends GenericAbstractDAO {

    @Inject
    public EmailDAO(EntityManager entityManager) {
        super(Email.class, entityManager);
    }

    @Override
    public Email findByID(int id){
        TypedQuery<Email> namedQuery = entityManager.createNamedQuery("Email.findById", Email.class);
        namedQuery.setParameter("id", id);
        return namedQuery.getSingleResult();
    }

    public Email findByEmail(String email){
        TypedQuery<Email> namedQuery = entityManager.createNamedQuery("Email.findByEmail", Email.class);
        namedQuery.setParameter("email", email);
        return namedQuery.getSingleResult();
    }
}
