package app.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {


    @NotNull(message = "{validation.not.null}")
    @Size(min = 2, max = 50, message = "{validation.name.size}")
    private String name;

    @NotNull(message = "{validation.not.null}")
    @Size(min = 2, max = 200, message = "{validation.surname.size}")
    private String surname;

    @NotEmpty(message = "{validation.not.null}")
    @NotNull(message = "{validation.not.null}")
    @Email(message = "{validation.email.pattern}")
    private String email;

    @NotNull(message = "{validation.not.null}")
    @Size(min = 2, max = 50,message = "{validation.password.size}")
    private String password;

    @NotNull(message = "{validation.not.null}")
    @Size(min = 2, max = 50, message = "{validation.password.size}")
    private String passwordRepeat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }
}
