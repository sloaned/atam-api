package assessment.entities;

import java.util.Date;
import java.util.List;

/**
 * Created by dsloane on 6/21/2016.
 */
public class PeriodApi {

    private String id;
    // the template of questions for this period of reviews
    private Template template;
    // the team this period of reviews is for
    private TeamApi team;
    // when this period of reviews is/was due
    private Date dueDate;
    // average score of reviews in this period
    private Double summaryScore;
    // list of all reviews
    private List<ReviewApi> reviews;

    // constructors
    public PeriodApi () {}

    public PeriodApi (String id, Template template, TeamApi team, Date dueDate,
                  Double summaryScore, List<ReviewApi> reviews) {
        this.id = id;
        this.template = template;
        this.team = team;
        this.dueDate = dueDate;
        this.summaryScore = summaryScore;
        this.reviews = reviews;
    }

    // getters & setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id;}
    public Template getTemplate() { return template; }
    public void setTemplate(Template template) { this.template = template; }
    public TeamApi getTeam() { return team ; }
    public void setTeam(TeamApi team) { this.team = team; }
    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    public Double getSummaryScore() { return summaryScore; }
    public void setSummaryScore(Double summaryScore) { this.summaryScore = summaryScore; }
    public List<ReviewApi> getReviews() { return reviews; }
    public void setReviews(List<ReviewApi> reviews) { this.reviews = reviews; }
}
