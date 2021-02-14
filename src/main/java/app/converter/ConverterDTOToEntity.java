package app.converter;

import app.dao.*;
import app.dto.*;
import app.exception.AccountException;
import app.exception.BuildingSalesAppException;
import app.exception.GeneralApplicationException;
import app.model.entity.*;
import app.security.Account;

import java.util.List;

public class ConverterDTOToEntity {

    private ConverterDTOToEntity() {
    }

    public static User convertUserDTOToUser(UserDTO user, UserDAO userDAO) {
        String email = user.getEmail();
        List<User> userByEmail = userDAO.findByEmail(email);
        if (userByEmail.size() == 0) {
            User u = new User();
            u.setFirstName(user.getFirstName());
            u.setSurname(user.getSurname());
            u.setEmail(new Email(user.getEmail()));
            return u;
        } else return userByEmail.get(0);
    }

    public static Developer convertDeveloperDTOToDeveloper(DeveloperDTO developer, DeveloperDAO developerDAO) {
        String email = developer.getEmail();
        List<Developer> byEmail = developerDAO.findByEmail(email);
        if (byEmail.size() == 0) {
            Developer d = new Developer();
            d.setSurname(developer.getSurname());
            d.setFirstName(developer.getFirstName());
            d.setCompanyName(developer.getCompanyName());
            d.setEmail(new Email(developer.getEmail()));
            return d;
        } else return byEmail.get(0);
    }

    public static Meeting convertMeetingDTOToMeeting(MeetingDTO meeting, MeetingDAO meetingDAO) {
        Integer id = meeting.getId();
        if (id > 0) {
            List<Meeting> byID = meetingDAO.findByID(id);
            return byID.get(0);
        } else {
            Meeting m = new Meeting();
            m.setDate(meeting.getDate());
            m.setAddedByUser(meeting.isAddedByUser());
            m.setUser(null);
            return m;
        }
    }

    public static Review convertReviewDTOToReview(ReviewDTO review, ReviewDAO reviewDAO) {
        Integer id = review.getId();
        if (id > 0) {
            List<Review> byID = reviewDAO.findByID(id);
            return byID.get(0);
        } else {
            Review r = new Review();
            simplyConvertReview(review, r);
            //r.setDeveloper(ConverterDTOToEntity.convertDeveloperDTOToDeveloper(review.getDeveloper(),developerDAO));
            return r;
        }
    }

    public static Review convertReviewDTOToReviewWithEdit(ReviewDTO reviewDTO, ReviewDAO reviewDAO) throws GeneralApplicationException {
        List<Review> byID = reviewDAO.findByID(reviewDTO.getId());
        if (byID.size() == 0) throw new GeneralApplicationException(GeneralApplicationException.KEY_OPTIMISTIC_LOCK);
        Review r = byID.get(0);
        return simplyConvertReview(reviewDTO, r);
    }

    public static Developer convertDeveloperDTOToDeveloperWithEdit(DeveloperDTO developerDTO, DeveloperDAO developerDAO) throws GeneralApplicationException {
        List<Developer> list = developerDAO.findByEmail(developerDTO.getEmail());
        if (list.size() == 0) throw new GeneralApplicationException(GeneralApplicationException.KEY_OPTIMISTIC_LOCK);
        Developer developer = list.get(0);
        return simplyConvertDeveloper(developerDTO, developer);
    }

    public static User convertUserDTOToUserWithEdit(UserDTO userDTO, UserDAO userDAO) throws GeneralApplicationException {
        List<User> list = userDAO.findByEmail(userDTO.getEmail());
        if (list.size() == 0) throw new GeneralApplicationException(GeneralApplicationException.KEY_OPTIMISTIC_LOCK);
        User user = list.get(0);
        return simplyConvertUser(userDTO, user);
    }

    private static Review simplyConvertReview(ReviewDTO reviewDTO, Review review) {
        review.setImage(reviewDTO.getImage()); // this method must be first - lazy fetch
        review.setTitle(reviewDTO.getTitle());
        review.setArea(reviewDTO.getArea());
        review.setLivingSpace(reviewDTO.getLivingSpace());
        review.setGarage(reviewDTO.isGarage());
        review.setCity(reviewDTO.getCity());
        review.setStreet(reviewDTO.getStreet());
        review.setPostCode(reviewDTO.getPostCode());
        review.setBuildingType(reviewDTO.getBuildingType());
        return review;
    }

    private static Developer simplyConvertDeveloper(DeveloperDTO developerDTO, Developer developer) {
        developer.setFirstName(developerDTO.getFirstName());
        developer.setSurname(developerDTO.getSurname());
        developer.setCompanyName(developerDTO.getCompanyName());
        return developer;
    }

    private static User simplyConvertUser(UserDTO userDTO, User user) {
        user.setFirstName(userDTO.getFirstName());
        user.setSurname(userDTO.getSurname());
        return user;
    }

    public static Account convertAccountDTOToAccount(AccountDTO accountDTO, AccountDAO accountDAO) throws BuildingSalesAppException {
        List<Account> list = accountDAO.findByEmail(accountDTO.getLogin());
        if (list.size() == 0) {
            throw new AccountException(AccountException.KEY_OPTIMISTIC_LOCK);
        } else return list.get(0);
    }
}
