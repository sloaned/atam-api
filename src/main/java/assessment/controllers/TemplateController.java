package assessment.controllers;

import assessment.entities.Template;
import assessment.services.interfaces.ITemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/templates")
public class TemplateController extends BaseController<Template> {

    @Autowired
    public TemplateController(ITemplateService service) {
        super(service);
    }
}
