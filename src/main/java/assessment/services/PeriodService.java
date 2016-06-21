package assessment.services;

import assessment.entities.*;
import assessment.entities.team.Frequency;
import assessment.entities.team.ReviewPeriod;
import assessment.entities.team.Team;
import assessment.services.interfaces.IPeriodService;
import assessment.utilities.UrlConstants;
import assessment.utilities.httpclient.jsonparser.DataJsonParser;
import org.apache.http.HttpException;
import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by dsloane on 6/20/2016.
 */
@Service
public class PeriodService extends BaseService<Period> implements IPeriodService {

    public PeriodService() {
        super(UrlConstants.DATA_URL_PERIODS, Period.class, new DataJsonParser());
    }

    public List<PeriodApi> getPeriodsByTeamAndUser(TeamApi team, String userId) throws HttpException {

        // get all periods
        List<Period> allPeriods = client.getAll(url + "?size=3000000", Period.class);

        System.out.println("got all periods, count = " + allPeriods.size());
        List<Period> periodsByTeamAndUser = new ArrayList<>();

        // pare list down to just periods for given team with reviews for given user
        for (Period period : allPeriods) {
            if (period.getTeamId().equals(team.getId())) {
                for (Review review : period.getReviews()) {
                    if (review.getReviewedId().equals(userId)) {
                        periodsByTeamAndUser.add(period);
                        break;
                    }
                }
            }
        }

        System.out.println("got periods by team and user, team = " + team.getName() + ", count = " + periodsByTeamAndUser.size());

        // pare down reviews in each period to only those given to the user
        for (Period period : periodsByTeamAndUser) {
            Set<Review> reviews = new HashSet<>();
            for (Review review : period.getReviews()) {
                if (review.getId().equals(userId)) {
                    reviews.add(review);
                }
            }
            period.setReviews(reviews);
        }

        UserService userService = new UserService();
        TemplateService templateService = new TemplateService();
        TeamService teamService = new TeamService();
        ReviewService reviewService = new ReviewService();

        List<PeriodApi> periodApis = new ArrayList<>();

        // translate into custom object for Android
        for (Period period : periodsByTeamAndUser) {
            PeriodApi periodApi = new PeriodApi();
            periodApi.setId(period.getId());
            periodApi.setTemplate(null);
            //periodApi.setTemplate(templateService.get(period.getTemplateId()));
            periodApi.setTeam(team);
            for (ReviewPeriod rp : team.getReviewPeriods()) {
                if (rp.getCurrentPeriodId().equals(period.getId())) {
                    System.out.println("found the review period");
                    Date dueDate = new Date();
                    Long dateTriggeredInMS = period.getDateTriggered().getTime();

                    if (rp.getFrequency() == Frequency.WEEK) {
                        dueDate = new Date(dateTriggeredInMS + (86400000 * 7));
                    } else if(rp.getFrequency() == Frequency.TWO_WEEKS) {
                        dueDate = new Date(dateTriggeredInMS + (86400000 * 14));
                    } else if(rp.getFrequency() == Frequency.MONTH) {
                        dueDate = new Date(dateTriggeredInMS + (86400000 * 30));
                    } else {
                        dueDate = null;
                    }

                    periodApi.setDueDate(dueDate);
                }
            }

            List<ReviewApi> reviewApis = new ArrayList<>();
            Double averageScore = 0.0;

            for (Review review : period.getReviews()) {
                averageScore += review.getSummaryScore();

                ReviewApi reviewApi = new ReviewApi();

                reviewApi.setSummaryScore(review.getSummaryScore());
                reviewApi.setFeedback(review.getFeedback());
                reviewApi.setId(review.getId());
                reviewApi.setReviewedId(review.getReviewedId());
                reviewApi.setTeamName(review.getTeamName());
                reviewApi.setSubmittedDate(review.getSubmittedDate());
                reviewApi.setReviewer(userService.get(review.getReviewerId()));

                reviewApis.add(reviewApi);
            }

            averageScore = averageScore/(period.getReviews().size());

            periodApi.setSummaryScore(averageScore);
            periodApi.setReviews(reviewApis);


            periodApis.add(periodApi);

        }


        return periodApis;
    }




}
