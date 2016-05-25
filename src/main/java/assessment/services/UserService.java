package assessment.services;

import assessment.entities.user.User;
import assessment.services.interfaces.IUserService;
import assessment.utilities.UrlConstants;
import assessment.utilities.httpclient.jsonparser.DataJsonParser;
import assessment.utilities.httpclient.jsonparser.EmployeeJsonParser;
import assessment.utilities.httpclient.jsonparser.IJsonParser;
import org.apache.http.HttpException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService extends BaseService<User> implements IUserService {

    public UserService() {
        super(UrlConstants.DATA_URL_USERS, User.class, new DataJsonParser());
    }
}
