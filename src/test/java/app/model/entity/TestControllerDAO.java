package app.model.entity;

import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;

public class TestControllerDAO {
    public static EntityManager getDAOManager(){
        //HibernatePersistenceProvider provider = new HibernatePersistenceProvider();
        //EntityManagerFactory persistenceUnit = provider.createEntityManagerFactory("NewPersistenceUnit", new HashMap());
        //EntityManager entityManager = persistenceUnit.createEntityManager();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("NewPersistenceUnit", new HashMap());
        EntityManager entityManager = entityManagerFactory.createEntityManager();


        return entityManager;
    }
}
