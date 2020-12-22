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
//
//    public void acceptMeeting(User user, Meeting meeting) throws BuildingSalesAppException {
//        if(!meeting.isAddedByUser()){
//            meeting.setUser(user);
//            meeting.setAddedByUser(true);
//            Review review = meeting.getReview();
//            review.addUser(user);
//            meetingDAO.joinTransaction();
//            meetingDAO.update(meeting);
//            reviewDAO.update(review);
//        }else {
//            throw new AppDataBaseException();
//        }
//
//    }
    public List<Meeting> getMeetingList(Review review){
        return meetingDAO.findByReview(review);
    }
    public void addMeeting(Meeting meeting, Review review) throws BuildingSalesAppException {
        review.addMeeting(meeting);
        reviewDAO.update(review);
    }
    public void acceptMeetingByUser(Meeting meeting, User user) throws BuildingSalesAppException {
        if(!meeting.isAddedByUser()){
        meeting.setUser(user);
        meeting.setAddedByUser(true);
        Review review = meeting.getReview();
        review.addUser(user);
        reviewDAO.update(review);
        meetingDAO.update(meeting);
        }else {
            throw new AppDataBaseException();
        }
    }

    public void cancelMeeting(Meeting meeting) throws BuildingSalesAppException{
        meeting.setAddedByUser(false);
        User user = meeting.getUser();
        meeting.setUser(null);
        Review review = meeting.getReview();
        review.removeUser(user);
        reviewDAO.update(review);
        meetingDAO.update(meeting);
    }
    public List<Meeting> getMettingsAcceptedByUser(User user){
        return meetingDAO.findByUser(user);
    }
}
