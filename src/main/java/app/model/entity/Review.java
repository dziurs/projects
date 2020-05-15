package app.model.entity;

import app.model.enums.BuildingType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity(name = "Review")
@Table(name = "buildingsales_reviews", schema = "buildingsales")
@NamedQueries({
        @NamedQuery(name = "Review.findAll", query = "SELECT r FROM Review r")
        , @NamedQuery(name = "Review.findById", query = "SELECT r FROM Review r WHERE r.id = :id")
        , @NamedQuery(name = "Review.findByTitle", query = "SELECT r FROM Review r WHERE r.title = :title")
        , @NamedQuery(name = "Review.findByArea", query = "SELECT r FROM Review r WHERE r.area = :area")
        , @NamedQuery(name = "Review.findByBuildingType", query = "SELECT r FROM Review r WHERE r.buildingType = :buildingType")
        , @NamedQuery(name = "Review.findByLivingSpace", query = "SELECT r FROM Review r WHERE r.livingSpace = :livingSpace")
        , @NamedQuery(name = "Review.findByGarage", query = "SELECT r FROM Review r WHERE r.garage = :garage")
        , @NamedQuery(name = "Review.findByCity", query = "SELECT r FROM Review r WHERE r.city = :city")
})

public class Review implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false, length = 300)
    private String title;

    @Column(name = "area", nullable = false, length = 100)
    private int area;

    @Column(name="building_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private BuildingType buildingType;

    @Column(name = "livingSpace", nullable = false, length = 10)
    private int livingSpace;

    @Column(name = "garage", nullable = false, length = 20)
    private boolean garage;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "street", nullable = false, length = 100)
    private String street;

    @Column(name = "post_code", nullable = false , length = 6)
    private String postCode;

    @Column(name="image", nullable = false, length = 300)
    private String image_url;

    @ManyToOne(fetch = FetchType.LAZY)
    private Developer developer;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "review")
    private List<Meeting> meetings = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "review_user",joinColumns = @JoinColumn(name = "review_id",foreignKey = @ForeignKey(name = "key_review"), nullable = false),
            inverseJoinColumns = @JoinColumn(name = "user_id",foreignKey = @ForeignKey(name = "key_user"), nullable = false))
    private Set<User> users = new HashSet<User>();

    public Integer getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    public int getLivingSpace() {
        return livingSpace;
    }

    public void setLivingSpace(int livingSpace) {
        this.livingSpace = livingSpace;
    }

    private List<Meeting> getMeetings() {
        return meetings;
    }

    private void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    public boolean isGarage() {
        return garage;
    }

    public void setGarage(boolean garage) {
        this.garage = garage;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        if(developer==null) throw new NullPointerException("developer parameter is null");
        //developer.addReview(this);// pytanie czy wyłączyć kaskadowe dodawanie czy ręcznie dodawać ?
        this.developer = developer;
    }

    public boolean addUser(User user){
        if(user==null) throw new NullPointerException("meeting parameter is null");
        else {
            users.add(user);
            user.getReviews().add(this);
            return true;
        }
    }
    public boolean removeUser(User user){
        if (user==null) return false;
        else {
            user.getReviews().remove(user);
            return users.remove(user);
        }
    }
    public boolean addMeeting(Meeting meeting){
        if(meeting==null) throw new NullPointerException("meeting parameter is null");
        else {
            meeting.setReview(this);
            return meetings.add(meeting);
        }
    }
    public boolean removeMeeting(Meeting meeting){
        if (meeting==null) return false;
        else {
            meeting.setReview(null);
            return meetings.remove(meeting);
        }
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public boolean hasMeeting(User user){
        if(user==null) throw new NullPointerException("user parameter is null");
        boolean value = false;
        for(Meeting meeting: meetings){
            if (meeting.getUser().getEmail().equals(user.getEmail())){
                value = true;
                break;
            }
        }
        return value;
    }
    public List<Meeting> getMeeting(User user){
        if(user==null) throw new NullPointerException("user parameter is null");
        List<Meeting> meetings = new ArrayList<>();
        Iterator<Meeting> iterator = meetings.iterator();
        while (iterator.hasNext()){
            Meeting meeting = iterator.next();
            User meetingUser = meeting.getUser();
            if (meetingUser.getEmail().equals(user.getEmail())){
                meetings.add(meeting);
            }
        }
        return meetings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (area != review.area) return false;
        if (livingSpace != review.livingSpace) return false;
        if (garage != review.garage) return false;
        //if (!id.equals(review.id)) return false;
        if (!title.equals(review.title)) return false;
        if (buildingType != review.buildingType) return false;
        if (!city.equals(review.city)) return false;
        if (!street.equals(review.street)) return false;
        if (!postCode.equals(review.postCode)) return false;
        if (!image_url.equals(review.image_url)) return false;
        return developer.equals(review.developer);
    }

    @Override
    public int hashCode() {
        int result = 0;//id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + area;
        result = 31 * result + buildingType.hashCode();
        result = 31 * result + livingSpace;
        result = 31 * result + (garage ? 1 : 0);
        result = 31 * result + city.hashCode();
        result = 31 * result + street.hashCode();
        result = 31 * result + postCode.hashCode();
        result = 31 * result + image_url.hashCode();
        result = 31 * result + developer.hashCode();
        return result;
    }
}
