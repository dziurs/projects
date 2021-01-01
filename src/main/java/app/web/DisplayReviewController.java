package app.web;

import app.dto.MeetingDTO;
import app.dto.ReviewDTO;
import app.endpoints.BuildingSalesEndpoint;
import app.exception.BuildingSalesAppException;
import app.exception.GeneralApplicationException;
import app.exception.NoKeyFlashException;
import org.primefaces.model.ByteArrayContent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@RequestScoped
@Named(value = "displayReviewController")
public class DisplayReviewController implements Serializable {

    @Inject
    BuildingSalesEndpoint endpoint;

    private ReviewDTO reviewDTO;

    private StreamedContent streamedContent;

    private List<MeetingDTO> meetingDTOList = new ArrayList<>();

    private ResourceBundle bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("resourceBundle.path"),
            FacesContext.getCurrentInstance().getViewRoot().getLocale());

    @PostConstruct
    public void init(){
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        ReviewDTO reviewDTOFromFlash = (ReviewDTO)flash.get("review");
        setReviewDTO(reviewDTOFromFlash);
        this.streamedContent = convertByteArrayToStreamedContent(reviewDTOFromFlash);
        try {
            this.meetingDTOList = endpoint.getMeetingList(reviewDTO);
        }catch (GeneralApplicationException ex) {
            addMessage(bundle.getString(ex.getMessage()), null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
            navigationHandler.handleNavigation(facesContext,null,"index");
        }catch (NoKeyFlashException ex){
            addMessage(bundle.getString(ex.getMessage()), null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext,null,"index");
        }catch (BuildingSalesAppException ex) {
            addMessage(ex.getCause().getMessage(), null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
            navigationHandler.handleNavigation(facesContext,null,"index");
        }
    }

    public ReviewDTO getReviewDTO() {
        return reviewDTO;
    }

    public void setReviewDTO(ReviewDTO reviewDTO) {
        this.reviewDTO = reviewDTO;
    }

    public List<MeetingDTO> getMeetingDTOList() {
        return meetingDTOList;
    }

    public void setMeetingDTOList(List<MeetingDTO> meetingDTOList) {
        this.meetingDTOList = meetingDTOList;
    }

    private StreamedContent convertByteArrayToStreamedContent(ReviewDTO reviewDTO){
        if(reviewDTO==null) return new ByteArrayContent(new byte[1]);
        else return new ByteArrayContent(reviewDTO.getImage());
    }

    private void addMessage(String message, String detail, FacesMessage.Severity f){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(f, message,detail));
    }
    private void saveMessageInFlashScope(){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    public StreamedContent getStreamedContent() {
        return streamedContent;
    }

    public void setStreamedContent(StreamedContent streamedContent) {
        this.streamedContent = streamedContent;
    }
}
