package app.dto;

import app.controllers.EmailController;
import app.model.Email;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class EmailDTOAssembler extends RepresentationModelAssemblerSupport<Email,EmailDTO> {

    public EmailDTOAssembler() {
        super(EmailController.class, EmailDTO.class);
    }

    @Override
    public EmailDTO toModel(Email entity) {
        return createModelWithId(entity.getId(),entity);
    }

    @Override
    protected EmailDTO instantiateModel(Email entity) {
        return new EmailDTO(entity);
    }
}
