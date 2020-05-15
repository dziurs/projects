package app.model.dao;

import app.model.entity.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional(value = Transactional.TxType.MANDATORY)
public class UserDAO extends GenericAbstractDAO {

    @Inject
    public UserDAO(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    @Override
    public User findByID(int id) {
        TypedQuery<User> namedQuery = entityManager.createNamedQuery("User.findById", User.class);
        namedQuery.setParameter("id", id);
        return namedQuery.getSingleResult();
    }
    public User findByEmail(String email){
        TypedQuery<User> namedQuery = entityManager.createNamedQuery("User.findByEmail", User.class);
        namedQuery.setParameter("email", email);
        return namedQuery.getSingleResult();
    }
    public List<User> findByName (String name){
        TypedQuery<User> namedQuery = entityManager.createNamedQuery("User.findByName", User.class);
        namedQuery.setParameter("name", name);
        return namedQuery.getResultList();
    }
    public List<User> findByFirstName (String firstName){
        TypedQuery<User> namedQuery = entityManager.createNamedQuery("User.findByFirstName", User.class);
        namedQuery.setParameter("firstName", firstName);
        return namedQuery.getResultList();
    }

}
