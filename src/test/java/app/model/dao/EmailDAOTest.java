package app.model.dao;

import app.model.entity.Email;
import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.jboss.weld.junit5.auto.ActivateScopes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@EnableWeld
@ActivateScopes({RequestScoped.class, ApplicationScoped.class})
public class EmailDAOTest {

    @WeldSetup
    WeldInitiator weldInitiator = WeldInitiator.from(EntityManagerProducer.class,EmailDAO.class).activate(RequestScoped.class).build();
    @Inject
    public EmailDAO emailDAO; //injected by CDI

    public EntityManager entityManager;
    public Email email;
    public Email emailSecond;
    public Email emailThird;
    public Email emailFourth;
    public final String MAINEMAIL= "dziur@outlook.com";
    private final int MINRANGE = 1;
    private final int MAXRANGE = 3;
    private final int MINRANGEMINUSONE = 0;
    private final int MAXRANGEMINUSONE = 2;


    @BeforeEach
    public  void initObject(){
        //this.entityManager = entityManagerFactory.createEntityManager();
        //entityManager = TestControllerDAO.getDAOManager();
        email = new Email();
        email.setEmail("dziur@outlook.com");
        emailSecond = new Email();
        emailSecond.setEmail("dziur2@outlook.com");
        emailThird = new Email("dziur3@outlook.com");
        emailFourth = new Email("dziur4@outlook.com");
        //emailDAO = new EmailDAO(entityManager);
        this.entityManager = emailDAO.getEntityManager(); //emailDAO has injected entityManager from EntityManagerProducer
    }
    @Test
    public void getEntityManager() {
        assertNotNull(emailDAO.getEntityManager());
        assertEquals(emailDAO.getEntityManager(), entityManager);
        System.out.println(entityManager);
    }
    @Test
    public void create(){
        entityManager.getTransaction().begin();
        emailDAO.create(email);
        entityManager.getTransaction().commit();
        List<Email> list = entityManager.createQuery("select e from Email e").getResultList();
        Email emailSQL = list.get(0);
        assertEquals(email.getEmail(),emailSQL.getEmail());

    }
    @Test
    public void findByID() {
        entityManager.getTransaction().begin();
        emailDAO.create(email);
        entityManager.getTransaction().commit();
        Email byID = emailDAO.findByID(1);
        assertNotNull(byID);
        assertEquals(byID,email);
        assertEquals(byID.getEmail(),email.getEmail());
    }

    @Test
    public void findByEmail() {
        entityManager.getTransaction().begin();
        emailDAO.create(email);
        entityManager.getTransaction().commit();
        Email byEmail = emailDAO.findByEmail(MAINEMAIL);
        assertNotNull(byEmail);
        assertEquals(email, byEmail);
        assertEquals(email.getEmail(),byEmail.getEmail());
    }
    @Test
    public void update(){
        entityManager.getTransaction().begin();
        emailDAO.create(email);
        entityManager.getTransaction().commit();
        Email firstReadEmail = emailDAO.findByID(1);
        assertNotNull(firstReadEmail);
        assertEquals(email, firstReadEmail);
        firstReadEmail.setEmail("emailpozmianie@test.pl");
        emailDAO.update(firstReadEmail);
        Email secondReadEmail = emailDAO.findByID(1);
        assertEquals("emailpozmianie@test.pl",secondReadEmail.getEmail());
        assertEquals(firstReadEmail.getId(),secondReadEmail.getId(), "Actual object has the same id ");
        assertNotEquals(email.getEmail().getBytes(),secondReadEmail.getEmail().getBytes(),"Actual object has the same id but different property email");
    }
    @Test
    public void delete(){
        entityManager.getTransaction().begin();
        emailDAO.create(email);
        entityManager.getTransaction().commit();
        Email firstReadEmail = emailDAO.findByID(1);
        assertNotNull(firstReadEmail);
        assertEquals(email, firstReadEmail);
        entityManager.getTransaction().begin();
        emailDAO.delete(firstReadEmail);
        entityManager.getTransaction().commit();
        List<Email> list = emailDAO.readAll();
        assertEquals(0,list.size());
    }
    @Test
    public void readRange(){
        entityManager.getTransaction().begin();
        emailDAO.create(email);
        emailDAO.create(emailSecond);
        emailDAO.create(emailThird);
        emailDAO.create(emailFourth);
        entityManager.getTransaction().commit();
        List<Email> firstList = emailDAO.readRange(MINRANGE, MAXRANGE);
        assertTrue(firstList.size()==3);
        assertEquals(emailSecond.getEmail(),firstList.get(0).getEmail());
        assertTrue(emailSecond.getId()==2);
        assertEquals(emailThird.getEmail(),firstList.get(1).getEmail());
        assertTrue(emailThird.getId()==3);
        assertEquals(emailFourth.getEmail(),firstList.get(2).getEmail());
        assertTrue(emailFourth.getId()==4);
        List<Email> secondList = emailDAO.readRange(MINRANGEMINUSONE, MAXRANGEMINUSONE);
        assertTrue(secondList.size()==3);
        assertEquals(email.getEmail(),secondList.get(0).getEmail());
        assertTrue(email.getId()==1);
        assertEquals(emailSecond.getEmail(),secondList.get(1).getEmail());
        assertTrue(emailSecond.getId()==2);
        assertEquals(emailThird.getEmail(),secondList.get(2).getEmail());
        assertTrue(emailThird.getId()==3);
    }
    @Test
    public void countEntities(){
        entityManager.getTransaction().begin();
        emailDAO.create(email);
        emailDAO.create(emailSecond);
        emailDAO.create(emailThird);
        emailDAO.create(emailFourth);
        entityManager.getTransaction().commit();
        long count = emailDAO.count();
        assertTrue(count==4);
    }

}