package assessment.entities;

import assessment.utilities.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by dsloane on 5/26/2016.
 */
public class KudoApi {


    @Id
    private String id;

    private User reviewer;

    private String reviewedId;

    @Length(max = 1500, message = "Comment length must not exceed 1,500 " +
            "characters")
    private String comment;

    @JsonFormat(pattern = "MM/dd/yyyy", timezone="PST")
    private Date submittedDate;

    @Range(min = 1, message = "version of at least 1 is required")
    private Integer version;

    public KudoApi() {
        this.version = Constants.KUDO_CURRENT_VERSION;
    }

    public KudoApi(User reviewer, String reviewedId, String comment, Date submittedDate) {
        this.reviewer = reviewer;
        this.reviewedId = reviewedId;
        this.comment = comment;
        this.submittedDate = submittedDate;
        this.version = Constants.KUDO_CURRENT_VERSION;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public void setReviewedId(String reviewedId) {
        this.reviewedId = reviewedId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public User getReviewer() {
        return reviewer;
    }

    public String getReviewedId() {
        return reviewedId;
    }

    public String getComment() {
        return comment;
    }

    public Date getSubmittedDate() {
        return submittedDate;
    }

    public Integer getVersion() {
        return version;
    }

    @AssertTrue(message = "Reviewer and reviewee cannot be the same user")
    private boolean isSelfReview() {
        boolean result = true;

        //Only validate the self review case if they both have values
        if (reviewer.getId() != null && reviewedId != null) {

            if (reviewer.getId().equals(reviewedId)){
                result = false;
            }

        }

        return result;
    }

    @AssertTrue(message = "Submitted date cannot be in the past")
    private boolean dateNotPast() {
        boolean result = true;
        int offset = 60 * 1000;
        Date currentDate = new Date();
        Date theSubmittedDate = new Date(submittedDate.getTime() + offset);
        int compareResult = currentDate.compareTo(theSubmittedDate);
        result = compareResult >= 0;

        return result;
    }
}
