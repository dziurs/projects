package app.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.Date;
import java.util.ResourceBundle;

@FacesValidator(value = "dateValidator")
public class MeetingDateValidator implements Validator {

    private ResourceBundle bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("resourceBundle.path"),
            FacesContext.getCurrentInstance().getViewRoot().getLocale());
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        Date dateToValidation = (Date) o;
        Date today = new Date();
        long oneDay = 24 * 60 * 60 * 1000;
        long tomorrowLong = today.getTime() + oneDay;
        long longToValidation = dateToValidation.getTime();
        if(longToValidation<=tomorrowLong){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, bundle.getString("date.validation.error.tomorrow"),null);
            throw new ValidatorException(message);
        }
    }
}
