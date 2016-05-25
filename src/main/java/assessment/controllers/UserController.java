package assessment.controllers;

import assessment.entities.user.User;
import assessment.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController extends BaseController<User> {

    @Autowired
    public UserController(IUserService service) {
        super(service);
    }
}
