package app.dto;

import java.util.Date;

public class MeetingDTO {
    /* if You create MeetingDTO from web page input, set 'id' less than zero, in the other case ConverterDTOToEntity
    should not work correctly */

    private Integer id;
    private Date date;
    private boolean addedByUser;
    private UserDTO user;
    private ReviewDTO review;

    public MeetingDTO(Integer id, Date date, boolean addedByUser, UserDTO user/*, ReviewDTO review*/) {
        this.id = id;
        this.date = date;
        this.addedByUser = addedByUser;
        this.user = user;
        this.review = review;
    }

    public MeetingDTO() {
        this.id = -1;
    }

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public boolean isAddedByUser() {
        return addedByUser;
    }

    public UserDTO getUser() {
        return user;
    }

    public ReviewDTO getReview() {
        return review;
    }

    public void setReview(ReviewDTO review) {
        this.review = review;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(UserDTO user){
        this.user = user;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public void setAddedByUser(boolean addedByUser) {
        this.addedByUser = addedByUser;
    }

    @Override
    public String toString() {
        return "MeetingDTO{" +
                "id=" + id +
                ", date=" + date +
                ", addedByUser=" + addedByUser +
                ", user=" + user +
                ", review=" + review +
                '}';
    }
}
