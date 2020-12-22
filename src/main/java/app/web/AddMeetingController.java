package app.web;

import app.dto.MeetingDTO;
import app.dto.ReviewDTO;
import app.endpoints.BuildingSalesEndpoint;
import app.exception.AppDataBaseException;
import app.exception.BuildingSalesAppException;
import app.exception.GeneralApplicationException;
import org.primefaces.model.ByteArrayContent;
import org.primefaces.model.StreamedContent;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@RequestScoped
@Named(value = "addMeetingController")
public class AddMeetingController{

    @Inject
    BuildingSalesEndpoint endpoint;

    private ReviewDTO reviewDTO;

    private MeetingDTO meetingDTO;

//    private Date tomorrowDate;

    private Date date;

    private StreamedContent streamedContent;

    private List<MeetingDTO> meetingDTOList = new ArrayList<>();

    private ResourceBundle bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("resourceBundle.path"),
            FacesContext.getCurrentInstance().getViewRoot().getLocale());

    @PostConstruct
    public void init(){
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        ReviewDTO reviewDTOFromFlash = (ReviewDTO)flash.get("addMeetingReview");
        this.reviewDTO = reviewDTOFromFlash;
        this.streamedContent = convertByteArrayToStreamedContent(reviewDTOFromFlash.getImage());
        this.meetingDTO = new MeetingDTO();
        long oneHour = 60 * 60 * 1000;
//        this.tomorrowDate = new Date(new Date().getTime()+oneDay);
        this.date = new Date(new Date().getTime()+oneHour);

        try {
            this.meetingDTOList = endpoint.getMeetingList(reviewDTO);
        }catch (GeneralApplicationException ex) {
            addMessage(bundle.getString(ex.getMessage()), null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            NavigationHandler navigationHandler = getNavigationHandler();
            navigationHandler.handleNavigation(getFacesContext(),null,"addMeetingToReview");
        }catch (BuildingSalesAppException ex) {
            addMessage(ex.getCause().getMessage(), null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            NavigationHandler navigationHandler = getNavigationHandler();
            navigationHandler.handleNavigation(getFacesContext(),null,"addMeetingToReview");
        }
    }

    public String addMeeting(){
        getMeetingDTO().setAddedByUser(false);
        getMeetingDTO().setUser(null);
        getMeetingDTO().setDate(date);
        try {
            endpoint.addMeetingToReview(getMeetingDTO(),getReviewDTO());
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
            flash.put("addMeetingReview",getReviewDTO());
            addMessage(bundle.getString("page.buildingsales.add.meeting.success.message"), bundle.getString("page.buildingsales.add.meeting.success.message.details"), FacesMessage.SEVERITY_INFO);
            saveMessageInFlashScope();
            return "addMeetingToReview";
        }catch (GeneralApplicationException e) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("addMeetingReview",getReviewDTO());
            addMessage(bundle.getString(e.getMessage()), null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "addMeetingToReview";
        }catch (AppDataBaseException e){
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
            flash.put("addMeetingToReview",getReviewDTO());
            addMessage(bundle.getString(e.getMessage()), null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "addMeetingToReview";
        }catch (BuildingSalesAppException e){
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
            flash.put("addMeetingReview",getReviewDTO());
            addMessage(e.getCause().getMessage(), null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "addMeetingToReview";
        }

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ReviewDTO getReviewDTO() {
        return reviewDTO;
    }

    public void setReviewDTO(ReviewDTO reviewDTO) {
        this.reviewDTO = reviewDTO;
    }

    public MeetingDTO getMeetingDTO() {
        return meetingDTO;
    }

    public void setMeetingDTO(MeetingDTO meetingDTO) {
        this.meetingDTO = meetingDTO;
    }

    public StreamedContent getStreamedContent() {
        return streamedContent;
    }

    public void setStreamedContent(StreamedContent streamedContent) {
        this.streamedContent = streamedContent;
    }

    public void setMeetingDTOList(List<MeetingDTO> meetingDTOList) {
        this.meetingDTOList = meetingDTOList;
    }

    public List<MeetingDTO> getMeetingDTOList() {
        return meetingDTOList;
    }

    private FacesContext getFacesContext(){
        return FacesContext.getCurrentInstance();
    }

    private NavigationHandler getNavigationHandler(){
        return getFacesContext().getApplication().getNavigationHandler();
    }

    private void addMessage(String message, String detail, FacesMessage.Severity f){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(f, message,detail));
    }

    private void saveMessageInFlashScope(){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    private StreamedContent convertByteArrayToStreamedContent(byte [] image){
        return new ByteArrayContent(image);
    }
}
