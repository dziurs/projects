package app.managers;

import app.dao.MeetingDAO;
import app.dao.ReviewDAO;
import app.exception.AppDataBaseException;
import app.exception.BuildingSalesAppException;
import app.exception.GeneralApplicationException;
import app.interceptor.Log;
import app.model.entity.Developer;
import app.model.entity.Meeting;
import app.model.entity.Review;
import app.model.entity.User;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
@Local
@RolesAllowed({"Developer","User"})
@Transactional(value = Transactional.TxType.MANDATORY)
public class MeetingManager {
    @Inject
    private ReviewDAO reviewDAO;
    @Inject
    private MeetingDAO meetingDAO;

    @PermitAll
    @Log
    public List<Meeting> getMeetingList(Review review) {
        return meetingDAO.findByReview(review);
    }

    @RolesAllowed("Developer")
    @Log
    public void addMeeting(Meeting meeting, Review review) throws BuildingSalesAppException {
        review.addMeeting(meeting);
        reviewDAO.update(review);
    }

    @RolesAllowed("User")
    @Log
    public void acceptMeetingByUser(Meeting meeting, User user) throws BuildingSalesAppException {
        if (!meeting.isAddedByUser()) {
            meeting.setUser(user);
            meeting.setAddedByUser(true);
            Review review = meeting.getReview();
            boolean check = review.addUser(user);
            if (check) {
                reviewDAO.update(review);
                meetingDAO.update(meeting);
            }else throw new GeneralApplicationException(GeneralApplicationException.USER_ACCEPT_MEETING_AGAIN);
        } else {
            throw new AppDataBaseException();
        }
    }

    @RolesAllowed("User")
    @Log
    public void cancelMeeting(Meeting meeting) throws BuildingSalesAppException {
        meeting.setAddedByUser(false);
        User user = meeting.getUser();
        meeting.setUser(null);
        Review review = meeting.getReview();
        review.removeUser(user);
        reviewDAO.update(review);
        meetingDAO.update(meeting);
    }

    @RolesAllowed("User")
    @Log
    public List<Meeting> getMettingsAcceptedByUser(User user) {
        return meetingDAO.findByUser(user);
    }

    @RolesAllowed("Developer")
    @Log
    public List<Meeting> getDeveloperMeetingsAcceptedList(Developer developer) {
        return meetingDAO.findDevelopersMeetingsAcceptedByUser(developer);
    }
}
