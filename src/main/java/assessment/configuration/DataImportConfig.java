package assessment.configuration;

import assessment.services.DataImportService;
import assessment.services.KudoService;
import assessment.services.PeriodService;
import assessment.services.UserService;
import assessment.services.interfaces.IUserService;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Configuration for custom properties
 */
@Component
@EnableConfigurationProperties(DataImportProperties.class)
public class DataImportConfig {

    @Autowired(required = false)
    private DataImportProperties properties;

    @Autowired
    private DataImportService dataImportService;

    @PostConstruct
    public void init() {

        importUsersFromEmployeeService();
    }

    private void importUsersFromEmployeeService(){

        if (properties.getUsers()) {

            try {
                dataImportService.importUsers(true);
            } catch (HttpException e) {
                e.printStackTrace();
            }
        }
    }

    private void addPreviousReviewPeriods() {
        PeriodService periodService = new PeriodService();

    }
}
