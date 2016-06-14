package assessment.services;

import assessment.entities.KudoApi;
import assessment.entities.Profile;
import assessment.entities.ReviewApi;
import assessment.entities.TeamApi;
import assessment.entities.kudo.Kudo;
import assessment.entities.user.User;
import assessment.services.interfaces.IUserService;
import assessment.utilities.UrlConstants;
import assessment.utilities.httpclient.jsonparser.DataJsonParser;
import org.apache.http.HttpException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService extends BaseService<User> implements IUserService {

    public UserService() {
        super(UrlConstants.DATA_URL_USERS, User.class, new DataJsonParser());
    }

    public User getUserByEmail(String email) throws HttpException {
        List<User> users = client.getAll(url + "?size=300000", User.class);
        System.out.println("in getUserByEmail, email = " + email);

        //System.out.println("users = " + users.toString());
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public Profile getProfile(String id) throws HttpException {
        User user = get(id);
        KudoService kudoService = new KudoService();
        TeamService teamService = new TeamService();
        ReviewService reviewService = new ReviewService();
        List<KudoApi> kudos = kudoService.getKudosByUser(id);
        List<TeamApi> teams = teamService.getTeamsByUser(id);
        List<ReviewApi> reviews = reviewService.getReviewsByUser(id);

        Profile profile = new Profile(user, kudos, teams, reviews);

        return profile;
    }
}
