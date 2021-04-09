package app.dto;

import app.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "person", collectionRelation = "people")
public class PersonDTO extends RepresentationModel<PersonDTO> {

    private final static EmailDTOAssembler assembler = new EmailDTOAssembler();

    private String firstName;

    private String surname;

    private EmailDTO email;

    @Autowired
    public PersonDTO(Person person){
        this.firstName = person.getFirstName();
        this.surname = person.getSurname();
        this.email = assembler.toModel(person.getEmail());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public EmailDTO getEmail() {
        return email;
    }

    public void setEmail(EmailDTO email) {
        this.email = email;
    }
}
