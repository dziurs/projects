package app.model.entity;

import app.model.audit.Audit;
import app.model.audit.AuditListener;
import app.model.enums.UserType;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "buildingsales_developers", schema = "buildingsales")
@NamedQueries({
        @NamedQuery(name = "Developer.findAll", query = "SELECT d FROM Developer d")
        , @NamedQuery(name = "Developer.findById", query = "SELECT d FROM Developer d WHERE d.id = :id")
        , @NamedQuery(name = "Developer.findBySurname", query = "SELECT d FROM Developer d WHERE d.surname = :surname")
        , @NamedQuery(name = "Developer.findByFirstName", query = "SELECT d FROM Developer d WHERE d.firstName = :firstName")
        , @NamedQuery(name = "Developer.findByUserType", query = "SELECT d FROM Developer d WHERE d.userType = :userType")
        , @NamedQuery(name = "Developer.findByEmail", query = "SELECT d FROM Developer d WHERE d.email.email = :email")})
@EntityListeners(value = AuditListener.class)
public class Developer implements Serializable, Audit {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ver", nullable = false)
    @Version
    private long version;

    @Size(min = 3, max = 250)
    @Column(name = "surname",nullable = false)
    private String surname;

    @Size(min = 3, max = 250)
    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Size(min = 3, max = 250)
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private final UserType userType = UserType.DEVELOPER;

    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "email_id", unique = true, nullable = false)
    private Email email;

    @OneToMany(mappedBy = "developer", cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

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

    public int getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String name) {
        this.surname = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public Set<Review> getReviews() {
        return reviews;
    }
    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public boolean addReview(Review review){
        return reviews.add(review);
    }
    public boolean removeReview(Review review){
        return reviews.remove(review);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Developer developer = (Developer) o;

        //if (!id.equals(developer.id)) return false;
        if (!surname.equals(developer.surname)) return false;
        if (!firstName.equals(developer.firstName)) return false;
        if (!companyName.equals(developer.companyName)) return false;
        return email.equals(developer.email);
    }

    @Override
    public int hashCode() {
        int result = 0;//id.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + companyName.hashCode();
        result = 31 * result + email.hashCode();
        return result;
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
