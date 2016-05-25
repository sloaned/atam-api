package assessment.controllers;

import assessment.entities.kudo.Kudo;
import assessment.services.interfaces.IKudoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hmccardell on 4/29/2016.
 */
@RestController
@RequestMapping(value = "kudos")
public class KudoController extends BaseController<Kudo> {

    @Autowired
    public KudoController(IKudoService service) {
        super(service);
    }


}
