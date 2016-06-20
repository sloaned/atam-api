package assessment.services;

import assessment.entities.ReviewApi;
import assessment.entities.Review;
import assessment.entities.User;
import assessment.services.interfaces.IReviewApiService;
import assessment.utilities.UrlConstants;
import assessment.utilities.httpclient.jsonparser.DataJsonParser;
import org.apache.http.HttpException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService extends BaseService<ReviewApi> implements IReviewApiService {

    public ReviewService() {
        super(UrlConstants.DATA_URL_REVIEWS, ReviewApi.class, new DataJsonParser());
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
}
