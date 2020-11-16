package app.web;

import app.dto.ReviewDTO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

@RequestScoped
@Named(value = "addMeetingController")
public class AddMeetingController implements Serializable {

    public String addMeeting(ReviewDTO reviewDTO){
        return "addMeetingToReview";
    }
}
