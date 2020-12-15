package app.web;

import app.dto.ReviewDTO;
import app.endpoints.BuildingSalesEndpoint;
import app.model.enums.BuildingType;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named(value = "reviewsSearchingController")
public class ReviewsSearchingController implements Serializable {

    @Inject
    private BuildingSalesEndpoint endpoint;

    private List<ReviewDTO> reviewDTOList;

    private String city ;

    private String buildingType ;


    public String searchReviews(){
        if(city.equals("") && buildingType.equals("none")){
            setReviewDTOList(endpoint.findAll());
        }
        if(!city.equals("") && buildingType.equals("none")){
            setReviewDTOList(endpoint.findAllByCity(getCity()));
        }
        if(city.equals("")&&!buildingType.equals("none")){
            setReviewDTOList(endpoint.findAllByBuildingType(BuildingType.valueOf(getBuildingType())));
        }
        if(!city.equals("") && !buildingType.equals("none")) {
            setReviewDTOList(endpoint.findAllByCityAndBuildingType(getCity(),BuildingType.valueOf(getBuildingType())));
        }
        return "displayResults";
    }

    public void displayReview(ActionEvent event){
        ReviewDTO reviewDTOFromAtribute = (ReviewDTO)event.getComponent().getAttributes().get("review");
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put("review",reviewDTOFromAtribute);
        flash.setKeepMessages(true);
        setReviewDTOList(null);
        setCity(null);
        setBuildingType(null);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(facesContext,null,"displaySelected");
    }

    public List<ReviewDTO> getReviewDTOList() {
        return this.reviewDTOList;
    }

    public void setReviewDTOList(List<ReviewDTO> reviewDTOList) {
        this.reviewDTOList = reviewDTOList;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    private BuildingType convertStringToBuildingType(String buildingType){
        return BuildingType.valueOf(buildingType);
    }
}
