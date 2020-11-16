package app.model.entity;

import app.model.audit.Audit;
import app.model.audit.AuditListener;
import app.model.enums.UserType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "buildingsales_users", schema = "buildingsales")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
        , @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id")
        , @NamedQuery(name = "User.findBySurname", query = "SELECT u FROM User u WHERE u.surname = :surname")
        , @NamedQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName = :firstName")
        , @NamedQuery(name = "User.findByUserType", query = "SELECT u FROM User u WHERE u.userType = :userType")
        , @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email.email = :email")})
@EntityListeners(value = AuditListener.class)
public class User implements Serializable, Audit {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ver", nullable = false)
    @Version
    private long version;

    @Column(name = "surname",nullable = false, length = 100)
    private String surname;

    @Column(name = "firstName", nullable = false, length = 100)
    private String firstName;

    @Column(name = "user_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private final UserType userType = UserType.USER;

    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "email_id",nullable = false, unique = true)
    private Email email;

    @ManyToMany(mappedBy = "users",fetch = FetchType.LAZY)
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

    public Integer getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getSurame() {
        return surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public Email getEmail() {
        return email;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews){
        this.reviews= reviews;
    }

    public UserType getUserType() {
        return userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        //if (!id.equals(user.id)) return false;
        if (!surname.equals(user.surname)) return false;
        if (!firstName.equals(user.firstName)) return false;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        int result = 0; //id.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + firstName.hashCode();
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
