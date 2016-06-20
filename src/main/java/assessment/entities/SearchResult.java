package assessment.entities;

import assessment.entities.team.Team;
import assessment.entities.User;

/**
 * Created by dsloane on 6/1/2016.
 */
public class SearchResult {

    private User user;
    private TeamApi team;

    public SearchResult() {}

    public SearchResult(User user, TeamApi team) {
        this.user = user;
        this.team = team;
    }

    public void setUser(User user) {this.user = user;}
    public User getUser() {return user;}
    public void setTeam(TeamApi team) {this.team = team;}
    public TeamApi getTeam() {return team;}
}
