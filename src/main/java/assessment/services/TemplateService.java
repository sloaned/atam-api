package assessment.services;

import assessment.entities.template.Template;
import assessment.services.interfaces.ITemplateService;
import assessment.utilities.UrlConstants;
import assessment.utilities.httpclient.jsonparser.DataJsonParser;
import org.springframework.stereotype.Service;

@Service
public class TemplateService extends BaseService<Template> implements ITemplateService {

    public TemplateService() {
        super(UrlConstants.DATA_URL_TEMPLATES, Template.class, new DataJsonParser());
    }
}
