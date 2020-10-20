package app.web;

import app.dto.UserDTO;
import app.endpoints.BuildingSalesEndpoint;
import app.endpoints.EmailSendingEndpoint;
import app.exception.EndpointException;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

@RequestScoped
@Named(value = "registerUser")
public class RegisterUserController {

    @Inject
    private BuildingSalesEndpoint endpoint;

    @Inject
    private Conversation conversation;

    @Inject
    private EmailSendingEndpoint emailSendingEndpoint;

    private final String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    private UserDTO userDTO = new UserDTO();

    private String password;

    private String passwordRepeat;

    private String emailParam;

    private String pidParam;

    private ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages");

    public String register(){
        if(!password.equals(this.getPasswordRepeat())){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    bundle.getString("register.developer.controller.password.check"),bundle.getString("register.developer.controller.password.detail")));
            return "";
        }
        if(conversation.isTransient())conversation.begin();
        return "successUser";
    }
    public String registered() {
        try {
            int pid = endpoint.registerUser(userDTO, password);
            StringBuilder builder = new StringBuilder(emailSendingEndpoint.getAppContext());
            builder.append("/activate?emailparam=");
            builder.append(userDTO.getEmail());
            builder.append("&pidparam=");
            builder.append(pid);

        } catch (NoSuchAlgorithmException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    bundle.getString("register.developer.controller.password.convert"), bundle.getString("register.developer.controller.detail")));
            return "registerUser";
        } catch (EndpointException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    bundle.getString("register.developer.controller.email.check"), bundle.getString("register.developer.controller.detail")));
            return "registerUser";
        }
        if (!conversation.isTransient()) conversation.end();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                bundle.getString("register.developer.controller.success"), bundle.getString("register.user.controller.success.detail")));
        return "index";
    }
    public String validateUser(){
        //TODO w endpoint dodać metodę która zweryfikuje email i pid w Account,
        // wczytać po emailu i z encji wyjąć pid, jak equals to account.setActivate
        return "index";
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
