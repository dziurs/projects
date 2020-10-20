package app.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    public User user;
    public Email email;

    @BeforeEach
    public void initObjects(){
        email = new Email("test@wp.pl");
        user = new User();
        user.setSurname("Kowalski");
        user.setFirstName("Jan");
        user.setEmail(email);
    }
    @Test
    public void testUser(){
        EntityManager entityManager = TestControllerDAO.getDAOManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        List<User> list = entityManager.createQuery("select u from User u").getResultList();
        entityManager.close();
        assertEquals(list.size(),1);
        User readedUser = list.get(0);
        assertEquals(readedUser.getSurame(), "Kowalski");
        assertEquals(readedUser.getFirstName(), "Jan");
        assertEquals(readedUser.getEmail().getEmail(),"test@wp.pl");
    }

}