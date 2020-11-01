package app.web;

import app.dto.ReviewDTO;
import app.endpoints.BuildingSalesEndpoint;
import app.exception.AccountException;
import app.exception.BuildingSalesAppException;
import app.exception.GeneralAplicationException;
import app.exception.ImegeFileIOException;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.Serializable;
import java.security.Principal;
import java.util.ResourceBundle;

@ViewScoped
@Named(value = "reviewController")
public class ReviewAddingController implements Serializable {

    @Inject
    BuildingSalesEndpoint endpoint;

    private final String postCodeRegex ="[0-9]{2}\\-[0-9]{3}";

    private final String intRegex = "[0-9]+";

    private ReviewDTO reviewDTO = new ReviewDTO();

    private String livingSpace;

    private String area;

    private Part file;

    private Principal principal;

    @PostConstruct
    public void init(){
        this.principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
    }

    private boolean trigger = false;
    private ResourceBundle bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("resourceBundle.path"),
            FacesContext.getCurrentInstance().getViewRoot().getLocale());

    public String getPostCodeRegex() {
        return postCodeRegex;
    }


    public String addReview(){
        reviewDTO.setArea(Integer.parseInt(area));
        reviewDTO.setLivingSpace(Integer.parseInt(livingSpace));
        setTrigger(false);
        try {
            endpoint.addReview(principal,file,reviewDTO);
        }catch (GeneralAplicationException e){
            addMessage(e.getMessage(),null,FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
        }catch (AccountException e){
            addMessage(e.getMessage(),null, FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
        }catch (ImegeFileIOException e){
            addMessage(bundle.getString("file.upload.error"),bundle.getString("file.upload.error.detail"),FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
        }catch (BuildingSalesAppException e){
            addMessage(bundle.getString(e.getMessage()),null,FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
        }
        addMessage(bundle.getString("page.buildingsales.add.review.message.success"),
                bundle.getString("page.buildingsales.add.review.message.success.detail"),FacesMessage.SEVERITY_INFO);
        saveMessageInFlashScope();
        return "addReview";

    }
    public void upload() {
        setTrigger(true);
        addMessage(bundle.getString("file.uploaded"),bundle.getString("file.uploaded.detail"), FacesMessage.SEVERITY_INFO);
    }

    private void addMessage(String message, String detail, FacesMessage.Severity f){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(f, message,detail));
    }
    private void saveMessageInFlashScope(){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    public ReviewDTO getReviewDTO() {
        return reviewDTO;
    }
    public void setReviewDTO(ReviewDTO reviewDTO) {
        this.reviewDTO = reviewDTO;
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

    public String getIntRegex() {
        return intRegex;
    }
}
