package app.web;

import app.dto.MeetingDTO;
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
import java.util.List;
import java.util.ResourceBundle;

@RequestScoped
@Named(value = "developerAcceptedMeetingController")
public class DeveloperAcceptedMeetingController implements Serializable {

    @Inject
    private BuildingSalesEndpoint endpoint;

    private Principal principal;

    private List<MeetingDTO> meetingDTOList;

    private ResourceBundle bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("resourceBundle.path"),
            FacesContext.getCurrentInstance().getViewRoot().getLocale());

    @Inject
    public DeveloperAcceptedMeetingController(SecurityContext securityContext){
        this.principal = securityContext.getCallerPrincipal();
    }

    @PostConstruct
    public void init(){
        try {
            if(principal==null) throw new GeneralApplicationException(GeneralApplicationException.PRINCIPAL);
            this.meetingDTOList= endpoint.getDeveloperMeetingsAcceptedByUsers(principal.getName());
        }catch (AccountException e) {
            addMessage(bundle.getString(e.getMessage()), null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            getFacesContext().getApplication().getNavigationHandler().handleNavigation(getFacesContext(),null,"");
        }catch (GeneralApplicationException e) {
            addMessage(bundle.getString(e.getMessage()), null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            getFacesContext().getApplication().getNavigationHandler().handleNavigation(getFacesContext(),null,"");
        }catch (BuildingSalesAppException e) {
            addMessage(bundle.getString(e.getMessage()), null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            getFacesContext().getApplication().getNavigationHandler().handleNavigation(getFacesContext(),null,"");
        }
    }
    private void addMessage(String message, String detail, FacesMessage.Severity f){
        getFacesContext().addMessage(null, new FacesMessage(f, message,detail));
    }
    private void saveMessageInFlashScope(){
        getFacesContext().getExternalContext().getFlash().setKeepMessages(true);
    }

    private FacesContext getFacesContext(){
        return FacesContext.getCurrentInstance();
    }

    public List<MeetingDTO> getMeetingDTOList() {
        return meetingDTOList;
    }

    public void setMeetingDTOList(List<MeetingDTO> meetingDTOList) {
        this.meetingDTOList = meetingDTOList;
    }
}
