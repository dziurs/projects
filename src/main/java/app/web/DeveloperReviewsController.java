package app.web;

import app.dto.ReviewDTO;
import app.endpoints.BuildingSalesEndpoint;
import app.exception.BuildingSalesAppException;
import app.exception.GeneralApplicationException;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.ResourceBundle;

@RequestScoped
@Named(value = "developerReviewsController")
public class DeveloperReviewsController implements Serializable {

    @Inject
    BuildingSalesEndpoint endpoint;

    private String developerLogin;

    private List<ReviewDTO> reviewDTOList;

    private ResourceBundle bundle;

    @Inject
    public DeveloperReviewsController(SecurityContext securityContext) {
        Principal callerPrincipal = securityContext.getCallerPrincipal();
        developerLogin= callerPrincipal.getName();
    }
    @PostConstruct
    public void init(){
        setReviewDTOList(endpoint.getDeveloperReviews(developerLogin));
        this.bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("resourceBundle.path"),
              FacesContext.getCurrentInstance().getViewRoot().getLocale());
    }

    public List<ReviewDTO> getReviewDTOList() {
        return reviewDTOList;
    }

    public void setReviewDTOList(List<ReviewDTO> reviewDTOList) {
        this.reviewDTOList = reviewDTOList;
    }

    public void displayReview(ActionEvent event){
        ReviewDTO reviewDTOFromAtribute = (ReviewDTO)event.getComponent().getAttributes().get("review");
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put("review",reviewDTOFromAtribute);
        flash.setKeepMessages(true);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(facesContext,null,"reviewDisplaySite");
    }
    public void addMeeting(ActionEvent event){
        ReviewDTO reviewDTOFromAtribute = (ReviewDTO)event.getComponent().getAttributes().get("addMeetingReview");
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put("addMeetingReview",reviewDTOFromAtribute);
        flash.setKeepMessages(true);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(facesContext,null,"addMeetingToReview");
    }

    public void getReviewToEdit(ActionEvent event){
        ReviewDTO reviewDTOFromAtributeToEdit = (ReviewDTO)event.getComponent().getAttributes().get("reviewToEdit");
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put("reviewToEdit",reviewDTOFromAtributeToEdit);
        flash.setKeepMessages(true);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(facesContext,null,"editReview");
    }

    public String reviewDelete(ReviewDTO reviewDTO){
        try {
            endpoint.reviewDelete(reviewDTO);
            return "myReviews";
        }catch (GeneralApplicationException ex){
            addMessage(bundle.getString(ex.getMessage()),null,FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "myReviews";
        }catch (BuildingSalesAppException ex){
            addMessage(ex.getCause().getMessage(),null,FacesMessage.SEVERITY_ERROR);
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
}
