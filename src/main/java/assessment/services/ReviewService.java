package assessment.services;

import assessment.entities.review.Review;
import assessment.services.interfaces.IReviewService;
import assessment.utilities.UrlConstants;
import assessment.utilities.httpclient.jsonparser.DataJsonParser;
import org.springframework.stereotype.Service;

@Service
public class ReviewService extends BaseService<Review> implements IReviewService {

    public ReviewService() {
        super(UrlConstants.DATA_URL_REVIEWS, Review.class, new DataJsonParser());
    }
}
