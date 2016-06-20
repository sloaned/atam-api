package assessment.utilities.httpclient.jsonparser;

import assessment.entities.User;
import assessment.utilities.employees.EmployeeParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A JSON parser to parse entities returned from the Employee Service CAMP project
 */
public class EmployeeJsonParser implements IJsonParser {

    /**
     * Empty method for now
     *
     * @param jsonBody The body of the json to parse
     * @param type The type of object
     * @param <T> The type
     *
     * @return null
     */
    @Override
    public <T> T parseObject(String jsonBody, Class<T> type) {
        return null;
    }

    /**
     * Parses the list of users from the employee service
     *
     * @param jsonBody The body of the json to parse
     * @param type The type of object
     * @param <T> The type
     * @return A list of users
     */
    @Override
    public <T> List<T> parseList(String jsonBody, Class<T> type) {
        JSONArray jsonArray = new JSONArray(jsonBody);

        List<T> users = new ArrayList<>();

        int len = jsonArray.length();
        for (int i = 0; i < len; i++) {
            User user = fromJson(jsonArray.getJSONObject(i).toString(), User.class);
            //reject certain users
            if (user.getFirstName().toLowerCase().equals("test") ||
                    user.getFirstName().toLowerCase().equals("admin") ||
                    user.getLastName().toLowerCase().equals("user") ||
                    user.getLastName().toLowerCase().equals("service") ||
                    user.getEmail() == null || user.getEmail().equals("")) {
                continue;
            }
            users.add((T) user);
        }
        return users;
    }

    /**
     * Parses a employee service JSON object into one of our user entity objects
     *
     * @param jsonObject The body of the json to parse
     * @param type The type of object
     * @param <T> The type
     * @return A user
     */
    @Override
    public <T> T fromJson(String jsonObject, Class<T> type) {
        User user = new User();
        JSONObject json = new JSONObject(jsonObject);

        //extract all the json info
        String firstName = EmployeeParser.getName(json.getString("firstName"));
        String lastName = EmployeeParser.getName(json.getString("lastName"));
        String email = EmployeeParser.getEmail(json.getString("email"));
        String title = json.getString("title");
        boolean isActive = json.getBoolean("isActive");

        SimpleDateFormat format = new SimpleDateFormat("M/DD/yyyy");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = format.parse(json.getString("startDate"));
            endDate = format.parse(json.getString("endDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setTitle(EmployeeParser.getTitle(title));
        user.setEmail(email);
        user.setIsActive(isActive);
        user.setStartDate(startDate);
        user.setEndDate(endDate);
        return (T) user;
    }
}