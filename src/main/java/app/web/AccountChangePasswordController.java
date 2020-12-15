package app.web;

import app.endpoints.AdministratorEndpoint;
import app.exception.AccountException;
import app.exception.BuildingSalesAppException;
import app.exception.GeneralApplicationException;
import app.security.Crypter;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import java.util.ResourceBundle;

@RequestScoped
@Named(value = "changePasswordController")
public class AccountChangePasswordController {

    private String login;

    private String oldPassword;

    private String newPassword;

    private String passwordRepeat;

    private ResourceBundle bundle;

    @Inject
    private AdministratorEndpoint endpoint;

    @Inject
    public AccountChangePasswordController(SecurityContext securityContext) {
        this.login = securityContext.getCallerPrincipal().getName();
        this.bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("resourceBundle.path"),
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
    }

    public String change(){
        if(!validatePassword(newPassword,passwordRepeat)){
            addMessage(bundle.getString("page.buildingsales.account.new.password.validate"),bundle.getString("page.buildingsales.account.new.password.validate.details"),FacesMessage.SEVERITY_WARN);
            saveMessageInFlashScope();
            return "passwordChange";
        }
        try {
            String passOldFromDatabase = endpoint.findAccountPassByLogin(login);
            String oldPassFromForm = Crypter.crypt(oldPassword);
            if(passOldFromDatabase.equals(oldPassFromForm)){
                endpoint.setNewPasswordForAccount(login,newPassword);
                addMessage(bundle.getString("page.buildingsales.account.password.changed"),bundle.getString("page.buildingsales.account.password.changed.details"),FacesMessage.SEVERITY_INFO);
                saveMessageInFlashScope();
                return "passwordChange";
            }else {
                addMessage(bundle.getString("page.buildingsales.account.old.password.validate"),bundle.getString("page.buildingsales.account.old.password.validate.details"),FacesMessage.SEVERITY_ERROR);
                saveMessageInFlashScope();
                return "passwordChange";
            }
        }catch (AccountException e) {
            addMessage(bundle.getString(e.getMessage()),null,FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "passwordChange";
        }catch (GeneralApplicationException e) {
            addMessage(bundle.getString(e.getMessage()),null,FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "passwordChange";
        }catch (BuildingSalesAppException e) {
            addMessage(e.getMessage(),null,FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "passwordChange";
        }
    }

    private boolean validatePassword(String newPassword, String passwordRepeat){
        if(null == newPassword|| null== passwordRepeat) return false;
        return newPassword.equals(passwordRepeat);
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }
    private void addMessage(String message, String detail, FacesMessage.Severity f){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(f, message,detail));
    }
    private void saveMessageInFlashScope(){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }
}
