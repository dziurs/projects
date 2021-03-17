package app.validators;

import app.dto.UserDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class UserDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDTO userDTO = (UserDTO)o;
        String password = userDTO.getPassword();
        String passwordRepeat = userDTO.getPasswordRepeat();
        if(!password.equals(passwordRepeat)){
            errors.rejectValue("password","validation.password.and.password.repeat");
            errors.rejectValue("passwordRepeat","validation.password.and.password.repeat");
        }
    }
}
