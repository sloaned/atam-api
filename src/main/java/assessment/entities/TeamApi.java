package assessment.entities;

import assessment.entities.team.Member;
import assessment.entities.team.ReviewPeriod;
import assessment.utilities.Constants;
import assessment.utilities.RegexConstants;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Set;

/**
 * Created by dsloane on 5/25/2016.
 */
public class TeamApi {

    @Id
    private String id;

    @Indexed(unique = true)
    @Length(max = 100, message = "Team name can be no longer than 100 characters.")
    @Pattern(regexp = RegexConstants.OBJECT_NAME, message = "Team name contains invalid characters.")
    private String name;

    private Boolean isActive;

    private Set<MemberApi> memberList;

    private String avatar;

    @Length(max = 255, message = "Team description length must not exceed " +
            "255 characters.")
    private String description;

    private Set<ReviewPeriod> reviewPeriods;

    @Range(min = 1, max = 5)
    private Double summaryScore;

    @Range(min = 1, message = "version of at least 1 is required")
    private Integer version;

    public TeamApi() {
        this.version = Constants.TEAM_CURRENT_VERSION;
    }

    public TeamApi(String name, Boolean isActive, Set<MemberApi> memberList, String avatar, String description, Set<ReviewPeriod> reviewPeriods, Double summaryScore) {
        this.name = name;
        this.isActive = isActive;
        this.memberList = memberList;
        this.avatar = avatar;
        this.description = description;
        this.reviewPeriods = reviewPeriods;
        this.summaryScore = summaryScore;
        this.version = Constants.TEAM_CURRENT_VERSION;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Set<MemberApi> getMemberList() {
        return memberList;
    }

    public void setMemberList(Set<MemberApi> memberList) {
        this.memberList = memberList;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ReviewPeriod> getReviewPeriods() {
        return reviewPeriods;
    }

    public void setReviewPeriods(Set<ReviewPeriod> reviewPeriods) {
        this.reviewPeriods = reviewPeriods;
    }

    public Double getSummaryScore() {
        return summaryScore;
    }

    public void setSummaryScore(Double summaryScore) {
        this.summaryScore = summaryScore;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isActive=" + isActive +
                ", memberList=" + memberList +
                ", avatar='" + avatar + '\'' +
                ", description='" + description + '\'' +
                ", reviewPeriods=" + reviewPeriods +
                ", summaryScore=" + summaryScore +
                ", version=" + version +
                '}';
    }
}
