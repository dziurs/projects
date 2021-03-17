package app.dto;

import app.model.User;
import org.springframework.security.core.GrantedAuthority;

public class ConverterEntityToDTO {

    public UserDTO convertUserToUserDTO(User user){
        if(null!=user){
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail().getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setPasswordRepeat(user.getPassword());
        return userDTO;
        }else return null;
    }

    public AccountDTO convertUserToAccountDTO(User user){
        if(null!=user){
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setName(user.getName());
            accountDTO.setSurname(user.getSurname());
            accountDTO.setEmail(user.getEmail().getEmail());
            accountDTO.setStatus(user.isEnabled());
            for (GrantedAuthority authority : user.getAuthorities()){
                accountDTO.setRole(authority.getAuthority()); // for this application user has only one role
            }
            return accountDTO;
        }else return null;
    }
}
