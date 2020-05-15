package app.model.entity;

import javax.persistence.*;
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
        , @NamedQuery(name = "Meeting.findByReview", query = "SELECT m FROM Meeting m WHERE m.review = :review")})
public class Meeting implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false, length = 100)
    private Date date;

    @Column(name = "addedByUser", nullable = false, length = 10)
    private boolean addedByUser;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    public Integer getId() {
        return id;
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
}
