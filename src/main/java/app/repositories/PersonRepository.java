package app.repositories;

import app.model.Person;
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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class PersonRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Person create(Person person) throws DataIntegrityViolationException, DataAccessResourceFailureException {
        entityManager.persist(person);
        entityManager.flush();
        return person;
    }

    public Person read(long id) throws DataAccessResourceFailureException{
        Person person = entityManager.find(Person.class, id);
        entityManager.flush();
        return person;
    }
    
    public List<Person> readAll(){
        CriteriaQuery<Person> query = entityManager.getCriteriaBuilder().createQuery(Person.class);;
        Root<Person> from = query.from(Person.class);
        CriteriaQuery<Person> selectAll = query.select(from);
        return entityManager.createQuery(selectAll).getResultList();
    }

    public Person read(String email) throws NoResultException {
        TypedQuery<Person> query = entityManager.createNamedQuery("Person.findByEmail", Person.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    public Person update (Person person) throws DataIntegrityViolationException, DataAccessResourceFailureException, OptimisticLockingFailureException {
        Person merge = entityManager.merge(person);
        entityManager.flush();
        return merge;
    }

    public void delete(Person person) throws DataIntegrityViolationException, DataAccessResourceFailureException, OptimisticLockingFailureException{
        entityManager.remove(entityManager.merge(person));
        entityManager.flush();
    }
}
