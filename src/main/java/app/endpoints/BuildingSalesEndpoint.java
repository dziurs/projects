package app.endpoints;

import app.converter.ConverterDTOToEntity;
import app.converter.ConverterEntityToDTO;
import app.dao.*;
import app.dto.DeveloperDTO;
import app.dto.MeetingDTO;
import app.dto.ReviewDTO;
import app.dto.UserDTO;
import app.exception.*;
import app.interceptor.DAOTransaction;
import app.manager.MeetingManager;
import app.model.entity.Developer;
import app.model.entity.Meeting;
import app.model.entity.Review;
import app.model.entity.User;
import app.security.Account;
import app.security.Crypter;

import javax.annotation.security.PermitAll;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.*;

@Stateless
@Local
@PermitAll
@Transactional(Transactional.TxType.REQUIRES_NEW)
public class BuildingSalesEndpoint implements Serializable {

    private final String roleUser = "USER";
    private final String roleDeveloper = "DEVELOPER";

    @Inject
    private MeetingManager meetingManager;

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
        Account account = new Account();
        account.setLogin(developerDTO.getEmail());
        account.setActivate(false);
        account.setPid(new Random().nextInt(100000));
        account.setPassword(Crypter.crypt(password));
        account.setRole(roleDeveloper);
        Developer developer = ConverterDTOToEntity.convertDeveloperDTOToDeveloper(developerDTO, developerDAO);
        developerDAO.create(developer);
        accountDAO.create(account);
    }
    public int registerUser(UserDTO userDTO,String password) throws BuildingSalesAppException {
            Account account = new Account();
            account.setLogin(userDTO.getEmail());
            account.setActivate(false);
            int pid = new Random().nextInt(100000);
            account.setPid(pid);
            account.setPassword(Crypter.crypt(password));
            account.setRole(roleUser);
            User user = ConverterDTOToEntity.convertUserDTOToUser(userDTO, userDAO);
            userDAO.create(user);
            accountDAO.create(account);
            return pid;
    }
    public void activateUserAccount(String login, String pid) throws EmailSendingException {
        List<Account> list = accountDAO.findByEmail(login);
        if(list.size()==1){
            Account account = list.get(0);
            int pidFromDatabase = account.getPid();
            if(pid.equals(String.valueOf(pidFromDatabase))){
                account.setActivate(true);
            }
            else {
                throw new EmailSendingException(EmailSendingException.EMAIL_OR_PID_ERROR);
            }
        }else {
            throw new EmailSendingException(EmailSendingException.EMAIL_OR_PID_ERROR);
        }
    }
    public Account findAccountByLogin(String login) throws BuildingSalesAppException {
        List<Account> byEmail = accountDAO.findByEmail(login);
        if(byEmail.size()==0) throw new AccountException(AccountException.LOGIN);
        else return byEmail.get(0);
    }
    public void addReview (Principal principal, Part file, ReviewDTO reviewDTO) throws BuildingSalesAppException{
        if(principal==null) throw new GeneralAplicationException(GeneralAplicationException.PRINCIPAL);
        List<Developer> list = developerDAO.findByEmail(principal.getName());
        if(list.size()==0) throw new AccountException(AccountException.LOGIN);
        Developer developer = list.get(0);
        Review review = ConverterDTOToEntity.convertReviewDTOToReview(reviewDTO, reviewDAO);
        review.setDeveloper(developer);
        developer.addReview(review);
        reviewDAO.create(review);
        developerDAO.update(developer);
        Review merge = reviewDAO.merge(review);
        Integer id = merge.getId();
        String fileName = file.getSubmittedFileName();
        int length = fileName.length();
        fileName = fileName.substring(length-4,length);
        fileName = String.valueOf(id).concat(fileName);
        try {
            file.write(fileName);
            merge.setImage_url(fileName);
            reviewDAO.update(merge);
        }catch (IOException e){
            throw new ImegeFileIOException(ImegeFileIOException.IMAGE_FILE, e);
        }

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
    public void reviewIterator(List<Review> listOfEntity, List<ReviewDTO> listOfDTO){
        Iterator<Review> iterator = listOfEntity.iterator();
        while (iterator.hasNext()) {
            Review next = iterator.next();
            ReviewDTO reviewDTO = ConverterEntityToDTO.convertReviewToReviewDTO(next);
            listOfDTO.add(reviewDTO);
        }
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
