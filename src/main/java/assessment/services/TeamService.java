package assessment.services;

import assessment.entities.MemberApi;
import assessment.entities.TeamApi;
import assessment.entities.team.Member;
import assessment.entities.team.Team;
import assessment.entities.user.User;
import assessment.services.interfaces.ITeamApiService;
import assessment.utilities.UrlConstants;
import assessment.utilities.httpclient.jsonparser.DataJsonParser;
import org.apache.http.HttpException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService extends BaseService<TeamApi> implements ITeamApiService {

    public TeamService() {
        super(UrlConstants.DATA_URL_TEAMS, TeamApi.class, new DataJsonParser());
    }


    @Override
    public List<TeamApi> getAll() throws HttpException {
        //return client.getAll(url, type);
        ArrayList<TeamApi> teams = new ArrayList<TeamApi>();

        List<Team> teamList = client.getAll(url, Team.class);

        for (Team team : teamList) {
            TeamApi teamApi = new TeamApi();

            teamApi.setId(team.getId());
            teamApi.setAvatar(team.getAvatar());
            teamApi.setDescription(team.getDescription());
            teamApi.setIsActive(team.getIsActive());
            teamApi.setName(team.getName());
            teamApi.setReviewPeriods(team.getReviewPeriods());
            teamApi.setVersion(team.getVersion());
            teamApi.setSummaryScore(team.getSummaryScore());
         /*   ArrayList<MemberApi> members = new ArrayList<MemberApi>();

            UserService userService = new UserService();

            for (Member member : team.getMemberList()) {
                MemberApi memberApi = new MemberApi();
                memberApi.setActive(member.getActive());
                memberApi.setAddedOn(member.getAddedOn());
                memberApi.setRemovedOn(member.getRemovedOn());
                memberApi.setRole(member.getRole());
                memberApi.setUser(userService.get(member.getUserId()));

                members.add(memberApi);
            }
        */
         //   teamApi.setMemberList(members);

            teams.add(teamApi);
        }

        return teams;
    }

    @Override
    public TeamApi get(String id) throws HttpException {
        System.out.println("in teamService getTeam!!!!!!!!!!!!!!!!!!!!!!!!");
        TeamApi teamApi = new TeamApi();
        Team team =  client.get(url + "/" + id, Team.class);
        System.out.println("getting team " + team.getName() + "!!!!!!!!!!!!!!!!");

        teamApi.setId(team.getId());
        teamApi.setAvatar(team.getAvatar());
        teamApi.setDescription(team.getDescription());
        teamApi.setIsActive(team.getIsActive());
        teamApi.setName(team.getName());
        teamApi.setReviewPeriods(team.getReviewPeriods());
        teamApi.setVersion(team.getVersion());
        teamApi.setSummaryScore(team.getSummaryScore());
        ArrayList<MemberApi> members = new ArrayList<MemberApi>();

        UserService userService = new UserService();

        for (Member member : team.getMemberList()) {
            MemberApi memberApi = new MemberApi();
            memberApi.setActive(member.getActive());
            memberApi.setAddedOn(member.getAddedOn());
            memberApi.setRemovedOn(member.getRemovedOn());
            memberApi.setRole(member.getRole());
            memberApi.setUser(userService.get(member.getUserId()));

            members.add(memberApi);
        }
        teamApi.setMemberList(members);

        return teamApi;
    }


    /*
    public Member(String userId, Boolean isActive, Date addedOn, Date removedOn, Role role) {
        this.userId = userId;
        this.isActive = isActive;
        this.addedOn = addedOn;
        this.removedOn = removedOn;
        this.role = role;
    }


    public Team(String name, Boolean isActive, List<Member> memberList, String avatar, String description, List<ReviewPeriod> reviewPeriods, Double summaryScore) {
        this.name = name;
        this.isActive = isActive;
        this.memberList = memberList;
        this.avatar = avatar;
        this.description = description;
        this.reviewPeriods = reviewPeriods;
        this.summaryScore = summaryScore;
        this.version = Constants.TEAM_CURRENT_VERSION;
    }
     */

    @Override
    public TeamApi create(TeamApi teamApi) throws HttpException {
        //return client.post(url, team, type);
        List<Member> teamMembers = new ArrayList<Member>();
        for(MemberApi memberApi : teamApi.getMemberList()) {
            Member member = new Member(memberApi.getUser().getId(), memberApi.getActive(), memberApi.getAddedOn(),
                    memberApi.getRemovedOn(), memberApi.getRole());
            teamMembers.add(member);
        }
        Team team = new Team(teamApi.getName(), teamApi.getIsActive(), teamMembers, teamApi.getAvatar(),
                teamApi.getDescription(), teamApi.getReviewPeriods(), teamApi.getSummaryScore());
        Team returnTeam = client.post(url, team, Team.class);

        if (returnTeam == team) {
            return teamApi;
        }
        return null;
    }

    @Override
    public TeamApi update(String id, TeamApi teamApi) throws HttpException {
        //Need to make sure it exists otherwise mongo will create it with the id because mongo is the worst
        try {
            client.get(url + "/" + id, Team.class);
        } catch (HttpException e) {
            logger.error(e);
            throw e;
        }

        List<Member> teamMembers = new ArrayList<Member>();
        for(MemberApi memberApi : teamApi.getMemberList()) {
            Member member = new Member(memberApi.getUser().getId(), memberApi.getActive(), memberApi.getAddedOn(),
                    memberApi.getRemovedOn(), memberApi.getRole());
            teamMembers.add(member);
        }
        Team team = new Team(teamApi.getName(), teamApi.getIsActive(), teamMembers, teamApi.getAvatar(),
                teamApi.getDescription(), teamApi.getReviewPeriods(), teamApi.getSummaryScore());

        Team returnTeam = client.put(url + "/" + id, team, Team.class);

        if (returnTeam == team) {
            return teamApi;
        }
        return null;
    }
}
