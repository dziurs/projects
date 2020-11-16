package app.web;

import app.dto.MeetingDTO;
import app.dto.ReviewDTO;
import app.endpoints.BuildingSalesEndpoint;
import app.exception.BuildingSalesAppException;
import app.exception.GeneralAplicationException;
import org.primefaces.model.ByteArrayContent;
import org.primefaces.model.StreamedContent;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
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

    private List<MeetingDTO> meetingDTOList = new ArrayList<>();

    private ResourceBundle bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("resourceBundle.path"),
            FacesContext.getCurrentInstance().getViewRoot().getLocale());

    public String displayReview(ReviewDTO reviewDTO){
        this.reviewDTO = reviewDTO;
        try {
            this.meetingDTOList = endpoint.getMeetingList(reviewDTO);
            return "reviewDisplaySite";
        }catch (GeneralAplicationException ex){
            addMessage(bundle.getString(ex.getMessage()),null,FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "";
        }catch (BuildingSalesAppException ex){
            addMessage(bundle.getString(ex.getCause().getMessage()),null,FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "";
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
    public StreamedContent convertByteArrayToStreamedContent(byte [] image){
        return new ByteArrayContent(image);
    }
    private void addMessage(String message, String detail, FacesMessage.Severity f){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(f, message,detail));
    }
    private void saveMessageInFlashScope(){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }
}
