package app.converter;

import app.dao.DeveloperDAO;
import app.dao.MeetingDAO;
import app.dao.ReviewDAO;
import app.dao.UserDAO;
import app.dto.DeveloperDTO;
import app.dto.MeetingDTO;
import app.dto.ReviewDTO;
import app.dto.UserDTO;
import app.exception.BuildingSalesAppException;
import app.model.entity.*;
import java.util.List;

public class ConverterDTOToEntity {

    private ConverterDTOToEntity() {
    }
    public static User convertUserDTOToUser(UserDTO user, UserDAO userDAO) throws BuildingSalesAppException {
        String email = user.getEmail();
        List<User> userByEmail = userDAO.findByEmail(email);
        if(userByEmail.size()==0) throw new BuildingSalesAppException("Can't convert data from UserDTO");
        else return userByEmail.get(0);
    }
    public static Developer convertDeveloperDTOToDeveloper(DeveloperDTO developer, DeveloperDAO developerDAO) throws BuildingSalesAppException {
        String email = developer.getEmail();
        List<Developer> byEmail = developerDAO.findByEmail(email);
        if(byEmail.size()==0) throw new BuildingSalesAppException("Can't convert data from DeveloperDTO");
        else return byEmail.get(0);
    }
    public static Meeting convertMeetinDTOToMeeting(MeetingDTO meeting, MeetingDAO meetingDAO) throws BuildingSalesAppException {
        Integer id = meeting.getId();
        List<Meeting> byID = meetingDAO.findByID(id);
        if(byID.size()==0) throw new BuildingSalesAppException("Can't convert data from MeetingDTO");
        else return byID.get(0);

    }
    public static Review convertReviewDTOToReview(ReviewDTO review, ReviewDAO reviewDAO) throws BuildingSalesAppException {
        Integer id = review.getId();
        List<Review> byID = reviewDAO.findByID(id);
        if(byID.size()==0) throw new BuildingSalesAppException("Can't convert data from ReviewDTO");
        else return byID.get(0);
    }
}
