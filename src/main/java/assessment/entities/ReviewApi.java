package assessment.entities;

import assessment.entities.Feedback;
import assessment.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Range;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by dsloane on 6/1/2016.
 */
public class ReviewApi {

    private String id;
    private String reviewedId;
    private User reviewer;
    @JsonFormat(pattern = "MM/dd/yyyy", timezone="PST")
    private Date submittedDate;
    private Set<Feedback> feedback;
    private String teamName;
    @Range(min = 1, max = 5)
    private Double summaryScore;


    public ReviewApi(String id, User reviewer, String reviewedId, Date submittedDate, Set<Feedback> feedback,
                  Double summaryScore, String teamName) {
        this.id = id;
        this.reviewer = reviewer;
        this.reviewedId = reviewedId;
        this.submittedDate = submittedDate;
        this.feedback = feedback;
        this.summaryScore = summaryScore;
        this.teamName = teamName;
    }

    /**
     * Getters and Setters
     */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewedId() {
        return reviewedId;
    }

    public void setReviewedId(String reviewedId) {
        this.reviewedId = reviewedId;
    }

    public Date getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    public Set<Feedback> getFeedback() {
        return feedback;
    }

    public void setFeedback(Set<Feedback> feedback) {
        this.feedback = feedback;
    }

    public Double getSummaryScore() {
        return summaryScore;
    }

    public void setSummaryScore(Double summaryScore) {
        this.summaryScore = summaryScore;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", reviewer=" + reviewer +
                ", reviewedId=" + reviewedId +
                ", submittedDate=" + submittedDate +
                ", feedback=" + feedback +
                ", summaryScore=" + summaryScore +
                ", teamName=" + teamName +
                "}";
    }


}
