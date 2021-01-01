package app.web;

import app.dto.MeetingDTO;
import app.endpoints.BuildingSalesEndpoint;
import app.exception.AccountException;
import app.exception.AppDataBaseException;
import app.exception.BuildingSalesAppException;
import app.exception.GeneralApplicationException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import java.io.Serializable;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@RequestScoped
@Named(value = "userMeetingsController")
public class UserMeetingsController implements Serializable {

    @Inject
    private BuildingSalesEndpoint endpoint;

    private List<MeetingDTO> meetingDTOList;

    private Principal principal;

    private Date now ;

    private ResourceBundle bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("resourceBundle.path"),
            FacesContext.getCurrentInstance().getViewRoot().getLocale());


    @Inject
    public UserMeetingsController(SecurityContext securityContext){
        this.principal = securityContext.getCallerPrincipal();
    }

    @PostConstruct
    public void init(){
        this.now = new Date();
        try {
            setMeetingDTOList(endpoint.getMeetingsAcceptedByUser(principal.getName()));
        }catch (AccountException e) {
            addMessage(bundle.getString(e.getMessage()),null,FacesMessage.SEVERITY_ERROR);
            getNavigationHandler().handleNavigation(getFacesContext(),null,"myMeetings");
        }catch (BuildingSalesAppException e){
            addMessage(bundle.getString(e.getCause().getMessage()),null,FacesMessage.SEVERITY_ERROR);
            getNavigationHandler().handleNavigation(getFacesContext(),null,"myMeetings");
        }
    }
    public void cancelMeeting(ActionEvent event){
        MeetingDTO meetingDTO = (MeetingDTO) event.getComponent().getAttributes().get("meetingToCancel");
        try {
            Date meetingDTODate = meetingDTO.getDate();
            long twoDays = 2 * 24 * 60 * 60 * 1000;
            if(now.getTime()+twoDays<meetingDTODate.getTime()){
                endpoint.cancelMeeting(meetingDTO);
            }
            else {
                addMessage(bundle.getString("page.buildingsales.cancel.meeting.by.user.48.hours"),bundle.getString("page.buildingsales.cancel.meeting.by.user.48.hours.details"),FacesMessage.SEVERITY_WARN);
            }
            FacesContext facesContext = FacesContext.getCurrentInstance();
            NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
            navigationHandler.handleNavigation(facesContext,null,"myMeetings");
        }catch (AppDataBaseException e) {
            addMessage(bundle.getString(e.getMessage()),null,FacesMessage.SEVERITY_ERROR);
            getNavigationHandler().handleNavigation(getFacesContext(),null,"myMeetings");
        }catch (GeneralApplicationException e){
            addMessage(bundle.getString(e.getMessage()),null,FacesMessage.SEVERITY_ERROR);
            getNavigationHandler().handleNavigation(getFacesContext(),null,"myMeetings");
        }catch (BuildingSalesAppException e){
            addMessage(bundle.getString(e.getCause().getMessage()),null,FacesMessage.SEVERITY_ERROR);
            getNavigationHandler().handleNavigation(getFacesContext(),null,"myMeetings");
        }

    }

    public List<MeetingDTO> getMeetingDTOList() {
        return meetingDTOList;
    }

    public void setMeetingDTOList(List<MeetingDTO> meetingDTOList) {
        this.meetingDTOList = meetingDTOList;
    }
    private void addMessage(String message, String detail, FacesMessage.Severity f){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(f, message,detail));
    }
    private void saveMessageInFlashScope(){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }
    private FacesContext getFacesContext(){
        return FacesContext.getCurrentInstance();
    }

    private NavigationHandler getNavigationHandler(){
        return getFacesContext().getApplication().getNavigationHandler();
    }
}
