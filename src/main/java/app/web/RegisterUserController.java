package app.web;

import app.dto.UserDTO;
import app.endpoints.BuildingSalesEndpoint;
import app.endpoints.EmailSendingEndpoint;
import app.exception.BuildingSalesAppException;
import app.exception.EmailSendingException;
import app.exception.AccountException;
import app.exception.GeneralApplicationException;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@ConversationScoped
@Named(value = "registerUser")
public class RegisterUserController implements Serializable {

    @Inject
    private BuildingSalesEndpoint endpoint;

    @Inject
    private Conversation conversation;

    @Inject
    private EmailSendingEndpoint emailSendingEndpoint;

    private final String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    private UserDTO userDTO = new UserDTO();

    @Size(min = 8, max = 20)
    private String password;

    private StringBuilder builder;

    @Size(min = 8, max = 20)
    private String passwordRepeat;

    private String emailParam;

    private String pidParam;

    private ResourceBundle bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("resourceBundle.path"),
            FacesContext.getCurrentInstance().getViewRoot().getLocale());

    public String register(){
        if(!password.equals(this.getPasswordRepeat())){
            addMessage(bundle.getString("register.developer.controller.password.check"),bundle.getString("register.developer.controller.password.detail"),FacesMessage.SEVERITY_WARN);
            return "";
        }
        if(conversation.isTransient())conversation.begin();
        return "successUser";
    }
    private String emailSending() throws EmailSendingException {
        emailSendingEndpoint.sendEmail(getUserDTO().getEmail(), builder.toString());
        if (!conversation.isTransient()) conversation.end();
        addMessage(bundle.getString("register.user.controller.success"),bundle.getString("register.user.controller.success.detail"), FacesMessage.SEVERITY_INFO);
        saveMessageInFlashScope();
        return "index";
    }
    private void addMessage(String message, String detail, FacesMessage.Severity f){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(f, message,detail));
    }
    private void saveMessageInFlashScope(){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    public String registered() {
        try {
            int pid = endpoint.registerUser(getUserDTO(), getPassword());
            builder = new StringBuilder(emailSendingEndpoint.getAppContext());
            builder.append("/accounts/activateUser.xhtml?emailparam=");
            builder.append(userDTO.getEmail());
            builder.append("&pidparam=");
            builder.append(pid);
            return emailSending();

        } catch (GeneralApplicationException e) {
            if (!conversation.isTransient()) conversation.end();
            addMessage(bundle.getString("register.developer.controller.password.convert"), bundle.getString("register.developer.controller.detail"), FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "registerUser";
        } catch (AccountException e) {
            if (!conversation.isTransient()) conversation.end();
            addMessage(bundle.getString(e.getMessage()), bundle.getString("register.developer.controller.detail"), FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "registerUser";
        }catch(BuildingSalesAppException e){
            Logger.getLogger(RegisterUserController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji 'registered' wyjatku typu: ", e.getClass());
            return "registerUser";
        }
        catch (EmailSendingException e) {
            try {
                return emailSending();
            } catch (EmailSendingException ex) {
                if (!conversation.isTransient()) conversation.end();
                addMessage(bundle.getString(ex.getMessage()), bundle.getString("register.user.controller.email.sending.error.detail"), FacesMessage.SEVERITY_ERROR);
                saveMessageInFlashScope();
                return "index";
            }
        }
    }

    public String validateUser(){
        try {
            endpoint.activateUserAccount(getEmailParam(), getPidParam());
            addMessage(bundle.getString("activate.user.done"),bundle.getString("activate.user.done.detail"), FacesMessage.SEVERITY_INFO);
            saveMessageInFlashScope();
            return "index";
        }catch (EmailSendingException e){
            addMessage(bundle.getString(e.getMessage()), bundle.getString("register.user.controller.email.sending.error.detail"), FacesMessage.SEVERITY_WARN);
            saveMessageInFlashScope();
            return "index";
        }
    }

    public String getRegex() {
        return regex;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public String getEmailParam() {
        return emailParam;
    }

    public void setEmailParam(String emailParam) {
        this.emailParam = emailParam;
    }

    public String getPidParam() {
        return pidParam;
    }

    public void setPidParam(String pidParam) {
        this.pidParam = pidParam;
    }
}
