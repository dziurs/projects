package app.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeveloperTest {
    public Developer developer;
    public Email email;
    @BeforeEach
    public void initObjects(){
        email = new Email("developer@wp.pl");
        developer = new Developer();
        developer.setName("Nowak");
        developer.setFirstName("Janusz");
        developer.setEmail(email);
        developer.setCompanyName("Firma Budowlana NOW-BUD");
    }
    @Test
    public void testDeveloper(){
        EntityManager entityManager = TestControllerDAO.getDAOManager();
        entityManager.getTransaction().begin();
        entityManager.persist(developer);
        entityManager.getTransaction().commit();
        List<Developer> list = entityManager.createQuery("select d from Developer d").getResultList();
        entityManager.close();
        assertEquals(list.size(),1);
        Developer readedDeveloper = list.get(0);
        assertEquals(readedDeveloper.getName(), "Nowak");
        assertEquals(readedDeveloper.getFirstName(), "Janusz");
        assertEquals(readedDeveloper.getEmail().getEmail(),"developer@wp.pl");
        assertEquals(readedDeveloper.getCompanyName(), "Firma Budowlana NOW-BUD");

    }

}