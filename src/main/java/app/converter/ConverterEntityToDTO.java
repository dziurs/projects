package app.converter;

import app.dto.*;
import app.model.entity.Developer;
import app.model.entity.Meeting;
import app.model.entity.Review;
import app.model.entity.User;
import app.security.Account;

public class ConverterEntityToDTO {

    private ConverterEntityToDTO() {
    }
    public static UserDTO convertUserToUserDTO(User user){
        return new UserDTO(user.getSurame(), user.getFirstName(), user.getEmail().getEmail());
    }
    public static DeveloperDTO convertDeveloperToDeveloperDTO(Developer developer){
        return new DeveloperDTO(developer.getSurname(),developer.getFirstName(),developer.getCompanyName(),developer.getEmail().getEmail());
    }
    public static MeetingDTO convertMeetingToMeetingDTO(Meeting meeting){
        MeetingDTO meetingDTO = new MeetingDTO();
        meetingDTO.setDate(meeting.getDate());
        meetingDTO.setAddedByUser(meeting.isAddedByUser());
        if(meeting.getUser()==null) meetingDTO.setUser(null);
        else meetingDTO.setUser(ConverterEntityToDTO.convertUserToUserDTO(meeting.getUser()));
        return meetingDTO;

    }
    public static ReviewDTO convertReviewToReviewDTO(Review review){
        return new ReviewDTO(review.getId(),review.getTitle(),review.getArea(),review.getBuildingType(),review.getLivingSpace(),
        review.isGarage(),review.getCity(),review.getStreet(),review.getPostCode(),review.getImage(),
                ConverterEntityToDTO.convertDeveloperToDeveloperDTO(review.getDeveloper()));
    }
    public static AccountDTO convertAccountToAccountDTO (Account account){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setLogin(account.getLogin());
        accountDTO.setActivate(account.isActivate());
        accountDTO.setPid(account.getPid());
        accountDTO.setRole(account.getRole().name());
        return accountDTO;
    }
}
