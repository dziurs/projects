package app.endpoint;

import app.converter.ConverterEntityToDTO;
import app.dao.MeetingDAO;
import app.dao.ReviewDAO;
import app.dao.UserDAO;
import app.dto.MeetingDTO;
import app.dto.ReviewDTO;
import app.dto.UserDTO;
import app.exception.BuildingSalesAppException;
import app.interceptor.DAOTransaction;
import app.manager.MeetingManager;
import app.model.entity.Meeting;
import app.model.entity.Review;
import app.model.entity.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.*;

@RequestScoped
@Transactional(Transactional.TxType.REQUIRES_NEW)
public class BuildingSalesEndpoint {

    @Inject
    private MeetingManager meetingManager;

    @Inject
    private ReviewDAO reviewDAO;

    @Inject
    private MeetingDAO meetingDAO;

    @Inject
    private UserDAO userDAO;

    @DAOTransaction
    public List<ReviewDTO> getAllReviews(){
        List<Review> listFromDataBase = reviewDAO.readAll();
        List<ReviewDTO> listDTO = new ArrayList<>();
        reviewIterator(listFromDataBase,listDTO);
        return listDTO;
    }
    @DAOTransaction
    public List<ReviewDTO> getTenReviews(int minRange){
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
    @DAOTransaction
    public void acceptMeetingEndpoint(UserDTO userDTO, MeetingDTO meetingDTO) throws BuildingSalesAppException {
        List<Meeting> meetings = meetingDAO.findByID(meetingDTO.getId());
        List<User> users = userDAO.findByEmail(userDTO.getEmail());
        if(meetings.size()==0||users.size()==0)
            throw new BuildingSalesAppException("Cant find user or meeting");
        else {
            Meeting meeting = meetings.get(0);
            User user = users.get(0);
            meetingManager.acceptMeeting(user,meeting);
        }
    }
    @DAOTransaction
    public List<ReviewDTO> getUsersReviews(UserDTO userDTO) throws BuildingSalesAppException {
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        List<User> users = userDAO.findByEmail(userDTO.getEmail());
        if(users.size()!=1)  throw new BuildingSalesAppException("Cant find user");
        else {
            User user = users.get(0);
            Set<Review> reviews = user.getReviews();
            List<Review> list = new ArrayList(reviews);
            reviewIterator(list,reviewDTOList);
        }
        return reviewDTOList;
    }
    @DAOTransaction List<MeetingDTO> getMeeting(UserDTO userDTO) throws BuildingSalesAppException {
        List<User> users = userDAO.findByEmail(userDTO.getEmail());
        List<MeetingDTO> meetingDTOList = new ArrayList<>();
        User user;
        if(users.size()!=1)  throw new BuildingSalesAppException("Cant find user");
        else user = users.get(0);
        List<Meeting> meetingListByUser = meetingDAO.findByUser(user);
        Iterator<Meeting> iterator = meetingListByUser.iterator();
        while (iterator.hasNext()){
            Meeting nextMeeting = iterator.next();
            MeetingDTO meetingDTO = ConverterEntityToDTO.convertMeetingToMeetingDTO(nextMeeting);
            meetingDTOList.add(meetingDTO);
        }
        return meetingDTOList;
    }

}
