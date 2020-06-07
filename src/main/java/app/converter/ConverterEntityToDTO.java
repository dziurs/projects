package app.converter;

import app.dto.DeveloperDTO;
import app.dto.MeetingDTO;
import app.dto.ReviewDTO;
import app.dto.UserDTO;
import app.model.entity.Developer;
import app.model.entity.Meeting;
import app.model.entity.Review;
import app.model.entity.User;

public class ConverterEntityToDTO {

    private ConverterEntityToDTO() {
    }
    public static UserDTO convertUserToUserDTO(User user){
        return new UserDTO(user.getName(), user.getFirstName(), user.getEmail().getEmail());
    }
    public static DeveloperDTO convertDeveloperToDeveloperDTO(Developer developer){
        return new DeveloperDTO(developer.getName(),developer.getFirstName(),developer.getCompanyName(),developer.getEmail().getEmail());
    }
    public static MeetingDTO convertMeetingToMeetingDTO(Meeting meeting){
        return new MeetingDTO(meeting.getId(),meeting.getDate(),meeting.isAddedByUser(),
                ConverterEntityToDTO.convertUserToUserDTO(meeting.getUser()),ConverterEntityToDTO.convertReviewToReviewDTO(meeting.getReview()));
    }
    public static ReviewDTO convertReviewToReviewDTO(Review review){
        return new ReviewDTO(review.getId(),review.getTitle(),review.getArea(),review.getBuildingType(),review.getLivingSpace(),
        review.isGarage(),review.getCity(),review.getStreet(),review.getPostCode(),review.getImage_url(),
                ConverterEntityToDTO.convertDeveloperToDeveloperDTO(review.getDeveloper()));
    }
}
