package assessment.services;

import assessment.entities.team.Team;
import assessment.services.interfaces.ITeamService;
import assessment.utilities.UrlConstants;
import assessment.utilities.httpclient.jsonparser.DataJsonParser;
import org.springframework.stereotype.Service;

@Service
public class TeamService extends BaseService<Team> implements ITeamService {

    public TeamService() {
        super(UrlConstants.DATA_URL_TEAMS, Team.class, new DataJsonParser());
    }
}
