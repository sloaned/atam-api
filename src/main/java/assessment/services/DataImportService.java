package assessment.services;


import assessment.entities.User;
import assessment.utilities.UrlConstants;
import assessment.utilities.httpclient.HttpClient;
import assessment.utilities.httpclient.jsonparser.DataJsonParser;
import assessment.utilities.httpclient.jsonparser.EmployeeJsonParser;
import org.apache.http.HttpException;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hmccardell on 4/29/2016.
 */
@Service
public class DataImportService {

    protected org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass());
    HttpClient client = new HttpClient(new DataJsonParser());

    public DataImportService() {

    }

    public void importUsers(boolean emptyDb) throws HttpException {

        client.setParser(new EmployeeJsonParser());
        String dataUrl = emptyDb ? UrlConstants.DATA_URL_IMPORT : UrlConstants.DATA_URL_SYNC;
        List<User> users = client.getAll(UrlConstants.EMPLOYEE_URL_USER, User.class);
        //if we're doing a full import instead of sync, remove inactive users
        if (emptyDb) {
            users.removeIf(user -> !user.getIsActive());
        }
        client.setParser(new DataJsonParser());
        List<User> savedUsers = client.post(dataUrl, users, List.class);

        logger.info("Imported " + savedUsers.size() + " users");
        logger.trace("User objects imported: " + savedUsers);

    }

    public void insertKudos() throws HttpException {

        client.post(UrlConstants.DATA_URL_TEST_DATA_INSERT, "", null);
    }

    public void insertTeams() throws HttpException {

        client.post(UrlConstants.DATA_URL_TEST_DATA_INSERT, "", null);
    }

    public void insertAssessments() throws HttpException {

        client.post(UrlConstants.DATA_URL_TEST_DATA_INSERT, "", null);
    }
}
