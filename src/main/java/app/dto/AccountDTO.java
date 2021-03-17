package app.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AccountDTO {
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

    @NotEmpty(message = "{validation.not.null}")
    @NotNull(message = "{validation.not.null}")
    private String role;

    @NotNull(message = "{validation.not.null}")
    @Size(min = 2, max = 50, message = "{validation.name.size}")
    private boolean status;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
