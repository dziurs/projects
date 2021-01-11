package app.web;

import app.dto.UserDTO;
import app.endpoints.BuildingSalesEndpoint;
import app.exception.AccountException;
import app.exception.BuildingSalesAppException;
import app.exception.GeneralApplicationException;
import app.security.SessionAccount;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import java.io.Serializable;
import java.security.Principal;
import java.util.ResourceBundle;

@RequestScoped
@Named(value = "editUserController")
public class EditUserController implements Serializable {

    @Inject
    private BuildingSalesEndpoint endpoint;

    @Inject
    private SessionAccount sessionAccount;

    private String principal;

    private ResourceBundle bundle;

    private UserDTO userDTO;

    public EditUserController(){
    }

    @PostConstruct
    public void init(){
        this.principal = sessionAccount.getLogin();
        this.bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("resourceBundle.path"),
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        try {
            this.userDTO = endpoint.findUserByEmail(principal);
        } catch (GeneralApplicationException e) {
            addMessage(bundle.getString(e.getMessage()),null, FacesMessage.SEVERITY_ERROR);
        }catch (BuildingSalesAppException e) {
            addMessage(e.getMessage(),null,FacesMessage.SEVERITY_ERROR);
        }

    }
    public String saveEditedUser(){
        try {
            endpoint.saveEditedUser(getUserDTO());
            addMessage(bundle.getString("page.buildingsales.edit.developer.save.message"),bundle.getString("page.buildingsales.edit.developer.save.message.details"), FacesMessage.SEVERITY_INFO);
            saveMessageInFlashScope();
            return "editUser";
        }catch (AccountException e) {
            addMessage(bundle.getString(e.getMessage()),null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "editUser";
        }catch (GeneralApplicationException e) {
            addMessage(bundle.getString(e.getMessage()), null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "editUser";
        }catch (BuildingSalesAppException e) {
            addMessage(e.getMessage(),null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "editUser";
        }
    }
    private void addMessage(String message, String detail, FacesMessage.Severity f){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(f, message,detail));
    }
    private void saveMessageInFlashScope(){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
