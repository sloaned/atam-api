package assessment.controllers;

import assessment.entities.review.Review;
import assessment.services.interfaces.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/reviews")
public class ReviewController extends BaseController<Review> {

    @Autowired
    public ReviewController(IReviewService service) {
        super(service);
    }
}
