package app.web;

import app.endpoints.AdministratorEndpoint;
import app.endpoints.BuildingSalesEndpoint;
import app.endpoints.EmailSendingEndpoint;
import app.exception.AccountException;
import app.exception.BuildingSalesAppException;
import app.exception.EmailSendingException;
import app.security.Account;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ResourceBundle;

@SessionScoped
@Named(value = "passwordResetController")
public class PasswordResetController implements Serializable {

    @Inject
    private EmailSendingEndpoint emailSendingEndpoint;

    @Inject
    private BuildingSalesEndpoint endpoint;

    @Inject
    private AdministratorEndpoint administratorEndpoint;

    private final String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    private String login;

    private int pid;

    private String emailParam;

    private String pidParam;

    private String sessionId = "none";

    private String password;

    private String passwordRepeat;

    private ResourceBundle bundle;

    @PostConstruct
    public void init() {
        this.bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("resourceBundle.path"),
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
    }


    public String passwordReset() {
        try {
            FacesContext currentInstance = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) currentInstance.getExternalContext().getRequest();
            HttpSession session = request.getSession();
            sessionId = session.getId();
            Account account = endpoint.findAccountByLogin(login);
            pid = account.getPid();
            HttpServletResponse response = (HttpServletResponse) currentInstance.getExternalContext().getResponse();
            StringBuilder builder = new StringBuilder(emailSendingEndpoint.getAppContext());
            builder.append("/" + response.encodeRedirectURL("accounts/resetPass.xhtml"));
            builder.append("?emailparam=");
            builder.append(account.getLogin());
            builder.append("&pidparam=");
            builder.append(pid);
            String url = builder.toString();
            emailSendingEndpoint.sendResetPasswordEmail(login, url);
            return "index";
        } catch (AccountException ex) {
            addMessage(bundle.getString(ex.getMessage()), null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "index";
        } catch (BuildingSalesAppException ex) {
            addMessage(bundle.getString(ex.getMessage()), null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "index";
        } catch (EmailSendingException ex) {
            addMessage(bundle.getString(ex.getMessage()), null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "index";
        }


    }

    public String validateAccount() {
        if (sessionId.equals("none")) {
           return killSessionDuringErrorAndSendMessage();
        } else {
            if (login.equals(emailParam) && Integer.parseInt(pidParam) == pid) {
                return "insertNewPass";
            } else {
               return killSessionDuringErrorAndSendMessage();
            }

        }

    }
    public String changePassword(){
        if(sessionId.equals("none")){
            return killSessionDuringErrorAndSendMessage();
        }else{
            if(validatePasswords(password,passwordRepeat)){
                try{
                    administratorEndpoint.setNewPasswordForAccount(login,password);
                    return killSessionAndSendMessage();
                }catch(AccountException ex){
                    return killSessionDuringExceptionAndSendMessage(ex);
                }catch(BuildingSalesAppException ex){
                    return killSessionDuringExceptionAndSendMessage(ex);
                }
            }
            else{
                addMessage(bundle.getString("page.buildingsales.password.reset.exception"),
                        bundle.getString("page.buildingsales.password.reset.validation.pass.details"),FacesMessage.SEVERITY_ERROR);
                saveMessageInFlashScope();
                password=null;
                passwordRepeat=null;
                return "insertNewPass";
            }
        }
    }

    public String getRegex() {
        return regex;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    private void addMessage(String message, String detail, FacesMessage.Severity f) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(f, message, detail));
    }

    private void saveMessageInFlashScope() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    private String killSessionDuringErrorAndSendMessage() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session != null) session.invalidate();
        addMessage(bundle.getString(bundle.getString("page.buildingsales.password.reset.session.timeout")), bundle.getString("page.buildingsales.password.reset.session.timeout.details"), FacesMessage.SEVERITY_INFO);
        saveMessageInFlashScope();
        return "index";
    }
    private String killSessionAndSendMessage() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session != null) session.invalidate();
        addMessage(bundle.getString(bundle.getString("page.buildingsales.password.reset.success")), bundle.getString("page.buildingsales.password.reset.success.details"), FacesMessage.SEVERITY_INFO);
        saveMessageInFlashScope();
        return "index";
    }

    private String killSessionDuringExceptionAndSendMessage(Throwable ex) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session != null) session.invalidate();
        addMessage(bundle.getString(bundle.getString(ex.getMessage())), bundle.getString("page.buildingsales.password.reset.exception.details"), FacesMessage.SEVERITY_ERROR);
        saveMessageInFlashScope();
        return "index";
    }
    private boolean validatePasswords(String password, String passwordRepeat){
        if(null!=password&&null!=passwordRepeat){
            return password.equals(passwordRepeat);
        }
        else return false;
    }

}
