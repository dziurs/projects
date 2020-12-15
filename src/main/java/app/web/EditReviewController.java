package app.web;

import app.dto.ReviewDTO;
import app.endpoints.BuildingSalesEndpoint;
import app.exception.BuildingSalesAppException;
import app.exception.GeneralApplicationException;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ResourceBundle;

@ViewScoped
@Named(value = "editReviewController")
public class EditReviewController implements Serializable {

    @Inject
    private BuildingSalesEndpoint endpoint;

    private ReviewDTO reviewDTOToEdit;

    private String livingSpace;

    private String area;

    private final String postCodeRegex ="[0-9]{2}\\-[0-9]{3}";

    private final String intRegex = "[0-9]+";

    private Part file;

    private boolean trigger = false;

    private ResourceBundle bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("resourceBundle.path"),
            FacesContext.getCurrentInstance().getViewRoot().getLocale());

    @PostConstruct
    public void loadReviewToEdit(){
      Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
      ReviewDTO reviewDTO = (ReviewDTO) flash.get("reviewToEdit");
      setReviewDTOToEdit(reviewDTO);
      setArea(String.valueOf(reviewDTO.getArea()));
      setLivingSpace(String.valueOf(reviewDTO.getLivingSpace()));
    }
    public void fileHandler(AjaxBehaviorEvent event){
        setTrigger(true);
        addMessage(bundle.getString("file.uploaded"),bundle.getString("file.uploaded.detail"), FacesMessage.SEVERITY_INFO);
    }
    public void cancelAction(AjaxBehaviorEvent event){
        setTrigger(true);
    }

    public String saveEditedReview(){
        getReviewDTOToEdit().setArea(Integer.parseInt(getArea()));
        getReviewDTOToEdit().setLivingSpace(Integer.parseInt(getLivingSpace()));
        setTrigger(false);
        try {
            if(null!=file){
                InputStream inputStream = file.getInputStream();
                getReviewDTOToEdit().setImage(inputStream.readAllBytes());
            }
            endpoint.editReviewByDeveloper(getReviewDTOToEdit());
            return "myReviews";
        }catch (GeneralApplicationException e) {
            addMessage(bundle.getString(e.getMessage()),null,FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "myReviews";
        } catch (BuildingSalesAppException e) {
            addMessage(e.getCause().getMessage(),null,FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "myReviews";
        } catch (IOException e){
            addMessage(bundle.getString("file.upload.error"),bundle.getString("file.upload.error.detail"),FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "myReviews";
        }
    }

    private void addMessage(String message, String detail, FacesMessage.Severity f){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(f, message,detail));
    }
    private void saveMessageInFlashScope(){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    public ReviewDTO getReviewDTOToEdit() {
        return reviewDTOToEdit;
    }

    public void setReviewDTOToEdit(ReviewDTO reviewDTOToEdit) {
        this.reviewDTOToEdit = reviewDTOToEdit;
    }

    public String getPostCodeRegex() {
        return postCodeRegex;
    }

    public String getIntRegex() {
        return intRegex;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public boolean isTrigger() {
        return trigger;
    }

    public void setTrigger(boolean trigger) {
        this.trigger = trigger;
    }

    public String getLivingSpace() {
        return livingSpace;
    }

    public void setLivingSpace(String livingSpace) {
        this.livingSpace = livingSpace;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
