package app.dto;

import app.exceptions.AppDatabaseException;
import app.managers.UserManager;
import app.model.Email;
import app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ConverterDTOToEntity {

    @Autowired
    private UserManager manager;

    @Autowired
    private PasswordEncoder encoder;

    public User simplyConvertUserDTOToUser(UserDTO userDTO) {
        return justConvert(userDTO);
    }

    public User convertUserDTOToUser(UserDTO userDTO) {
        try {
            User userFromDatabase = manager.findUserByEmail(userDTO.getEmail());
            userFromDatabase.setName(userDTO.getName());
            userFromDatabase.setSurname(userDTO.getSurname());
            userFromDatabase.setPassword(encoder.encode(userDTO.getPassword()));
            return userFromDatabase;
        } catch (AppDatabaseException e) {
            return justConvert(userDTO);
        }
    }

    private User justConvert(UserDTO userDTO){
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        Email email = new Email();
        email.setEmail(userDTO.getEmail());
        user.setEmail(email);
        user.setPassword(encoder.encode(userDTO.getPassword()));
        return user;
    }
}
