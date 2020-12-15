package app.managers;

import app.dao.MeetingDAO;
import app.dao.ReviewDAO;
import app.exception.AppDataBaseException;
import app.exception.BuildingSalesAppException;
import app.model.entity.Meeting;
import app.model.entity.Review;
import app.model.entity.User;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
@Local
@Transactional(value = Transactional.TxType.MANDATORY)
public class MeetingManager {
    @Inject
    private ReviewDAO reviewDAO;
    @Inject
    private MeetingDAO meetingDAO;

    public void acceptMeeting(User user, Meeting meeting) throws BuildingSalesAppException {
        if(!meeting.isAddedByUser()){
            meeting.setUser(user);
            meeting.setAddedByUser(true);
            Review review = meeting.getReview();
            review.addUser(user);
            meetingDAO.joinTransaction();
            meetingDAO.update(meeting);
            reviewDAO.update(review);
        }else {
            throw new AppDataBaseException();
        }

    }
    public List<Meeting> getMeetingList(Review review){
        return meetingDAO.findByReview(review);
    }
    public void addMeeting(Meeting meeting, Review review) throws BuildingSalesAppException {
        review.addMeeting(meeting);
        reviewDAO.update(review);
    }
}
