package assessment.controllers;

import assessment.entities.Period;
import assessment.services.interfaces.IPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dsloane on 6/20/2016.
 */
@RestController
@RequestMapping(value = "/periods")
public class PeriodController extends BaseController<Period> {

    @Autowired
    public PeriodController(IPeriodService service) {
        super(service);
    }
}
