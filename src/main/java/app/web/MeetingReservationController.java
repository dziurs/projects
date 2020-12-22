package app.web;

import app.dto.MeetingDTO;
import app.dto.ReviewDTO;
import app.endpoints.BuildingSalesEndpoint;
import app.exception.AppDataBaseException;
import app.exception.BuildingSalesAppException;
import app.exception.GeneralApplicationException;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import java.io.Serializable;
import java.security.Principal;
import java.util.ResourceBundle;

@RequestScoped
@Named(value = "meetingReservationController")
public class MeetingReservationController implements Serializable {

    @Inject
    private BuildingSalesEndpoint endpoint;

    private MeetingDTO meetingDTO;

    private Principal principal;

    private ResourceBundle bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("resourceBundle.path"),
            FacesContext.getCurrentInstance().getViewRoot().getLocale());

    @Inject
    public MeetingReservationController(SecurityContext securityContext){
        this.principal = securityContext.getCallerPrincipal();
    }

    public String acceptMeeting(){
        try {
            ReviewDTO reviewDTO = endpoint.acceptMeetingByUser(meetingDTO, principal.getName());
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
            flash.put("review", reviewDTO);
            flash.setKeepMessages(true);
            return "displaySelected";
        }catch (GeneralApplicationException e) {
            addMessage(bundle.getString(e.getMessage()),null,FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "displaySelected";
        }catch (AppDataBaseException e){
            addMessage(bundle.getString(e.getMessage()),null,FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "displaySelected";
        }catch (BuildingSalesAppException e){
            addMessage(e.getCause().getMessage(),null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "displaySelected";
        }
    }

    public MeetingDTO getMeetingDTO() {
        return meetingDTO;
    }

    public void setMeetingDTO(MeetingDTO meetingDTO) {
        this.meetingDTO = meetingDTO;
    }

    private void addMessage(String message, String detail, FacesMessage.Severity f){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(f, message,detail));
    }
    private void saveMessageInFlashScope(){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }
}
