package assessment.controllers;

import assessment.entities.ReviewApi;
import assessment.services.interfaces.IReviewApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/reviews")
public class ReviewController extends BaseController<ReviewApi> {

    @Autowired
    public ReviewController(IReviewApiService service) {
        super(service);
    }
}
