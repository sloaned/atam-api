package assessment.controllers;

import assessment.entities.question.Question;
import assessment.services.interfaces.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/questions")
public class QuestionController extends BaseController<Question> {

    @Autowired
    public QuestionController(IQuestionService service) {
        super(service);
    }
}
