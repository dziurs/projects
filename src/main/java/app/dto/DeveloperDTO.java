package app.dto;

public class DeveloperDTO {
    private String surname;
    private String firstName;
    private String companyName;
    private String email;

    public DeveloperDTO(String surname, String firstName, String companyName, String email) {
        this.surname = surname;
        this.firstName = firstName;
        this.companyName = companyName;
        this.email = email;
    }
    public DeveloperDTO(){

    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getEmail() {
        return email;
    }
}
