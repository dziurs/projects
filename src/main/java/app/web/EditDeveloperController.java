package app.web;

import app.dto.DeveloperDTO;
import app.endpoints.BuildingSalesEndpoint;
import app.exception.AccountException;
import app.exception.BuildingSalesAppException;
import app.exception.GeneralApplicationException;

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
@Named(value = "editDeveloperController")
public class EditDeveloperController implements Serializable {

    @Inject
    private BuildingSalesEndpoint endpoint;

    private Principal principal;

    private ResourceBundle bundle;

    private DeveloperDTO developerDTO;

    @Inject
    public EditDeveloperController(SecurityContext securityContext) {
        this.principal = securityContext.getCallerPrincipal();
    }

    @PostConstruct
    public void init(){
        this.bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("resourceBundle.path"),
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        try {
            this.developerDTO = endpoint.findDeveloperByEmail(principal.getName());
        } catch (GeneralApplicationException e) {
            addMessage(bundle.getString(e.getMessage()),null, FacesMessage.SEVERITY_ERROR);
        }catch (BuildingSalesAppException e) {
            addMessage(e.getMessage(),null,FacesMessage.SEVERITY_ERROR);
        }

    }

    public String saveEditedDeveloper(){
        try {
            endpoint.saveEditedDeveloper(getDeveloperDTO());
            addMessage(bundle.getString("page.buildingsales.edit.developer.save.message"),bundle.getString("page.buildingsales.edit.developer.save.message.details"), FacesMessage.SEVERITY_INFO);
            saveMessageInFlashScope();
            return "editDeveloper";
        }catch (AccountException e) {
            addMessage(bundle.getString(e.getMessage()),null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "editDeveloper";
        }catch (GeneralApplicationException e) {
            addMessage(bundle.getString(e.getMessage()), null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "editDeveloper";
        }catch (BuildingSalesAppException e) {
            addMessage(e.getMessage(),null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "editDeveloper";
        }
    }

    public DeveloperDTO getDeveloperDTO() {
        return developerDTO;
    }

    public void setDeveloperDTO(DeveloperDTO developerDTO) {
        this.developerDTO = developerDTO;
    }
    private void addMessage(String message, String detail, FacesMessage.Severity f){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(f, message,detail));
    }
    private void saveMessageInFlashScope(){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }
}
