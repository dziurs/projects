package app;

import app.model.Email;
import app.model.Person;
import app.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitData {

    @Autowired
    private PersonRepository repository;

    @PostConstruct
    public void init(){
        Email email = new Email();
        email.setEmail("test@test.pl");
        Person person = new Person();
        person.setFirstName("TestName");
        person.setSurname("TestSurname");
        person.setEmail(email);
        repository.create(person);
    }
}
