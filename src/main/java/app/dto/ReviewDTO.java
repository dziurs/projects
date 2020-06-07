package app.dto;

import app.dto.DeveloperDTO;
import app.model.enums.BuildingType;

public class ReviewDTO {

    private Integer id;
    private String title;
    private int area;
    private BuildingType buildingType;
    private int livingSpace;
    private boolean garage;
    private String city;
    private String street;
    private String postCode;
    private String image_url;
    private DeveloperDTO developer;

    public ReviewDTO(Integer id, String title, int area, BuildingType buildingType, int livingSpace, boolean garage, String city, String street, String postCode, String image_url, DeveloperDTO developer) {
        this.id = id;
        this.title = title;
        this.area = area;
        this.buildingType = buildingType;
        this.livingSpace = livingSpace;
        this.garage = garage;
        this.city = city;
        this.street = street;
        this.postCode = postCode;
        this.image_url = image_url;
        this.developer = developer;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getArea() {
        return area;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public int getLivingSpace() {
        return livingSpace;
    }

    public boolean isGarage() {
        return garage;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getImage_url() {
        return image_url;
    }

    public DeveloperDTO getDeveloper() {
        return developer;
    }
}
