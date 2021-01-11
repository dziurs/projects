package app.model.entity;

import app.model.audit.Audit;
import app.model.audit.AuditListener;
import app.model.enums.BuildingType;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.*;

@Entity(name = "Review")
@Table(name = "buildingsales_reviews", schema = "buildingsales")
@NamedQueries({
        @NamedQuery(name = "Review.findAll", query = "SELECT r FROM Review r")
        , @NamedQuery(name = "Review.findById", query = "SELECT r FROM Review r WHERE r.id = :id")
        , @NamedQuery(name = "Review.findByTitle", query = "SELECT r FROM Review r WHERE r.title = :title")
        , @NamedQuery(name = "Review.findByArea", query = "SELECT r FROM Review r WHERE r.area = :area")
        , @NamedQuery(name = "Review.findByBuildingType", query = "SELECT r FROM Review r WHERE r.buildingType = :buildingType order by r.creationDate desc ")
        , @NamedQuery(name = "Review.findByLivingSpace", query = "SELECT r FROM Review r WHERE r.livingSpace = :livingSpace")
        , @NamedQuery(name = "Review.findByGarage", query = "SELECT r FROM Review r WHERE r.garage = :garage")
        , @NamedQuery(name = "Review.findByCity", query = "SELECT r FROM Review r WHERE r.city = :city order by r.creationDate desc ")
})
@EntityListeners(value = AuditListener.class)
public class Review implements Serializable, Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ver", nullable = false)
    @Version
    private long version;

    @Size(min = 3, max = 300)
    @Column(name = "title", nullable = false, length = 300)
    private String title;

    @Digits(integer = 10, fraction = 0)
    @Min(value = 1)
    @Column(name = "area", nullable = false, length = 10)
    private int area;

    @Column(name="building_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private BuildingType buildingType;

    @Digits(integer = 10, fraction = 0)
    @Min(value = 1)
    @Column(name = "livingSpace", nullable = false, length = 10)
    private int livingSpace;

    @NotNull
    @Column(name = "garage", nullable = false, length = 5)
    private boolean garage;

    @Size(min = 2, max = 50)
    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Size(min = 2, max = 100)
    @Column(name = "street", nullable = false, length = 100)
    private String street;

    @Pattern(regexp = "[0-9]{2}\\-[0-9]{3}")
    @Size(min = 5, max = 6)
    @Column(name = "post_code", nullable = false , length = 6)
    private String postCode;

    @NotNull
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name="image", columnDefinition = "MEDIUMBLOB", length = 1048576, nullable = false)
    private byte[] image;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Developer developer;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "review")
    private List<Meeting> meetings = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "review_user",joinColumns = @JoinColumn(name = "review_id",foreignKey = @ForeignKey(name = "key_review"), nullable = false),
            inverseJoinColumns = @JoinColumn(name = "user_id",foreignKey = @ForeignKey(name = "key_user"), nullable = false))
    private Set<User> users = new HashSet<User>();

    @Column
    private String creationUserLogin;

    @Column
    private String modificationUserLogin;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date modificationDate;

    public Integer getId() {
        return id;
    }

    public long getVersion() {
        return version;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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
        if(user==null) throw new NullPointerException("user parameter is null");
        if(users.contains(user))return false;
        else {
            users.add(user);
            user.getReviews().add(this);
            return true;
        }
    }
    public boolean removeUser(User user){
        if (user==null) throw new NullPointerException("user parameter is null");
        else {
            user.getReviews().remove(this);
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
        List<Meeting> meetingList = new ArrayList<>();
        Iterator<Meeting> iterator = meetings.iterator();
        while (iterator.hasNext()){
            Meeting meeting = iterator.next();
            User meetingUser = meeting.getUser();
            if (meetingUser.getEmail().equals(user.getEmail())){
                meetingList.add(meeting);
            }
        }
        return meetingList;
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
        if (!image.equals(review.image)) return false;
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
        result = 31 * result + developer.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", version=" + version +
                ", title='" + title + '\'' +
                ", area=" + area +
                ", buildingType=" + buildingType +
                ", livingSpace=" + livingSpace +
                ", garage=" + garage +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", postCode='" + postCode + '\'' +
                ", developer=" + developer +
                ", creationUserLogin='" + creationUserLogin + '\'' +
                ", modificationUserLogin='" + modificationUserLogin + '\'' +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                '}';
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public Date getModificationDate() {
        return modificationDate;
    }

    @Override
    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public String getCreationUserLogin() {
        return creationUserLogin;
    }

    @Override
    public void setCreationUserLogin(String creationUserLogin) {
        this.creationUserLogin = creationUserLogin;
    }

    @Override
    public String getModificationUserLogin() {
        return modificationUserLogin;
    }

    @Override
    public void setModificationUserLogin(String modificationUserLogin) {
        this.modificationUserLogin = modificationUserLogin;
    }
}
