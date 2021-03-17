package app.managers;

import app.dto.AccountDTO;
import app.exceptions.AppDatabaseException;
import app.loggers.LogApp;
import app.model.User;
import app.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UserManager {

    @Autowired
    private UserDAO repository;

    @LogApp
    public User findUserByEmail(String email) throws AppDatabaseException {
        return repository.read(email);
    }
    @LogApp
    public void addUser(User user){
        repository.create(user);
    }

    @LogApp
    public void editUser(User user) {
        repository.update(user);
    }

    @LogApp
    public void deleteUser(User user) {
        repository.delete(user);
    }

    @LogApp
    public List<User> getAllUsers(){
        return repository.readAllUsers();
    }

    @LogApp
    public void changeAccountStatus(AccountDTO accountDTO) throws AppDatabaseException {
        try {
            User user = repository.read(accountDTO.getEmail());
            user.setStatus(accountDTO.getStatus());
            repository.update(user);
        } catch (AppDatabaseException e) {
            throw e;
        }
    }
}
