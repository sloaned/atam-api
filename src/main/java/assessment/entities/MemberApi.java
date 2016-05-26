package assessment.entities;

import assessment.entities.team.Role;
import assessment.entities.user.User;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by dsloane on 5/25/2016.
 */
public class MemberApi {


    private User user;

    private Boolean isActive;

    private Date addedOn;

    private Date removedOn;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public MemberApi() {
    }

    public MemberApi(User user, Boolean isActive, Date addedOn, Date removedOn, Role role) {
        this.user = user;
        this.isActive = isActive;
        this.addedOn = addedOn;
        this.removedOn = removedOn;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Date getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Date addedOn) {
        this.addedOn = addedOn;
    }

    public Date getRemovedOn() {
        return removedOn;
    }

    public void setRemovedOn(Date removedOn) {
        this.removedOn = removedOn;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Member{" +
                "user='" + user.getFirstName() + ' ' + user.getLastName() + '\'' +
                ", isActive=" + isActive +
                ", addedOn=" + addedOn +
                ", removedOn=" + removedOn +
                ", role=" + role +
                '}';
    }
}
