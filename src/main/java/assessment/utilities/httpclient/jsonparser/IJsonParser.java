package assessment.utilities.httpclient.jsonparser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Interface that works with HttpClient
 *
 * Used for parsing different types of json responses
 */
public interface IJsonParser {

    ObjectMapper mapper = new ObjectMapper();

    /**
     * Parse json into a single object after manipulating it however needed
     *
     * @param jsonBody The body of the json to parse
     * @param type The type of object
     * @param <T> The type of object
     * @return The parsed object
     */
    <T> T parseObject(String jsonBody, Class<T> type);

    /**
     * Parse json into a list of objects after manipulating it however needed
     *
     * @param jsonBody The body of the json to parse
     * @param type The type of object
     * @param <T> The type of object
     * @return A List of objects
     */
    <T> List<T> parseList(String jsonBody, Class<T> type);

    /**
     * Parse the json into the object you need
     * Should be called within parseObject or parseList
     *
     * @param jsonObject The body of the json to parse
     * @param type The type of object
     * @param <T> The type of object
     * @return The parsed object
     */
    default <T> T fromJson(String jsonObject, Class<T> type) {
        T obj = null;
        try {
            obj = mapper.readValue(jsonObject, type);
        } catch (IOException e) {
            e.printStackTrace();
            throw new EntityNotFoundException("Could not parse entity from json");
        }

        return obj;
    }

    /**
     * Turn the object into json
     *
     * @param obj The object to transform into json
     * @param <T> The type of object
     * @return A json string from the object
     */
    default <T> String toJson(T obj) {
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }
}
