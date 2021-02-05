package app.security;

import app.exception.BuildingSalesAppException;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ResourceBundle;

@RequestScoped
@Named(value = "loginController")
public class LoginController {

    @NotNull
    @Size(min = 5, max = 50, message = "Username must be between 3 and 50 characters")
    private String userName;

    @NotNull
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;

    @Inject
    private SecurityContext securityContext;

    private ResourceBundle bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("resourceBundle.path"),
            FacesContext.getCurrentInstance().getViewRoot().getLocale());


    public String login(){
        try {
            String cryptedPassword = Crypter.crypt(password);
            Credential credential = new UsernamePasswordCredential(userName, new Password(cryptedPassword));
            AuthenticationStatus authenticate = securityContext.authenticate(getRequest(), getResponse(), AuthenticationParameters.withParams().credential(credential));
                if (authenticate.equals(AuthenticationStatus.SEND_CONTINUE)) {
                    FacesContext.getCurrentInstance().responseComplete();
                    return "";
                }else if (authenticate.equals(AuthenticationStatus.SEND_FAILURE)) {
                    addMessage(bundle.getString("page.login.error"),null);
                    return "";
                }
                return "index";
            }catch (BuildingSalesAppException e){
            addMessage(e.getMessage(),null);
            return "";
        }
    }
    public String logout(){
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if(session!= null) session.invalidate();
        return "index";
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private HttpServletResponse getResponse() {
        return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }
    private void addMessage(String message, String detail){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message,detail));
    }
}
