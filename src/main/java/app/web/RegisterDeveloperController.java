package app.web;

import app.dto.DeveloperDTO;
import app.endpoints.BuildingSalesEndpoint;
import app.exception.AccountException;
import app.exception.BuildingSalesAppException;
import app.exception.GeneralAplicationException;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@ConversationScoped
@Named(value = "registerDeveloper")
public class RegisterDeveloperController implements Serializable {

    @Inject
    private BuildingSalesEndpoint endpoint;

    @Inject
    private Conversation conversation;

    private DeveloperDTO developerDTO = new DeveloperDTO();

    private String password;

    private String passwordRepeat;

    private final String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    private ResourceBundle bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("resourceBundle.path"),
            FacesContext.getCurrentInstance().getViewRoot().getLocale());

    public String register(){

        if(!passwordCheck()){
            addWarningMessage(bundle.getString("register.developer.controller.password.check"),bundle.getString("register.developer.controller.password.detail"));
            return "";
        }
        if(conversation.isTransient())conversation.begin();
        return "successDeveloper";
    }
    public String registered(){
        try {
            endpoint.registerDeveloper(developerDTO, password);
            if(!conversation.isTransient()) conversation.end();
            addMessage(bundle.getString("register.developer.controller.success"),bundle.getString("register.developer.controller.success.detail"));
            saveMessageInFlashScope();
            return "index";
        }catch (GeneralAplicationException e){
            if(!conversation.isTransient()) conversation.end();
            addWarningMessage(bundle.getString("register.developer.controller.password.convert"),bundle.getString("register.developer.controller.detail"));
            saveMessageInFlashScope();
            return "registerDeveloper";
        }catch (AccountException e){
            if(!conversation.isTransient()) conversation.end();
            addWarningMessage(bundle.getString(e.getMessage()),bundle.getString("register.developer.controller.detail"));
            saveMessageInFlashScope();
            return "registerDeveloper";
        }catch (BuildingSalesAppException e){
            Logger.getLogger(RegisterDeveloperController.class.getName()).log(Level.SEVERE, "Zgłoszenie w metodzie akcji 'registered' wyjatku typu: ", e.getClass());
            return "registerDeveloper";
        }
    }
    private void addWarningMessage(String message, String detail){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message,detail));
    }
    private void addMessage(String message, String detail){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message,detail));
    }
    private void saveMessageInFlashScope(){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    public DeveloperDTO getDeveloperDTO() {
        return developerDTO;
    }

    public void setDeveloperDTO(DeveloperDTO developerDTO) {
        this.developerDTO = developerDTO;
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

    private boolean passwordCheck(){
        return password.equals(this.getPasswordRepeat());
    }

    public String getRegex() {
        return regex;
    }

}
