package app.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import app.dto.PersonDTO;
import app.dto.PersonDTOAssembler;
import app.model.Email;
import app.model.Person;
import app.repositories.EmailRepository;
import app.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/person", produces = "application/json")
@CrossOrigin(origins = "*")
public class PersonController {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private PersonDTOAssembler assembler;

    @GetMapping
    public CollectionModel<PersonDTO> getAllPersons(){
        List<Person> people = repository.readAll();
        CollectionModel<PersonDTO> collectionModel = assembler.toCollectionModel(people);
        collectionModel.add(linkTo(methodOn(PersonController.class).getAllPersons()).withRel("people"));
        return collectionModel;
    }
    @GetMapping(path = "/{id}")
    public EntityModel<PersonDTO> getPersonById(@PathVariable(name = "id") Long id){
        Person person = repository.read(id);
        PersonDTO personDTO = assembler.toModel(person);
        EntityModel<PersonDTO> entityModel = EntityModel.of(personDTO);
        return entityModel;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<PersonDTO> createPerson(@RequestBody Person person){
        Person persisted = repository.create(person);
        PersonDTO personDTO = assembler.toModel(persisted);
        return EntityModel.of(personDTO);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable(name = "id") Long id){
        Person person = repository.read(id);
        repository.delete(person);
    }

    @PutMapping(path ="/{id}", consumes = "application/json")
    public EntityModel<PersonDTO> updatePerson(@PathVariable(name = "id") Long id, @RequestBody PersonDTO personDTO){
        Person person= repository.read(id);
        if(personDTO.getFirstName()!=null) person.setFirstName(personDTO.getFirstName());
        else if(personDTO.getSurname()!=null) person.setSurname(personDTO.getSurname());
        else if(personDTO.getEmail()!=null){
            Email email = emailRepository.read(personDTO.getEmail().getEmail());
            email.setEmail(personDTO.getEmail().getEmail());
            person.setEmail(email);
        }
        Person updated = repository.update(person);
        PersonDTO personDTOAfterUpdate = assembler.toModel(updated);
        return EntityModel.of(personDTOAfterUpdate);

    }


}
