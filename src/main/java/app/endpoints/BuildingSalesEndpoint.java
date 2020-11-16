package app.endpoints;

import app.converter.ConverterDTOToEntity;
import app.converter.ConverterEntityToDTO;
import app.dao.*;
import app.dto.*;
import app.exception.*;
import app.managers.*;
import app.model.entity.Developer;
import app.model.entity.Meeting;
import app.model.entity.Review;
import app.model.entity.User;
import app.security.Account;
import javax.annotation.security.PermitAll;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.security.Principal;
import java.util.*;

@Stateless
@Local
@PermitAll
@Transactional(Transactional.TxType.REQUIRES_NEW)
public class BuildingSalesEndpoint implements Serializable {

    @Inject
    private AccountManager accountManager;

    @Inject
    private MeetingManager meetingManager;

    @Inject
    private ReviewManager reviewManager;

    @Inject
    private ReviewDAO reviewDAO;

    @Inject
    private MeetingDAO meetingDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private DeveloperDAO developerDAO;

    @Inject
    private AccountDAO accountDAO;

    private ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages");


    public void registerDeveloper(DeveloperDTO developerDTO,String password) throws BuildingSalesAppException {
        Developer developer = ConverterDTOToEntity.convertDeveloperDTOToDeveloper(developerDTO, developerDAO);
        accountManager.registerDeveloper(developer,password);

    }
    public int registerUser(UserDTO userDTO,String password) throws BuildingSalesAppException {
        User user = ConverterDTOToEntity.convertUserDTOToUser(userDTO, userDAO);
        int pid = accountManager.registerUser(user, password);
        return pid;
    }

    public void activateUserAccount(String login, String pid) throws EmailSendingException {
        accountManager.activateUserAccount(login,pid);
    }

    public Account findAccountByLogin(String login) throws BuildingSalesAppException {
        List<Account> byEmail = accountDAO.findByEmail(login);
        if(byEmail.size()==0) throw new AccountException(AccountException.LOGIN);
        else return byEmail.get(0);
    }

    public void addReview (Principal principal, ReviewDTO reviewDTO) throws BuildingSalesAppException{
        if(principal==null) throw new GeneralAplicationException(GeneralAplicationException.PRINCIPAL);
        List<Developer> list = developerDAO.findByEmail(principal.getName());
        if(list.size()==0) throw new AccountException(AccountException.LOGIN);
        Developer developer = list.get(0);
        Review review = ConverterDTOToEntity.convertReviewDTOToReview(reviewDTO, reviewDAO);
        reviewManager.addReview(developer,review);
    }
    public List<ReviewDTO> getDeveloperReviews(String developerLogin){
        return reviewManager.getDeveloperReviews(developerLogin);
    }

    public void reviewDelete(ReviewDTO reviewDTO)throws BuildingSalesAppException{
        Integer id = reviewDTO.getId();
        List<Review> reviews = reviewDAO.findByID(id);
        if(reviews.size()==0) throw new GeneralAplicationException(GeneralAplicationException.REVIEW);
        Review review = reviews.get(0);
        reviewManager.reviewDelete(review);
    }
    public List<MeetingDTO> getMeetingList(ReviewDTO reviewDTO) throws BuildingSalesAppException {
        List<Review> list = reviewDAO.findByID(reviewDTO.getId());
        if(list.size()==0) throw new GeneralAplicationException(GeneralAplicationException.REVIEW);
        Review review = list.get(0);
        List<Meeting> meetingList = meetingManager.getMeetingList(review);
        List<MeetingDTO> meetingDTOList = meetingIterator(meetingList);
        return meetingDTOList;
    }


    public List<ReviewDTO> getAllReviews(){
        List<Review> listFromDataBase = reviewDAO.readAll();
        List<ReviewDTO> listDTO = new ArrayList<>();
        reviewIterator(listFromDataBase,listDTO);
        return listDTO;
    }
    public List<ReviewDTO> getTenReviews(int minRange){ //brak count
        List<ReviewDTO> listTenDTOReviews = new ArrayList<>();
        if(minRange>=0) {
            List<Review> tenReviews = reviewDAO.readRange(minRange, minRange + 9);
            reviewIterator(tenReviews,listTenDTOReviews);
        }
        return listTenDTOReviews;
    }
    private void reviewIterator(List<Review> listOfEntity, List<ReviewDTO> listOfDTO){
        Iterator<Review> iterator = listOfEntity.iterator();
        while (iterator.hasNext()) {
            Review next = iterator.next();
            ReviewDTO reviewDTO = ConverterEntityToDTO.convertReviewToReviewDTO(next);
            listOfDTO.add(reviewDTO);
        }
    }
    private List<MeetingDTO> meetingIterator(List<Meeting> listOfEntity){
        List<MeetingDTO> meetingDTOList = new ArrayList<>();
        Iterator<Meeting> iterator = listOfEntity.iterator();
        while (iterator.hasNext()) {
            Meeting next = iterator.next();
            MeetingDTO meetingDTO = ConverterEntityToDTO.convertMeetingToMeetingDTO(next);
            meetingDTOList.add(meetingDTO);
        }
        return meetingDTOList;
    }
//    @DAOTransaction
//    public void acceptMeetingEndpoint(UserDTO userDTO, MeetingDTO meetingDTO) throws BuildingSalesAppException {
//        List<Meeting> meetings = meetingDAO.findByID(meetingDTO.getId());
//        List<User> users = userDAO.findByEmail(userDTO.getEmail());
//        if(meetings.size()==0||users.size()==0)
//            throw new BuildingSalesAppException("Cant find user or meeting");
//        else {
//            Meeting meeting = meetings.get(0);
//            User user = users.get(0);
//            meetingManager.acceptMeeting(user,meeting);
//        }
//    }
//    public List<ReviewDTO> getUsersReviews(UserDTO userDTO) throws BuildingSalesAppException {
//        List<ReviewDTO> reviewDTOList = new ArrayList<>();
//        List<User> users = userDAO.findByEmail(userDTO.getEmail());
//        if(users.size()!=1)  throw new BuildingSalesAppException("Cant find user");
//        else {
//            User user = users.get(0);
//            Set<Review> reviews = user.getReviews();
//            List<Review> list = new ArrayList(reviews);
//            reviewIterator(list,reviewDTOList);
//        }
//        return reviewDTOList;
//    }
//    public List<MeetingDTO> getMeeting(UserDTO userDTO) throws BuildingSalesAppException {
//        List<User> users = userDAO.findByEmail(userDTO.getEmail());
//        List<MeetingDTO> meetingDTOList = new ArrayList<>();
//        User user;
//        if(users.size()!=1)  throw new BuildingSalesAppException("Cant find user");
//        else user = users.get(0);
//        List<Meeting> meetingListByUser = meetingDAO.findByUser(user);
//        Iterator<Meeting> iterator = meetingListByUser.iterator();
//        while (iterator.hasNext()){
//            Meeting nextMeeting = iterator.next();
//            MeetingDTO meetingDTO = ConverterEntityToDTO.convertMeetingToMeetingDTO(nextMeeting);
//            meetingDTOList.add(meetingDTO);
//        }
//        return meetingDTOList;
//    }

}
