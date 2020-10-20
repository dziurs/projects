package app.web;

import app.dto.DeveloperDTO;
import app.endpoints.BuildingSalesEndpoint;
import app.exception.EndpointException;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

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

    private ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages");

    public String register(){



        if(!passwordCheck()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    bundle.getString("register.developer.controller.password.check"),bundle.getString("register.developer.controller.password.detail")));
            return "";
        }
        if(conversation.isTransient())conversation.begin();
        return "successDeveloper";
    }
    public String registered(){
        try {
            endpoint.registerDeveloper(developerDTO, password);
        }catch (NoSuchAlgorithmException e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    bundle.getString("register.developer.controller.password.convert"),bundle.getString("register.developer.controller.detail")));
            return "registerDeveloper";
        }catch (EndpointException e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    bundle.getString("register.developer.controller.email.check"),bundle.getString("register.developer.controller.detail")));
            return "registerDeveloper";
        }
        if(!conversation.isTransient()) conversation.end();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                bundle.getString("register.developer.controller.success"),bundle.getString("register.developer.controller.success.detail")));
        return "index";
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
