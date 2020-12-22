package app.model.entity;

import app.model.audit.Audit;
import app.model.audit.AuditListener;
import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity(name ="Meeting")
@Table(name = "buildingsales_meetings", schema = "buildingsales")
@NamedQueries({
        @NamedQuery(name = "Meeting.findAll", query = "SELECT m FROM Meeting m")
        , @NamedQuery(name = "Meeting.findById", query = "SELECT m FROM Meeting m WHERE m.id = :id")
        , @NamedQuery(name = "Meeting.findByDate", query = "SELECT m FROM Meeting m WHERE m.date = :date")
        , @NamedQuery(name = "Meeting.findByAddedByUser", query = "SELECT m FROM Meeting m WHERE m.addedByUser = :added")
        , @NamedQuery(name = "Meeting.findByUser", query = "SELECT m FROM Meeting m WHERE m.user = :user")
        , @NamedQuery(name = "Meeting.findByReview", query = "SELECT m FROM Meeting m WHERE m.review = :review order by m.date desc ")})
@EntityListeners(value = AuditListener.class)
public class Meeting implements Serializable, Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ver", nullable = false)
    @Version
    private long version;

    @Future
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false)
    private Date date;

    @NotNull
    @Column(name = "addedByUser", nullable = false, length = 10)
    private boolean addedByUser = false;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isAddedByUser() {
        return addedByUser;
    }

    public void setAddedByUser(boolean addedByUser) {
        this.addedByUser = addedByUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meeting meeting = (Meeting) o;

        //if (!id.equals(meeting.id)) return false;
        if (!date.equals(meeting.date)) return false;
        return review.equals(meeting.review);
    }

    @Override
    public int hashCode() {
        int result = 0; //id.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + review.hashCode();
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
