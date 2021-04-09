package app.dto;

import app.model.Email;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "email", collectionRelation = "emails")
public class EmailDTO extends RepresentationModel<EmailDTO> {

    private String email;

    public EmailDTO(Email email) {
        this.email = email.getEmail();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
