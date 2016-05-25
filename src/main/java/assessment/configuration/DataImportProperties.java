package assessment.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties for configuring the data to import
 * Can be used in application.properties/yml
 */
@ConfigurationProperties(prefix = "import")
public class DataImportProperties {

    private boolean users;

    public boolean getUsers() {
        return users;
    }

    public void setUsers(boolean users) {
        this.users = users;
    }

}
