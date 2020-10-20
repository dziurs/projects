package app.dto;

public class UserDTO {
    private String surname;
    private String firstName;
    private String email;

    public UserDTO(String surname, String firstName, String email) {
        this.surname = surname;
        this.firstName = firstName;
        this.email = email;
    }

    public UserDTO() {
    }
    public String getSurname() {
        return surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
