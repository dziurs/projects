package app.model.entity;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class JPAConnectionDatabaseTest {
//    public UsersEntity developer;
//    public UsersEntity user2;
//    public Developer developer;
//
//    @BeforeEach
//    public void initObjects(){
//        developer = new UsersEntity();
//        developer.setFirstname("Nowak");
//        developer.setName("Jan");
//        developer.setType("Developer");
//        developer.setId(null);
//        user2= new UsersEntity();
//        user2.setFirstname("Kuświk");
//        user2.setName("Radek");
//        user2.setType("Developer");
//        user2.setId(null);
//        developer = new Developer();
//        developer.setFirstname("Antczak");
//        developer.setName("Marek");
//        developer.setId(null);
//        developer.setType("Firma budowlana");
//        developer.getList().add(developer);
//        developer.getList().add(user2);
//    }
//    @Test
//    public void testCommitObject(){
//        HibernatePersistenceProvider provider = new HibernatePersistenceProvider();
//        EntityManagerFactory persistenceUnit = provider.createEntityManagerFactory("NewPersistenceUnit", new HashMap());
//        EntityManager entityManager = persistenceUnit.createEntityManager();
//        entityManager.getTransaction().begin();
//        entityManager.persist(developer);
//        entityManager.getTransaction().commit();
//        List<Developer> developerList = entityManager.createQuery("select d from Developer d").getResultList();
//        entityManager.close();
//        assertNotNull(developerList);
//        assertEquals(developerList.size(),1);
//        Developer developerFromDatabase = developerList.get(0);
//        assertNotNull(developerFromDatabase);
//        assertEquals(developerList.get(0).getFirstname(),"Antczak");
//        assertEquals(developerList.get(0).getName(),"Marek");
//        assertEquals(developerList.get(0).getType(),"Firma budowlana");
//        List<UsersEntity> list = developerFromDatabase.getList();
//        assertNotNull(list);
//        assertEquals(list.size(),2);
//        assertEquals(list.get(0).getFirstname(),"Nowak");
//        assertEquals(list.get(0).getName(),"Jan");
//        assertEquals(list.get(0).getType(),"Developer");
//        assertEquals(list.get(1).getFirstname(),"Kuświk");
//        assertEquals(list.get(1).getName(),"Radek");
//        assertEquals(list.get(1).getType(),"Developer");
//    }

}