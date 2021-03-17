package app.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTOForEdit {
    @NotNull(message = "{validation.not.null}")
    @Size(min = 2, max = 50, message = "{validation.name.size}")
    private String name;

    @NotNull(message = "{validation.not.null}")
    @Size(min = 2, max = 200, message = "{validation.surname.size}")
    private String surname;


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
}
