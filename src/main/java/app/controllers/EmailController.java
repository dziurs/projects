package app.controllers;

import app.dto.EmailDTO;
import app.dto.EmailDTOAssembler;
import app.model.Email;
import app.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/email", produces = "application/json")
@CrossOrigin(origins = "*")
public class EmailController {

    private final static EmailDTOAssembler assembler = new EmailDTOAssembler();

    @Autowired
    private EmailRepository repository;

    @GetMapping
    public CollectionModel<EmailDTO> getAllEmails(){
        List<Email> emailList = repository.readAll();
        CollectionModel<EmailDTO> collectionModel = assembler.toCollectionModel(emailList);
        collectionModel.add(linkTo(methodOn(EmailController.class).getAllEmails()).withRel("emails"));
        return collectionModel;
    }
    @GetMapping(path = "/{id}")
    public EntityModel<EmailDTO> getPersonById(@PathVariable(name = "id") Long id){
        Email email = repository.read(id);
        EmailDTO emailDTO = assembler.toModel(email);
        EntityModel<EmailDTO> entityModel = EntityModel.of(emailDTO);
        return entityModel;
    }
}
