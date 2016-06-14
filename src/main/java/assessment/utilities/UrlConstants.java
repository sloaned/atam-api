package assessment.utilities;

public class UrlConstants {

    //TODO Change base url to reflect real server instead of localhost
    public static final String DATA_BASE_URL = "http://localhost";
    public static final String DATA_URL_PORT = "8080";
    public static final String TOKEN_URL_PORT = "8070";
    public static final String DATA_URL = DATA_BASE_URL + ":" + DATA_URL_PORT;
    public static final String TOKEN_URL = DATA_BASE_URL + ":" + TOKEN_URL_PORT;
    public static final String DATA_URL_USERS = DATA_URL + "/users";
    public static final String DATA_URL_QUESTIONS = DATA_URL + "/questions";
    public static final String DATA_URL_TEMPLATES = DATA_URL + "/templates";
    public static final String DATA_URL_REVIEWS = DATA_URL + "/reviews";
    public static final String DATA_URL_TEAMS = DATA_URL + "/teams";
    public static final String DATA_URL_IMPORT = DATA_URL_USERS + "/import";
    public static final String DATA_URL_SYNC = DATA_URL + "/sync";
    public static final String DATA_URL_KUDO = DATA_URL + "/kudos";
    public static final String DATA_URL_TEAM = DATA_URL + "/teams";
    public static final String DATA_URL_TEST_DATA_INSERT = DATA_URL + "/testDataInsert";

    //Employee service URLs
    public static final String EMPLOYEE_BASE_URL = "http://52.24.45.27";
    public static final String EMPLOYEE_URL_PORT = "8080";
    public static final String EMPLOYEE_URL = EMPLOYEE_BASE_URL + ":" + EMPLOYEE_URL_PORT;
    public static final String EMPLOYEE_URL_USER = EMPLOYEE_URL + "/user";
    public static final String EMPLOYEE_URL_PROJECT = EMPLOYEE_URL + "/project";

    public static final String TOKEN_URL_TOKENS = TOKEN_URL + "/tokens";
}
