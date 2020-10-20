package app.manager;

import app.dao.MeetingDAO;
import app.dao.ReviewDAO;
import app.exception.BuildingSalesAppException;
import app.model.entity.Meeting;
import app.model.entity.Review;
import app.model.entity.User;
import javax.inject.Inject;
import javax.transaction.Transactional;

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
            throw new BuildingSalesAppException("meeting was accepted by another user");
        }

    }
}
