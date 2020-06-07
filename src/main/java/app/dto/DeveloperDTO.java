package app.dto;

public class DeveloperDTO {
    private String name;
    private String firstName;
    private String companyName;
    private String email;

    public DeveloperDTO(String name, String firstName, String companyName, String email) {
        this.name = name;
        this.firstName = firstName;
        this.companyName = companyName;
        this.email = email;
    }

    public String getName() {
        return name;
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
