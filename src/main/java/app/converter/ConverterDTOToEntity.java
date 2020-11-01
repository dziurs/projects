package app.converter;

import app.dao.DeveloperDAO;
import app.dao.MeetingDAO;
import app.dao.ReviewDAO;
import app.dao.UserDAO;
import app.dto.DeveloperDTO;
import app.dto.MeetingDTO;
import app.dto.ReviewDTO;
import app.dto.UserDTO;
import app.model.entity.*;
import java.util.List;

public class ConverterDTOToEntity {

    private ConverterDTOToEntity() {
    }
    public static User convertUserDTOToUser(UserDTO user, UserDAO userDAO){
        String email = user.getEmail();
        List<User> userByEmail = userDAO.findByEmail(email);
        if(userByEmail.size()==0){
            User u = new User();
            u.setFirstName(user.getFirstName());
            u.setSurname(user.getSurname());
            u.setEmail(new Email(user.getEmail()));
            return u;
        }
        else return userByEmail.get(0);
    }
    public static Developer convertDeveloperDTOToDeveloper(DeveloperDTO developer, DeveloperDAO developerDAO){
        String email = developer.getEmail();
        List<Developer> byEmail = developerDAO.findByEmail(email);
        if(byEmail.size()==0){
            Developer d = new Developer();
            d.setSurname(developer.getSurname());
            d.setFirstName(developer.getFirstName());
            d.setCompanyName(developer.getCompanyName());
            d.setEmail(new Email(developer.getEmail()));
            return d;
        }
        else return byEmail.get(0);
    }
    public static Meeting convertMeetinDTOToMeeting(MeetingDTO meeting, MeetingDAO meetingDAO){
        Integer id = meeting.getId();
        if(id>0) {
            List<Meeting> byID = meetingDAO.findByID(id);
            return byID.get(0);
        }
        else {
            Meeting m = new Meeting();
            m.setDate(meeting.getDate());
            m.setAddedByUser(meeting.isAddedByUser());
            m.setUser(null);
            return m;
        }
    }
    public static Review convertReviewDTOToReview(ReviewDTO review, ReviewDAO reviewDAO){
        Integer id = review.getId();
        if(id>0){
            List<Review> byID = reviewDAO.findByID(id);
            return byID.get(0);
        }else{
            Review r = new Review();
            r.setTitle(review.getTitle());
            r.setArea(review.getArea());
            r.setLivingSpace(review.getLivingSpace());
            r.setGarage(review.isGarage());
            r.setCity(review.getCity());
            r.setStreet(review.getStreet());
            r.setPostCode(review.getPostCode());
            r.setBuildingType(review.getBuildingType());
            //r.setImage_url(review.getImage_url());
            //r.setDeveloper(ConverterDTOToEntity.convertDeveloperDTOToDeveloper(review.getDeveloper(),developerDAO));
            return r;
        }
    }
}
