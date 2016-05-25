package assessment.httpclient;

public class TestConstants {

    public static final String BASE_URL = "http://localhost:4242";

    public static final String RELATIVE_USER_URL = "/users";
    public static final String USER_URL = BASE_URL + RELATIVE_USER_URL;

    public static final String SINGLE_RELATIVE_USER_URL = "/users/5707d64e1e620d133f2fd92f";
    public static final String SINGLE_URL = BASE_URL + SINGLE_RELATIVE_USER_URL;

    public static final String INVALID_RELATIVE_URL = "/invalid";
    public static final String INVALID_URL = BASE_URL + INVALID_RELATIVE_URL;

    public static final String POPULATED_JSON_LIST_RESULT = "{" +
            "\"_embedded\": {" +
            "\"users\": [" +
            "{" +
            "\"id\": \"5707d64e1e620d133f2fd92f\"," +
            "\"firstName\": \"testFirstName\"," +
            "\"lastName\": \"testLastName\"," +
            "\"email\": \"test@test.com\"," +
            "\"role\": \"DEVELOPER\"," +
            "\"avatar\": \"avatar\"," +
            "\"profileDescription\": \"Test description\"," +
            "\"version\": 1," +
            "\"isActive\": true," +
            "\"_links\": {" +
            "\"self\": {" +
            "\"href\": \"http://localhost:8080/users/5707d64e1e620d133f2fd92f\"" +
            "}," +
            "\"user\": {" +
            "\"href\": \"http://localhost:8080/users/5707d64e1e620d133f2fd92f\"" +
            "}" +
            "}" +
            "}," +
            "{" +
            "\"id\": \"5707d64e1e620d133f2fd930\"," +
            "\"firstName\": \"testFirstNameTwo\"," +
            "\"lastName\": \"testLastNameTwo\"," +
            "\"email\": \"test2@test.com\"," +
            "\"role\": \"DEVELOPER\"," +
            "\"avatar\": \"avatar2\"," +
            "\"profileDescription\": \"Test description2\"," +
            "\"version\": 1," +
            "\"isActive\": true," +
            "\"_links\": {" +
            "\"self\": {" +
            "\"href\": \"http://localhost:8080/users/5707d64e1e620d133f2fd930\"" +
            "}," +
            "\"user\": {" +
            "\"href\": \"http://localhost:8080/users/5707d64e1e620d133f2fd930\"" +
            "}" +
            "}" +
            "}" +
            "]" +
            "}," +
            "\"_links\": {" +
            "\"self\": {" +
            "\"href\": \"http://localhost:8080/users\"" +
            "}," +
            "\"profile\": {" +
            "\"href\": \"http://localhost:8080/profile/users\"" +
            "}," +
            "\"search\": {" +
            "\"href\": \"http://localhost:8080/users/search\"" +
            "}" +
            "}," +
            "\"page\": {" +
            "\"size\": 20," +
            "\"totalElements\": 2," +
            "\"totalPages\": 1," +
            "\"number\": 0" +
            "}" +
            "}";
    
    public static final String EMPTY_JSON_LIST_RESULT = "{" +
            "\"_embedded\": {" +
            "\"users\": []" +
            "}," +
            "\"_links\": {" +
            "\"self\": {" +
            "\"href\": \"http://localhost:8080/users\"" +
            "}," +
            "\"profile\": {" +
            "\"href\": \"http://localhost:8080/profile/users\"" +
            "}," +
            "\"search\": {" +
            "\"href\": \"http://localhost:8080/users/search\"" +
            "}" +
            "}," +
            "\"page\": {" +
            "\"size\": 20," +
            "\"totalElements\": 0," +
            "\"totalPages\": 0," +
            "\"number\": 0" +
            "}" +
            "}";
    
    public static final String SINGLE_JSON_RESULT = "{" +
            "\"id\": \"5707d64e1e620d133f2fd92f\"," +
            "\"firstName\": \"TestGet\"," +
            "\"lastName\": \"GetTest\"," +
            "\"email\": \"test@test.com\"," +
            "\"role\": \"DEVELOPER\"," +
            "\"avatar\": \"avatar\"," +
            "\"profileDescription\": \"Test description\"," +
            "\"version\": 1," +
            "\"isActive\": true," +
            "\"_links\": {" +
            "\"self\": {" +
            "\"href\": \"http://localhost:8080/users/5707d64e1e620d133f2fd92f\"" +
            "}," +
            "\"user\": {" +
            "\"href\": \"http://localhost:8080/users/5707d64e1e620d133f2fd92f\"" +
            "}" +
            "}" +
            "}";

    public static final String JSON_POST_DATA = "{" +
            "\"firstName\": \"test name\\'-\"," +
            "\"lastName\": \"test name\\'-\"," +
            "\"email\": \"squag@catalystdevworks.com\"," +
            "\"role\": \"DEVELOPER\"," +
            "\"avatar\": \"205e460b479e2e5b48aec07710c08d50\"," +
            "\"profileDescription\": \"My name is Stuart Smalley, and I\\'m going to help people. I\\'m good enough, smart enough, and gosh darnit, people like me\"," +
            "\"version\": 1," +
            "\"isActive\": true," +
            "}";

    /*
    public static final String JSON_PUT_DATA = "{" +
            "\"firstName\": \"TestPut\"," +
            "\"lastName\": \"PutTest\"," +
            "\"email\": \"test@test.com\"," +
            "\"role\": \"DEVELOPER\"," +
            "\"avatar\": \"The Last Airbender\"," +
            "\"profileDescription\": \"Test description\"," +
            "\"version\": 1," +
            "\"isActive\": true," +
            "}";*/
}
