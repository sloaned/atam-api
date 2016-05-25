package assessment.services;

import assessment.entities.question.Question;
import assessment.services.interfaces.IQuestionService;
import assessment.utilities.UrlConstants;
import assessment.utilities.httpclient.jsonparser.DataJsonParser;
import org.springframework.stereotype.Service;

@Service
public class QuestionService extends BaseService<Question> implements IQuestionService {

    public QuestionService() {
        super(UrlConstants.DATA_URL_QUESTIONS, Question.class, new DataJsonParser());
    }
}
