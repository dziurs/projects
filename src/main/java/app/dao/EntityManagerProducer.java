package app.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import java.util.HashMap;


@Named("producer")
@ApplicationScoped
public class EntityManagerProducer {

    @PersistenceUnit(unitName = "NewPersistenceUnit")
    EntityManagerFactory entityManagerFactory;

    @Produces
    @RequestScoped
    public EntityManager createEM(){
        //return Persistence.createEntityManagerFactory("NewPersistenceUnit", new HashMap())
        //        .createEntityManager();//TODO this is only for the time of testing, it must be removed
        return entityManagerFactory.createEntityManager();//this was commented because under tests it does not run on container
        //and entityManagerFactory will not be injected
    }
    public void disposeEM(@Disposes EntityManager entityManager){
        if(entityManager.isOpen())entityManager.close();
    }

}
