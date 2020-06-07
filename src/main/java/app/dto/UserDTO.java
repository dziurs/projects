package app.dto;

public class UserDTO {
    private String name;
    private String firstName;
    private String email;

    public UserDTO(String name, String firstName, String email) {
        this.name = name;
        this.firstName = firstName;
        this.email = email;
    }
    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }
}
