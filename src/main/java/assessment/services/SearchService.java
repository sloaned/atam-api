package assessment.services;

import assessment.entities.SearchResult;
import assessment.entities.TeamApi;
import assessment.entities.User;
import assessment.services.interfaces.ISearchService;
import org.apache.http.HttpException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsloane on 6/1/2016.
 */
@Service
public class SearchService implements ISearchService {

    public List<SearchResult> search(String searchTerm) throws HttpException {

        searchTerm = searchTerm.toLowerCase();

        ArrayList<SearchResult> results = new ArrayList<SearchResult>();
        UserService userService = new UserService();
        TeamService teamService = new TeamService();

        List<User> users = userService.getAll();
        List<TeamApi> teams = teamService.getAll();
        System.out.println("number of users = " + users.size() + ", number of teams = " + teams.size());

        for (User user : users) {
            String name = user.getFirstName().toLowerCase() + " " + user.getLastName().toLowerCase();

            if (name.contains(searchTerm)) {
                System.out.println("match!!!!!, name = " + name);
                SearchResult result = new SearchResult(user, null);

                results.add(result);
            }
        }

        for (TeamApi team : teams) {
            String name = team.getName().toLowerCase();

            if (name.contains(searchTerm)) {
                System.out.println("match!!!!!, name = " + name);
                SearchResult result = new SearchResult(null, team);

                results.add(result);
            }
        }

        return results;
    }
}
