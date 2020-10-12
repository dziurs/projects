package app.model.entity;

import app.model.enums.BuildingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MeetingTest {
    public User user;
    public Email email;
    public Review review;
    public Meeting meeting;
    public Developer developer;

    @BeforeEach
    public void initObjects(){
        email = new Email("antek_nowak@wp.pl");
        user = new User();
        user.setName("Nowak");
        user.setFirstName("Antek");
        user.setEmail(email);
        developer = new Developer();
        Email emailDeveloper = new Email("developer@wp.pl");
        developer = new Developer();
        developer.setSurname("Nowak");
        developer.setFirstName("Janusz");
        developer.setEmail(emailDeveloper);
        developer.setCompanyName("Firma Budowlana NOW-BUD");
        review = new Review();
        review.setTitle("testowe ogłoszenie");
        review.setArea(0);
        review.setBuildingType(BuildingType.Flat);
        review.setLivingSpace(100);
        review.setGarage(false);
        review.setCity("Kalisz");
        review.setStreet("Lechosławska 6");
        review.setPostCode("62-800");
        review.setImage_url("sample/image.jpg");
        review.setDeveloper(developer);
        meeting = new Meeting();
        meeting.setDate(new Date());
        meeting.setAddedByUser(true);
        meeting.setUser(user);
        review.addMeeting(meeting);
        review.addUser(user);

    }
    @Test
    public void testMeeting(){
        EntityManager entityManager = TestControllerDAO.getDAOManager();
        entityManager.getTransaction().begin();
        //entityManager.persist(user);
        entityManager.persist(developer);
        entityManager.persist(review);
        entityManager.getTransaction().commit();
        List<Meeting> list = entityManager.createQuery("select m from Meeting m").getResultList();
        entityManager.close();
        assertEquals(list.size(),1);
        Meeting readedMeeting = list.get(0);
        assertEquals(readedMeeting.getUser().getName(), "Nowak");
        assertEquals(readedMeeting.getUser().getFirstName(), "Antek");
        assertEquals(readedMeeting.getReview().getDeveloper().getEmail().getEmail(),"developer@wp.pl");
        assertEquals(readedMeeting.getReview().getTitle(), "testowe ogłoszenie");

    }

}