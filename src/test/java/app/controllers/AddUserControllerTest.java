package app.controllers;

import app.dto.ConverterDTOToEntity;
import app.dto.UserDTO;
import app.managers.UserManager;
import app.model.Email;
import app.model.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class AddUserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @MockBean
    private ConverterDTOToEntity converterDTOToEntity;

    @MockBean
    private UserManager manager;

    private User user;

    @BeforeEach
    public void setup(){
        user = new User();
        user.setName("TestName");
        user.setSurname("TestSurname");
        Email email = new Email();
        email.setEmail("test@test.com");
        user.setEmail(email);
        user.setPassword("aaaaaaaa");
    }

    @Test
    public void registerForm() throws Exception {
        mvc.perform(get("/registerUser"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/addUser"))
                .andExpect(model().attributeExists("userDTO"))
                .andExpect(content().string(Matchers.containsString(messageSourceAccessor.getMessage("spring.content.big.add.user.title"))));
    }

    @Test
    public void addUser() throws Exception {
        when(converterDTOToEntity.simplyConvertUserDTOToUser(any(UserDTO.class))).thenReturn(user);
        doNothing().when(manager).addUser(user);
        mvc.perform(
                post("/registerUser").with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content("name=TestName&surname=TestSurname&email=test@test.com&password=aaaaaaaa&passwordRepeat=aaaaaaaa")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        )
            .andExpect(model().attributeDoesNotExist("userDTO"))
            .andExpect(status().is3xxRedirection())
            .andExpect(header().stringValues("Location", "/home"));

    }
}