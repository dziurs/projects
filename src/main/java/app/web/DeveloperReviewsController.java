package app.web;

import app.dto.ReviewDTO;
import app.endpoints.BuildingSalesEndpoint;
import app.exception.AccountException;
import app.exception.BuildingSalesAppException;
import app.exception.GeneralAplicationException;
import org.primefaces.model.ByteArrayContent;
import org.primefaces.model.StreamedContent;
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
@Named(value = "developerReviewsController")
public class DeveloperReviewsController implements Serializable {

    @Inject
    BuildingSalesEndpoint endpoint;

    private String developerLogin;

    private List<ReviewDTO> reviewDTOList;

    private ResourceBundle bundle = ResourceBundle.getBundle(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("resourceBundle.path"),
            FacesContext.getCurrentInstance().getViewRoot().getLocale());

    @Inject
    public DeveloperReviewsController(SecurityContext securityContext) {
        Principal callerPrincipal = securityContext.getCallerPrincipal();
        developerLogin= callerPrincipal.getName();
    }
    @PostConstruct
    public void init(){
        setReviewDTOList(endpoint.getDeveloperReviews(developerLogin));

    }

    public List<ReviewDTO> getReviewDTOList() {
        return reviewDTOList;
    }

    public void setReviewDTOList(List<ReviewDTO> reviewDTOList) {
        this.reviewDTOList = reviewDTOList;
    }

    public StreamedContent convertByteArrayToStreamedContent(byte [] image){
        return new ByteArrayContent(image);
    }

    public String reviewDelete(ReviewDTO reviewDTO){
        try {
            endpoint.reviewDelete(reviewDTO);
            return "myReviews";
        }catch (AccountException e){
            addMessage(bundle.getString(e.getMessage()),null,FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "myReviews";
        }catch (GeneralAplicationException ex){
            addMessage(bundle.getString(ex.getMessage()),null,FacesMessage.SEVERITY_ERROR);
            saveMessageInFlashScope();
            return "myReviews";
        }catch (BuildingSalesAppException ex){
            Throwable cause = ex.getCause();
            addMessage(bundle.getString(cause.getMessage()),null,FacesMessage.SEVERITY_ERROR);
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
