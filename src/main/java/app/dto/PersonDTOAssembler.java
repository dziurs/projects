package app.dto;

import app.controllers.PersonController;
import app.model.Person;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PersonDTOAssembler extends RepresentationModelAssemblerSupport<Person,PersonDTO> {

    public PersonDTOAssembler() {
        super(PersonController.class, PersonDTO.class);
    }

    @Override
    protected PersonDTO instantiateModel(Person entity) {
        return new PersonDTO(entity);
    }

    @Override
    public PersonDTO toModel(Person entity) {
        return createModelWithId(entity.getId(),entity);
    }
}
