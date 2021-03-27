package app.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import app.dto.AccountDTO;
import app.dto.ConverterEntityToDTO;
import app.managers.UserManager;
import app.model.Email;
import app.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class UsersAccountsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserManager manager;

    @MockBean
    private ConverterEntityToDTO converterEntityToDTO;

    private List<User> userList;
    private User user;
    private AccountDTO accountDTO;

    @BeforeEach
    public void setUp(){
        userList = new ArrayList<>();
        user = new User();
        user.setName("TestName");
        user.setSurname("TestSurname");
        Email email = new Email();
        email.setEmail("test@test.com");
        user.setEmail(email);
        user.setPassword("aaaaaaaa");
        userList.add(user);
        accountDTO = new AccountDTO();
    }

    @Test
    @WithMockUser(username="testuser", password="testpass", authorities = "ROLE_ADMIN")
    void getAllUsers() throws Exception {
        when(manager.getAllUsers()).thenReturn(userList);
        when(converterEntityToDTO.convertUserToAccountDTO(any(User.class))).thenReturn(accountDTO);
        mvc.perform(get("/allUsers"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/usersList"))
                .andExpect(model().attributeExists("accountDTOList"))
                .andExpect(model().attributeExists("accountDTO"));
    }

    @Test
    @WithMockUser(username="testuser", password="testpass",authorities = "ROLE_ADMIN")
    void changeUserAccountStatus() throws Exception {
        doNothing().when(manager).changeAccountStatus(any(AccountDTO.class));
        mvc.perform(
                post("/allUsers").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(model().attributeDoesNotExist("accountDTO"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().stringValues("Location", "/allUsers"));
    }
}