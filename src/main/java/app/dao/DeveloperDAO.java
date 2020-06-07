package app.dao;

import app.model.entity.Developer;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional(value = Transactional.TxType.MANDATORY)
public class DeveloperDAO extends GenericAbstractDAO {

    @Inject
    public DeveloperDAO(EntityManager entityManager) {
        super(Developer.class, entityManager);
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
        TypedQuery<Developer> namedQuery = entityManager.createNamedQuery("Developer.findByName", Developer.class);
        namedQuery.setParameter("name", name);
        return namedQuery.getResultList();
    }
    public List<Developer> findByFirstName (String firstName){
        TypedQuery<Developer> namedQuery = entityManager.createNamedQuery("Developer.findByFirstName", Developer.class);
        namedQuery.setParameter("firstName", firstName);
        return namedQuery.getResultList();
    }
}
