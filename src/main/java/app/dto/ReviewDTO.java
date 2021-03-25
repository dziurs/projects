package app.dto;

import app.dto.DeveloperDTO;
import app.model.enums.BuildingType;

import java.util.Arrays;

public class ReviewDTO {
    /* if You create ReviewDTO from web page input, set 'id' less than zero, in the other case ConverterDTOToEntity
    should not work correctly */

    private Integer id;
    private String title;
    private int area;
    private BuildingType buildingType;
    private int livingSpace;
    private boolean garage;
    private String city;
    private String street;
    private String postCode;
    private byte[] image;
    private DeveloperDTO developer;

    public ReviewDTO(Integer id, String title, int area, BuildingType buildingType, int livingSpace, boolean garage, String city, String street, String postCode, byte[] image, DeveloperDTO developer) {
        this.id = id;
        this.title = title;
        this.area = area;
        this.buildingType = buildingType;
        this.livingSpace = livingSpace;
        this.garage = garage;
        this.city = city;
        this.street = street;
        this.postCode = postCode;
        this.image = image;
        this.developer = developer;
    }

    public ReviewDTO() {
        this.id = -1;
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

    public byte[] getImage() {
        return image;
    }

    public DeveloperDTO getDeveloper() {
        return developer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    public void setLivingSpace(int livingSpace) {
        this.livingSpace = livingSpace;
    }

    public void setGarage(boolean garage) {
        this.garage = garage;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setDeveloper(DeveloperDTO developer) {
        this.developer = developer;
    }

    @Override
    public String toString() {
        return "ReviewDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", area=" + area +
                ", buildingType=" + buildingType +
                ", livingSpace=" + livingSpace +
                ", garage=" + garage +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", postCode='" + postCode + '\'' +
                ", developer=" + developer +
                '}';
    }
}