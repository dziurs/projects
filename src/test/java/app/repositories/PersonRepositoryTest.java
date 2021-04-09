package app.repositories;

import app.model.Email;
import app.model.Person;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    private Person person;

    @BeforeEach
    public void initData(){
        Email email = new Email();
        email.setEmail("test2@test.pl");
        person = new Person();
        person.setFirstName("TestName");
        person.setSurname("TestSurname");
        person.setEmail(email);
    }

    @Order(1)
    @Test()
    void create() {
        repository.create(person);
        assertEquals(repository.readAll().size(),2);
    }

    @Order(2)
    @Test
    void read() {
        Person personReaded = repository.read(2);
        assertNotNull(personReaded);
        assertEquals(personReaded.getFirstName(), person.getFirstName());
    }
    @Order(3)
    @Test
    void readAll() {
        assertEquals(repository.readAll().size(),2);
    }
    @Order(4)
    @Test
    void readbyEmail() {
       assertNotNull(repository.read("test2@test.pl"));
       assertEquals(repository.read("test2@test.pl").getEmail().getEmail(),"test2@test.pl");
    }
    @Order(5)
    @Test
    void update() {
        Person read = repository.read(2);
        read.setSurname("newSurname");
        repository.update(read);
        assertEquals(repository.read(2).getSurname(),"newSurname");
    }
    @Order(6)
    @Test
    void delete() {
        assertEquals(repository.readAll().size(),2);
        Person read = repository.read(2);
        repository.delete(read);
        assertEquals(repository.readAll().size(),1);
    }
}