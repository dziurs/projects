package app.model.entity;

import app.model.enums.UserType;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "buildingsales_developers", schema = "buildingsales")
@NamedQueries({
        @NamedQuery(name = "Developer.findAll", query = "SELECT d FROM Developer d")
        , @NamedQuery(name = "Developer.findById", query = "SELECT d FROM Developer d WHERE d.id = :id")
        , @NamedQuery(name = "Developer.findByName", query = "SELECT d FROM Developer d WHERE d.name = :name")
        , @NamedQuery(name = "Developer.findByFirstName", query = "SELECT d FROM Developer d WHERE d.firstName = :firstName")
        , @NamedQuery(name = "Developer.findByUserType", query = "SELECT d FROM Developer d WHERE d.userType = :userType")
        , @NamedQuery(name = "Developer.findByEmail", query = "SELECT d FROM Developer d WHERE d.email = :email")})

public class Developer implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private final UserType userType = UserType.Seller;

    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "email_id", unique = true, nullable = false)
    private Email email;

    @OneToMany(mappedBy = "developer", cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!name.equals(developer.name)) return false;
        if (!firstName.equals(developer.firstName)) return false;
        if (!companyName.equals(developer.companyName)) return false;
        return email.equals(developer.email);
    }

    @Override
    public int hashCode() {
        int result = 0;//id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + companyName.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
