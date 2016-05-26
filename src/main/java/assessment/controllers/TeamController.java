package assessment.controllers;

import assessment.entities.TeamApi;
import assessment.entities.team.Team;
import assessment.services.interfaces.ITeamApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/teams")
public class TeamController extends BaseController<TeamApi> {

    @Autowired
    public TeamController(ITeamApiService service) {
        super(service);
    }
}
