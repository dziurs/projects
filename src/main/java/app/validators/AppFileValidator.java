package app.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;
import java.util.ResourceBundle;

@FacesValidator(value = "fileValidator")
public class AppFileValidator implements Validator {

    private ResourceBundle bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("resourceBundle.path"),
            FacesContext.getCurrentInstance().getViewRoot().getLocale());
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        Part file = (Part)o;
        if(file.getSize()>1048576||file.getSize()<204800){
            FacesMessage message = new FacesMessage(bundle.getString("file.validation.error.size"));
            throw new ValidatorException(message);
        }
        if((!"image/png".equals(file.getContentType()))&&(!"image/jpeg".equals(file.getContentType()))){
            FacesMessage message = new FacesMessage(bundle.getString("file.validation.error.content"));
            throw new ValidatorException(message);
        }
    }
}
