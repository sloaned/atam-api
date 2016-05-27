package assessment.controllers;

import assessment.entities.Response;
import assessment.entities.SuccessfulResponse;
import assessment.entities.TeamApi;
import assessment.entities.team.Team;
import assessment.services.TeamService;
import assessment.services.interfaces.ITeamApiService;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/teams")
public class TeamController extends BaseController<TeamApi> {

    @Autowired
    public TeamController(ITeamApiService service) {
        super(service);
    }

    @RequestMapping(value="/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<Response> getTeamsByUser(@PathVariable String id) {
        try {

            TeamService teamService = new TeamService();
            List<TeamApi> entities = teamService.getTeamsByUser(id);

            if (entities.isEmpty()) {
                System.out.println("Got an empty list");
                logger.info("Got an empty list");
            } else {
                System.out.println("got a list! " + entities);
                logger.debug("Got list: " + entities);
                logger.info("Got list of: " + entities.get(0).getClass().getSimpleName());
            }
            return new ResponseEntity<>(new SuccessfulResponse<>(entities), HttpStatus.OK);

        } catch (HttpException e) {
            System.out.println("HttpException error: " + e);
            logger.error(e);
            return createFailedResponse(e.getMessage());
        }
    }

}
