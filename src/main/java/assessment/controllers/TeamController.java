package assessment.controllers;

import assessment.entities.team.Team;
import assessment.services.interfaces.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/teams")
public class TeamController extends BaseController<Team> {

    @Autowired
    public TeamController(ITeamService service) {
        super(service);
    }
}
