package app.model.entity;

import app.model.enums.BuildingType;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserAndReviewTest {

    public Review review;
    public Review reviewTwo;
    public Review reviewThree;
    public User user;
    public User userTwo;
    public User userThree;
    public Developer developer;
    @BeforeEach
    public void initObjects(){
        Email email = new Email("dziur@interia.pl");
        Email email2 = new Email("dziur74@interia.pl");
        Email email3 = new Email("basia.wysocka@gmail.com");
        Email emailDeveloper = new Email("develop@o2.pl");
        developer = new Developer();
        developer.setEmail(emailDeveloper);
        developer.setSurname("Nowak");
        developer.setFirstName("Piotr");
        developer.setCompanyName("BUD-DOM");
        review = new Review();
        review.setTitle("Dom w Sulisławicach");
        review.setArea(1000);
        review.setBuildingType(BuildingType.House);
        review.setLivingSpace(230);
        review.setGarage(true);
        review.setCity("Kalisz");
        review.setStreet("Łódzka 59");
        review.setPostCode("62-800");
        review.setImage_url("/images/mojdom.png");
        review.setDeveloper(developer);
        reviewTwo = new Review();
        reviewTwo.setTitle("Mieszkanie na Hożej w Kaliszu");
        reviewTwo.setArea(68);
        reviewTwo.setBuildingType(BuildingType.Flat);
        reviewTwo.setLivingSpace(59);
        reviewTwo.setGarage(false);
        reviewTwo.setCity("Kalisz");
        reviewTwo.setStreet("Moniuszki 1");
        reviewTwo.setPostCode("62-800");
        reviewTwo.setImage_url("/images/mojemieszkanie.png");
        reviewTwo.setDeveloper(developer);
        reviewThree = new Review();
        reviewThree.setTitle("Działka we wsi Warszówka");
        reviewThree.setArea(808);
        reviewThree.setBuildingType(BuildingType.Land);
        reviewThree.setLivingSpace(0);
        reviewThree.setGarage(false);
        reviewThree.setCity("Kalisz");
        reviewThree.setStreet("Malownicza 8");
        reviewThree.setPostCode("62-800");
        reviewThree.setImage_url("/images/działka.png");
        reviewThree.setDeveloper(developer);
        user = new User();
        user.setSurname("Kuświk");
        user.setFirstName("Radek");
        user.setEmail(email);
        userTwo = new User();
        userTwo.setSurname("Gierłowska");
        userTwo.setFirstName("Monika");
        userTwo.setEmail(email2);
        userThree = new User();
        userThree.setSurname("Wysocka");
        userThree.setFirstName("Barbara");
        userThree.setEmail(email3);
        review.addUser(user);
        review.addUser(userTwo);
        review.addUser(userThree);
        reviewTwo.addUser(userTwo);
        reviewTwo.addUser(userThree);
        reviewThree.addUser(userThree);
    }
    @Test
    public void testRelationManyToMany(){
        HibernatePersistenceProvider provider = new HibernatePersistenceProvider();
        EntityManagerFactory persistenceUnit = provider.createEntityManagerFactory("NewPersistenceUnit", new HashMap());
        EntityManager entityManager = persistenceUnit.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(developer);
        entityManager.persist(review);
        entityManager.persist(reviewTwo);
        entityManager.persist(reviewThree);
        entityManager.getTransaction().commit();
        List<Review> reviewList = entityManager.createQuery("select r from Review r").getResultList();
        assertNotNull(reviewList);
        assertEquals(reviewList.size(),3);
        Review reviewFromDatabase = reviewList.get(0);
        assertNotNull(reviewFromDatabase);
        assertEquals(reviewFromDatabase.getTitle(),"Dom w Sulisławicach");
        assertEquals(reviewFromDatabase.getArea(),1000);
        assertEquals(reviewFromDatabase.getLivingSpace(),230);
        Set<User> users = reviewFromDatabase.getUsers();
        assertEquals(users.size(),3);
        assertTrue(users.contains(user));
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()){
            User user = iterator.next();
            String name = user.getSurame();
            String firstName = user.getFirstName();
            String email = user.getEmail().getEmail();
            switch (name){
                case "Kuświk" : {
                    assertEquals(this.user.getSurame(),name);
                    break;
                }
                case "Gierłowska" : {
                    assertEquals(this.userTwo.getSurame(),name);
                    break;
                }
                case "Wysocka" : {
                    assertEquals(this.userThree.getSurame(),name);
                    break;
                }
            }
            switch (firstName){
                case "Radek" : {
                    assertEquals(this.user.getFirstName(),firstName);
                    break;
                }
                case "Monika" : {
                    assertEquals(this.userTwo.getFirstName(),firstName);
                    break;
                }
                case "Barbara" : {
                    assertEquals(this.userThree.getFirstName(),firstName);
                    break;
                }
            }
            switch (email){
                case "dziur@interia.pl" : {
                    assertEquals(this.user.getEmail().getEmail(),email);
                    break;
                }
                case "dziur74@gmail.com" : {
                    assertEquals(this.userTwo.getEmail().getEmail(),email);
                    break;
                }
                case "basia.wysocka@gmail.com" : {
                    assertEquals(this.userThree.getEmail().getEmail(),email);
                    break;
                }
            }
        }
        entityManager.close();
    }

}