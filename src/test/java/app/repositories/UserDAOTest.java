package app.repositories;

import app.exceptions.AppDatabaseException;
import app.managers.UserManager;
import app.model.Email;
import app.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class UserDAOTest {

    @Autowired
    private UserManager manager;

    @MockBean
    private UserDAO repository;

    private List<User> userList;
    private User user;

    @BeforeEach
    void setUp() throws AppDatabaseException {
        user = new User();
        user.setName("TestUserName");
        user.setSurname("TestUserSurname");
        Email email = new Email();
        email.setEmail("test@test.com");
        user.setEmail(email);
        userList = new ArrayList<>();
        userList.add(user);
        when(repository.read(user.getEmail().getEmail())).thenReturn(user);
        when(repository.read(1)).thenReturn(user);
        when(repository.readAllUsers()).thenReturn(userList);
        doNothing().when(repository).create(user);
        doNothing().when(repository).update(user);
        doNothing().when(repository).delete(user);

    }

    @Test
    void create() {
        manager.addUser(user);
        verify(repository,times(1)).create(user);
    }

    @Test
    void read() {
        repository.read(1);
        assertEquals(user.getName(),"TestUserName");
        assertEquals(user.getEmail().getEmail(),"test@test.com");
    }

    @Test
    void readAllUsers() {
        List<User> allUsers = manager.getAllUsers();
        assertArrayEquals(userList.toArray(),allUsers.toArray());
        assertEquals(allUsers.size(),1);
    }

    @Test
    void readUserByEmail() throws AppDatabaseException {
        User readedUser = manager.findUserByEmail("test@test.com");
        assertSame(user, readedUser);
        assertEquals(user.getName(),readedUser.getName());
    }

    @Test
    void update() {
        manager.editUser(user);
        verify(repository,times(1)).update(user);
    }

    @Test
    void delete() {
        manager.deleteUser(user);
        verify(repository,times(1)).delete(user);
    }
}