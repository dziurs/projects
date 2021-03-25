package app.web;

import app.dto.AccountDTO;
import app.endpoints.AdministratorEndpoint;
import app.exception.AccountException;
import app.exception.BuildingSalesAppException;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

@RequestScoped
@Named(value = "activateUserAccountController")
public class ActivateUserAccountController implements Serializable {
    @Inject
    AdministratorEndpoint endpoint;

    private List<AccountDTO> accountList;

    private ResourceBundle bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("resourceBundle.path"),
            FacesContext.getCurrentInstance().getViewRoot().getLocale());

    @PostConstruct
    public void init(){
        setAccountList(endpoint.getUserAccounts());
    }

    public List<AccountDTO> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<AccountDTO> accountList) {
        this.accountList = accountList;
    }
    public String activate(AccountDTO a){
        try {
            endpoint.activateAccount(a);
            return "activateUserAccount";

        }catch (AccountException e) {
            addMessage(bundle.getString(e.getMessage()),null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "activateUserAccount";
        }catch (BuildingSalesAppException e) {
            addMessage(e.getCause().getMessage(),null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "activateUserAccount";
        }
    }
    public String  deactivate(AccountDTO a){
        try {
            endpoint.deactivateAccount(a);
            return "activateUserAccount";
        }catch (AccountException e) {
            addMessage(bundle.getString(e.getMessage()),null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "activateUserAccount";
        } catch (BuildingSalesAppException e) {
            addMessage(e.getCause().getMessage(),null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "activateUserAccount";
        }
    }

    private void addMessage(String message, String detail, FacesMessage.Severity f){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(f, message,detail));
    }
    private void saveMessageInFlashScope(){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }
}