package app.dto;

import java.util.Date;

public class MeetingDTO {

    private Integer id;
    private Date date;
    private boolean addedByUser;
    private UserDTO user;
    private ReviewDTO review;

    public MeetingDTO(Integer id, Date date, boolean addedByUser, UserDTO user, ReviewDTO review) {
        this.id = id;
        this.date = date;
        this.addedByUser = addedByUser;
        this.user = user;
        this.review = review;
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
}
