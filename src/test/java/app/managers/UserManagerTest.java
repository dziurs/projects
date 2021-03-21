package app.managers;

import app.exceptions.AppDatabaseException;
import app.model.Email;
import app.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application_test.properties")
@SpringBootTest
class UserManagerTest {

    @Autowired()
    private UserManager manager;

    private User user;
    private User secondUser;

    @BeforeEach
    public void setUp(){
        user = new User();
        user.setName("TestUserName");
        user.setSurname("TestUserSurname");
        Email email = new Email();
        email.setEmail("test@test.com");
        user.setEmail(email);
        user.setPassword("testtest");
        secondUser = new User();
        secondUser.setName("TestUserNameTwo");
        secondUser.setSurname("TestUserSurnameTwo");
        Email emailforSecondUser = new Email();
        emailforSecondUser.setEmail("test2@test.com");
        secondUser.setEmail(emailforSecondUser);
        secondUser.setPassword("testtest");
    }
    @Test
    @WithMockUser(username="testuser", password="testpass")
    void addUserAndFindUserByEmail() throws AppDatabaseException {
        manager.addUser(user);
        User userByEmail = manager.findUserByEmail(user.getEmail().getEmail());
        assertEquals(userByEmail.getEmail().getEmail(), user.getEmail().getEmail());
    }

    @Test
    @WithMockUser(username="testuser", password="testpass")
    void editUser() throws AppDatabaseException {
        manager.addUser(user);
        User userByEmail = manager.findUserByEmail(user.getEmail().getEmail());
        userByEmail.setName("TestNameAfterEdit");
        manager.editUser(userByEmail);
        User userByEmailAfterEdit = manager.findUserByEmail(user.getEmail().getEmail());
        assertEquals(userByEmail.getEmail().getEmail(), userByEmailAfterEdit.getEmail().getEmail());
        assertEquals(userByEmailAfterEdit.getName(),"TestNameAfterEdit" );
        assertNotEquals(user.getName(),userByEmailAfterEdit.getName());
    }

    @Test
    @WithMockUser(username="testuser", password="testpass")
    void deleteUser() {
        manager.addUser(user);
        manager.addUser(secondUser);
        List<User> allUsers = manager.getAllUsers();
        assertEquals(allUsers.size(),3); //admin user is third
        manager.deleteUser(user);
        List<User> users = manager.getAllUsers();
        assertEquals(users.size(),2);
        assertNotEquals(users.get(0).getName(),user.getName());
        try {
            manager.findUserByEmail("test@test.com");
        }catch (AppDatabaseException ex){
            assertEquals(ex.getMessage(),"exception.database.no.result.found");
        }
    }

    @Test
    void getAllUsers() {
        assertEquals(manager.getAllUsers().size(),1);
        manager.addUser(user);
        manager.addUser(secondUser);
        List<User> allUsers = manager.getAllUsers();
        assertEquals(allUsers.size(),3); //admin user is third
    }

}