package app.controllers;

import app.dto.EmailDTO;
import app.dto.PersonDTO;
import app.dto.PersonDTOAssembler;
import app.model.Email;
import app.model.Person;
import app.repositories.EmailRepository;
import app.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.hamcrest.Matchers;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    protected MockMvc mvc;

    @MockBean
    protected PersonRepository repository;

    @MockBean
    protected EmailRepository emailRepository;

    @Autowired
    private PersonController controller;

    @Autowired
    protected PersonDTOAssembler assembler;

    @Mock
    private Person mock;

    @Mock
    private Person afterUpdateMock;

    @Mock
    private Email afterUpdateEmailMock;

    @Mock
    private Email emailMock;

    @Mock
    private Email email;

    private Person person;

    private List<Person> people;

    @BeforeEach
    void setUp() {
        email = new Email();
        email.setEmail("test@test.pl");
        person = new Person();
        person.setFirstName("TestName");
        person.setSurname("TestSurname");
        people = new ArrayList<>();
        people.add(mock);
    }

    @Test
    void getAllPersons() throws Exception {
        when(repository.readAll()).thenReturn(people);
        prepareMock();
        mvc.perform(get("/person")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.people", Matchers.hasSize(1)))
                .andExpect(jsonPath("$._embedded.people[0].firstName", Matchers.is(person.getFirstName())));
    }

    @Test
    void getPersonById() throws Exception {
        when(repository.read(anyLong())).thenReturn(mock);
        prepareMock();
        mvc.perform(get("/person/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.surname", Matchers.is(mock.getSurname())))
                .andExpect(jsonPath("$.email.email", Matchers.is(emailMock.getEmail())))
                .andExpect(jsonPath("$.firstName", Matchers.is(mock.getFirstName())));
    }

    @Test
    void createPerson() throws Exception {
        when(repository.create(any(Person.class))).thenReturn(mock);
        prepareMock();
        controller.createPerson(mock);
        EntityModel<PersonDTO> personDTO = controller.createPerson(mock);
        assertEquals(personDTO.getContent().getFirstName(),mock.getFirstName());
        assertEquals(personDTO.getContent().getSurname(),mock.getSurname());
        assertEquals(personDTO.getContent().getEmail().getEmail(), emailMock.getEmail());
        mvc.perform(post("/person")
                .content("{\"firstName\" : \"Radek\" , \"surname\" : \"Radekrest\", \"email\" : {\"email\" : \"radek@rest.com.pl\"}}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void deletePerson() throws Exception {
        prepareMock();
        when(repository.read(anyLong())).thenReturn(mock);
        doNothing().when(repository).delete(mock);
        mvc.perform(delete("/person/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updatePerson() {
        prepareMock();
        PersonDTO personDTO = new PersonDTO(mock);
        personDTO.setSurname("afterUpdateSurename");
        personDTO.setFirstName("sfterUpdateName");
        EmailDTO emailDTO = personDTO.getEmail();
        emailDTO.setEmail("afterUpdate@email.com.pl");
        personDTO.setEmail(emailDTO);
        when(emailRepository.read(anyString())).thenReturn(emailMock);
        when(repository.read(anyLong())).thenReturn(mock);
        when(repository.update(mock)).thenReturn(afterUpdateMock);
        setAfterUpdateMock(personDTO,emailDTO);
        EntityModel<PersonDTO> entityModel = controller.updatePerson(1l, personDTO);
        assertEquals(entityModel.getContent().getSurname(),personDTO.getSurname());
        assertEquals(entityModel.getContent().getFirstName(),personDTO.getFirstName());
        assertEquals(entityModel.getContent().getEmail().getEmail(),personDTO.getEmail().getEmail());
    }

    private void prepareMock(){
        when(mock.getId()).thenReturn(100L);
        when(mock.getFirstName()).thenReturn(person.getFirstName());
        when(mock.getSurname()).thenReturn(person.getSurname());
        when(mock.getEmail()).thenReturn(emailMock);
        when(emailMock.getId()).thenReturn(100L);
        when(emailMock.getEmail()).thenReturn(email.getEmail());
    }
    private void setAfterUpdateMock(PersonDTO personDTO, EmailDTO emailDTO){
        when(afterUpdateMock.getId()).thenReturn(50L);
        when(afterUpdateMock.getFirstName()).thenReturn(personDTO.getFirstName());
        when(afterUpdateMock.getSurname()).thenReturn(personDTO.getSurname());
        when(afterUpdateMock.getEmail()).thenReturn(afterUpdateEmailMock);
        when(afterUpdateEmailMock.getId()).thenReturn(50L);
        when(afterUpdateEmailMock.getEmail()).thenReturn(emailDTO.getEmail());
    }


}