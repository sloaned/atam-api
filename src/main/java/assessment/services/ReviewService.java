package assessment.services;

import assessment.entities.*;
import assessment.entities.team.ReviewPeriod;
import assessment.entities.team.Team;
import assessment.entities.team.TeamStatus;
import assessment.services.interfaces.IReviewApiService;
import assessment.utilities.UrlConstants;
import assessment.utilities.httpclient.jsonparser.DataJsonParser;
import org.apache.http.HttpException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ReviewService extends BaseService<ReviewApi> implements IReviewApiService {

    public ReviewService() {
        super(UrlConstants.DATA_URL_REVIEWS, ReviewApi.class, new DataJsonParser());
    }

    @Override
    public List<ReviewApi> getAll() throws HttpException {

        List<ReviewApi> reviews = new ArrayList<>();

        List<Review> reviewList = client.getAll(url + "?size=3000000", Review.class);

        UserService userService = new UserService();

        for (Review review: reviewList) {
            ReviewApi reviewApi = new ReviewApi();

            reviewApi.setSummaryScore(review.getSummaryScore());
            reviewApi.setFeedback(review.getFeedback());
            reviewApi.setId(review.getId());
            reviewApi.setReviewedId(review.getReviewedId());
            reviewApi.setTeamName(review.getTeamName());
            reviewApi.setSubmittedDate(review.getSubmittedDate());
            reviewApi.setReviewer(userService.get(review.getReviewerId()));

            reviews.add(reviewApi);

        }

        return reviews;
    }

    @Override
    public ReviewApi get(String id) throws HttpException {
        ReviewApi reviewApi = new ReviewApi();

        UserService userService = new UserService();

        Review review = client.get(url + "/" + id, Review.class);
        reviewApi.setSummaryScore(review.getSummaryScore());
        reviewApi.setFeedback(review.getFeedback());
        reviewApi.setId(review.getId());
        reviewApi.setReviewedId(review.getReviewedId());
        reviewApi.setTeamName(review.getTeamName());
        reviewApi.setSubmittedDate(review.getSubmittedDate());
        reviewApi.setReviewer(userService.get(review.getReviewerId()));

        return reviewApi;
    }

    @Override
    public ReviewApi create(ReviewApi reviewApi) throws HttpException {
        Review review = new Review(reviewApi.getReviewer().getId(), reviewApi.getReviewedId(), reviewApi.getSubmittedDate(),
                reviewApi.getFeedback(), reviewApi.getSummaryScore(), reviewApi.getTeamName());

        Review returnReview = client.post(url, review, Review.class);
        if (returnReview == review) {
            return reviewApi;
        }
        return null;
    }

    @Override
    public ReviewApi update(String id, ReviewApi reviewApi) throws HttpException {
        //Need to make sure it exists otherwise mongo will create it with the id because mongo is the worst
        try {
            client.get(url + "/" + id, Review.class);
        } catch (HttpException e) {
            logger.error(e);
            throw e;
        }

        Review review = new Review(reviewApi.getReviewer().getId(), reviewApi.getReviewedId(), reviewApi.getSubmittedDate(),
                reviewApi.getFeedback(), reviewApi.getSummaryScore(), reviewApi.getTeamName());

        Review returnReview = client.put(url + "/" + id, review, Review.class);
        if (returnReview == review) {
            return reviewApi;
        }
        return null;
    }


    public List<ReviewApi> getReviewsByUser(String id) throws HttpException {
        ArrayList<ReviewApi> reviews = new ArrayList<ReviewApi>();

        List<Review> reviewList = client.getAll(url, Review.class);

        for (Review review : reviewList) {

            if (review.getReviewedId().equals(id)) {

                UserService userService = new UserService();
                User reviewer = userService.get(review.getReviewerId());

                ReviewApi reviewApi = new ReviewApi(review.getId(), reviewer, review.getReviewedId(), review.getSubmittedDate(),
                        review.getFeedback(), review.getSummaryScore(), review.getTeamName());

                reviews.add(reviewApi);
            }

        }

        return reviews;
    }

    /**
     *
     * @param user - user whose average reviews we want
     * @return a list of average reviews by team
     */
    public List<ReviewApi> getMobileReviewsByUser(User user, List<TeamApi> teams) throws HttpException {

       // TeamService teamService = new TeamService();
        PeriodService periodService = new PeriodService();

        List<ReviewApi> reviews = new ArrayList<>();

        System.out.println("in getMobileReviewsByUser!");

        for (TeamApi team : teams) {
            for (ReviewPeriod rp : team.getReviewPeriods()) {
                // needs to be updated to previous review period instead of current
                Period period = periodService.get(rp.getCurrentPeriodId());
                ReviewApi reviewApi = getTeamReview(user.getId(), period.getReviews());
                if (reviewApi != null) {
                    reviews.add(reviewApi);
                }
            }
        }
/*
        for (TeamStatus teamStatus : user.getTeamStatus()) {
            System.out.println(teamStatus);
            TeamApi team = teamService.get(teamStatus.getTeamId());
            for (ReviewPeriod rp : team.getReviewPeriods()) {
                // needs to be updated to previous review period instead of current
                Period period = periodService.get(rp.getCurrentPeriodId());
                ReviewApi reviewApi = getTeamReview(user.getId(), period.getReviews());
                if (reviewApi != null) {
                    reviews.add(reviewApi);
                }
            }
        } */

        return reviews;
    }

    /**
     *
     * @param reviewedId - id of the team member whose average score we want
     * @param reviews - list of all reviews in a given period for a given team
     * @return a ReviewApi object for that user with an average summary score of every review received in that period
     */
    public ReviewApi getTeamReview(String reviewedId, Set<Review> reviews) {

        System.out.println("in getTeamReview");

        if (reviews.isEmpty()) {
            return null;
        }

        ReviewApi reviewApi = new ReviewApi();
        reviewApi.setReviewedId(reviewedId);

        Double average = 0.0;

        for (Review review : reviews) {
            reviewApi.setTeamName(review.getTeamName());
            if (review.getReviewedId().equals(reviewedId)) {
                average += review.getSummaryScore();
            }

        }
        // the user did not have any reviews in the list
        if (average == 0.0) {
            return null;
        }

        average = average/reviews.size();
        reviewApi.setSummaryScore(average);

        return reviewApi;

    }
}
