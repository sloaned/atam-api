package assessment.utilities.httpclient;

import assessment.utilities.httpclient.jsonparser.IJsonParser;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A generic http client which can be used to do basic CRUD with
 * Can be autowired in a service
 */
public class HttpClient {

    private Map<String, String> headers = new HashMap<>();
    private IJsonParser parser;

    /**
     * Empty constructor
     */
    public HttpClient() {
        //empty constructor
    }

    /**
     * Constructor that takes a parser
     */
    public HttpClient(IJsonParser parser) {
        this.parser = parser;
    }

    /**
     * Used to get a single result and returns the object
     *
     * @param url The url to request from
     * @param headers The headers to send with the request
     * @param type The type of object
     * @param <T> The type of object
     * @return Returns an object of type T from the request response assuming it's a 200
     *
     * @throws HttpException, should be able to rethrow and spring will handle it
     */
    public <T> T get(String url, Map<String, String> headers, Class<T> type) throws HttpException {
        HttpGet request = constructRequest(url, HttpMethod.GET, headers);

        String entity = getEntity(request);

        if (entity == null) {
            //should probably do something else besides return null
            return null;
        }

        return parser.parseObject(entity, type);
    }

    /**
     * Used to get a single result and returns the object using default headers
     *
     * @param url The url to request from
     * @param type The type of object
     * @param <T> The type of object
     * @return An object of type T
     *
     * @throws HttpException Should be able to rethrow and spring will handle it
     */
    public <T> T get(String url, Class<T> type) throws HttpException {
        return get(url, null, type);
    }

    /**
     * Used to get a list of objects with type T
     *
     * @param url The url to request from
     * @param headers The headers to send with the request
     * @param type The type of object
     * @param <T> The type of object
     * @return A list of objects with type T
     *
     * @throws HttpException Should be able to rethrow and spring will handle it
     */
    public <T> List<T> getAll(String url, Map<String, String> headers, Class<T> type) throws HttpException {
        HttpGet request = constructRequest(url, HttpMethod.GET, headers);
        String entity = getEntity(request);

        if (entity == null) {
            return null;
        }

        return parser.parseList(entity, type);
    }

    /**
     * Used to get a list of objects with type T using the default headers
     *
     * @param url The url to request from
     * @param type The type of object
     * @param <T> The type of object
     * @return A list of objects with type T
     *
     * @throws HttpException Should be able to rethrow and spring will handle it
     */
    public <T> List<T> getAll(String url, Class<T> type) throws HttpException {
        return getAll(url, null, type);
    }

    /**
     * Used to post an object
     *
     * @param url The url to request from
     * @param body The object to send
     * @param headers The headers to send with the request
     * @param type The type of object
     * @param <T> The type of object
     * @return The object that was posted
     *
     * @throws HttpException Should be able to rethrow and spring will handle it
     */
    public <T> T post(String url, T body, Map<String, String> headers, Class<T> type) throws HttpException {
        HttpPost request = constructRequest(url, HttpMethod.POST, headers);
        try {
            request.setEntity(new StringEntity(parser.toJson(body)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //should probably be handled better
            return null;
        }

        String entity = getEntity(request);
        return (entity == null || entity.isEmpty()) ? null : parser.parseObject(entity, type);
    }

    /**
     * Used to post an object using the default headers
     *
     * @param url The url to request from
     * @param body The object to send
     * @param type The type of object
     * @param <T> The type of object
     * @return The object that was posted
     *
     * @throws HttpException Should be able to rethrow and spring will handle it
     */
    public <T> T post(String url, T body, Class<T> type) throws HttpException {
        return post(url, body, null, type);
    }

    /**
     * Used to update an object
     *
     * @param url The url to request from
     * @param body The object to send
     * @param headers The headers to send with the request
     * @param type The type of object
     * @param <T> The type of object
     * @return The object that was updated
     *
     * @throws HttpException Should be able to rethrow and spring will handle it
     */
    public <T> T put(String url, T body, Map<String, String> headers, Class<T> type) throws HttpException {
        HttpPut request = constructRequest(url, HttpMethod.PUT, headers);

        try {
            request.setEntity(new StringEntity(parser.toJson(body)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //should probably be handled better
            return null;
        }

        String entity = getEntity(request);
        if (entity == null) {
            //should probably do something else besides return null
            return null;
        }

        return parser.parseObject(entity, type);
    }

    /**
     * Used to update an object using the default headers
     *
     * @param url The url to request from
     * @param body The object to send
     * @param type The type of object
     * @param <T> The type of object
     * @return The object that was updated
     *
     * @throws HttpException Should be able to rethrow and spring will handle it
     */
    public <T> T put(String url, T body, Class<T> type) throws HttpException {
        return put(url, body, null, type);
    }

    /**
     * Not implemented since the data layer can't delete anything for the moment
     *
     * @param url
     * @param headers
     */
    public void delete(String url, Map<String, String> headers) {
        throw new UnsupportedOperationException("Delete method has not been implemented yet");
    }

    /**
     * Used to set custom headers to be used with a request
     *
     * @param headers A map of headers to used
     */
    public void setHeaders(Map<String, String> headers) {
        this.headers.clear();
        if (headers == null) {
            this.headers.put("Content-Type", "application/json");
        } else {
            this.headers = headers;
        }
    }

    /**
     * Used to add an additional header to be used with a request
     *
     * @param key The name of the header
     * @param value The value of the header
     */
    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    /**
     * Used to remove all headers
     */
    public void clearHeaders() {
        headers.clear();
    }

    private <E extends HttpRequestBase> String getEntity(E request) throws HttpException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response;

        try {
            response = httpclient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
            //should probably be handled better
            return null;
        }

        int responseCode = response.getStatusLine().getStatusCode();
        if (responseCode >= 400) {
            throw new HttpException("Error: " + responseCode + ", message: " + response.getStatusLine().getReasonPhrase());
        }

        //have to manually get the data from post/puts since it doesn't return with the response
        //Should only apply to ATA's Data API, if not this will need refactored
        Header locHeader = response.getFirstHeader("Location");
        if (locHeader != null) {
            HttpGet get = constructRequest(locHeader.getValue(), HttpMethod.GET, null);
            try {
                httpclient.close();
                response.close();
            } catch (IOException e) {
                //should probably be handled better
                e.printStackTrace();
            }
            return getEntity(get);
        }

        String entity = null;
        try {
            entity = EntityUtils.toString(response.getEntity());
            httpclient.close();
            response.close();
        } catch (IOException e) {
            //should probably be handled better
            e.printStackTrace();
        }

        return entity;
    }

    private <E extends HttpRequestBase> E constructRequest(String url, HttpMethod method, Map<String, String> headers) {
        setHeaders(headers);
        final E request;

        switch (method) {
            case GET:
                request = (E) new HttpGet(url);
                break;
            case POST:
                request = (E) new HttpPost(url);
                break;
            case PUT:
                request = (E) new HttpPut(url);
                break;
            case DELETE:
                request = (E) new HttpDelete(url);
                break;
            default:
                throw new IllegalArgumentException("Unknown method");
        }

        this.headers.forEach((key, value) -> request.addHeader(key, value));

        return request;
    }

    /**
     * Set the parser that will be used to
     * @param parser The parse to use
     */
    public void setParser(IJsonParser parser) {
        this.parser = parser;
    }
}