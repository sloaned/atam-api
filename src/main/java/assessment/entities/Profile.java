package assessment.entities;

import assessment.entities.kudo.Kudo;
import assessment.entities.review.Review;
import assessment.entities.team.Team;
import assessment.entities.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dsloane on 6/1/2016.
 */
public class Profile {

    private User user;
    private List<KudoApi> kudos;
    private List<TeamApi> teams;
    private List<ReviewApi> reviews;

    public Profile(User user, List<KudoApi> kudos, List<TeamApi> teams, List<ReviewApi> reviews) {
        this.user = user;
        this.kudos = kudos;
        this.teams = teams;
        this.reviews = reviews;
    }

    public void setUser(User user) {this.user = user;}
    public User getUser() {return user;}
    public void setKudos(ArrayList<KudoApi> kudos) {this.kudos = kudos;}
    public List<KudoApi> getKudos() {return kudos;}
    public void setTeams(ArrayList<TeamApi> teams) {this.teams = teams;}
    public List<TeamApi> getTeams() {return teams;}
    public void setReviews(ArrayList<ReviewApi> reviews) {this.reviews = reviews;}
    public List<ReviewApi> getReviews() {return reviews;}
}
