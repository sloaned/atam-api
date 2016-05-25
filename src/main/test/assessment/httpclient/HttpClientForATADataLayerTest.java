package assessment.httpclient;

import assessment.entities.user.User;
import assessment.factories.user.UserFactory;
import assessment.factories.user.UserOption;
import assessment.utilities.httpclient.HttpClient;
import assessment.utilities.httpclient.jsonparser.DataJsonParser;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.http.HttpException;
import org.hamcrest.Matcher;
import org.hamcrest.core.StringStartsWith;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static assessment.httpclient.TestConstants.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertTrue;

public class HttpClientForATADataLayerTest {

    @Rule
    //don't use port 8080 since it'll probably be in use by something else
    public WireMockRule wireMockRule = new WireMockRule(4242);

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();


    private static HttpClient client;
    private static UserFactory factory;

    @BeforeClass
    public static void setup() {
        client = new HttpClient();
        factory = new UserFactory();
        client.setParser(new DataJsonParser());
    }


    @Test
    public void happyGetSingleUserResult() throws HttpException {
        stubFor(get(urlEqualTo(SINGLE_RELATIVE_USER_URL))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/hal+json;charset=UTF-8")
                    .withBody(SINGLE_JSON_RESULT)));

        User user = client.get(SINGLE_URL, User.class);

        assertTrue(user.getFirstName().equals("TestGet"));
    }

    @Test
    public void happyGetPopulatedListOfUsersResult() throws HttpException {
        stubFor(get(urlEqualTo(RELATIVE_USER_URL))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/hal+json;charset=UTF-8")
                    .withBody(POPULATED_JSON_LIST_RESULT)));

        List<User> users = client.getAll(USER_URL, User.class);

        assertTrue(users.size() > 0);
    }

    @Test
    public void happyGetEmptyListOfUsersResult() throws HttpException {
        stubFor(get(urlEqualTo(RELATIVE_USER_URL))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/hal+json;charset=UTF-8")
                    .withBody(EMPTY_JSON_LIST_RESULT)));

        List<User> users = client.getAll(USER_URL, User.class);

        assertTrue(users.size() == 0);
    }

    @Test
    public void happyPostSingleUserResult() throws HttpException {
        stubFor(get(urlEqualTo(SINGLE_RELATIVE_USER_URL))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/hal+json;charset=UTF-8")
                        .withBody(SINGLE_JSON_RESULT)));

        stubFor(post(urlEqualTo(RELATIVE_USER_URL))
                .willReturn(aResponse()
                    .withStatus(201)
                    .withHeader("Content-Type", "application/hal+json;charset=UTF-8")
                    .withHeader("Location", SINGLE_URL)));

        User postUser = factory.assembleUser(UserOption.VALID_ACTIVE_DEVELOPER);
        client.post(USER_URL, postUser, User.class);

        verify(1, postRequestedFor(urlEqualTo(RELATIVE_USER_URL)));
    }

    @Test
    public void happyPutSingleUserResult() throws HttpException {
        stubFor(get(urlEqualTo(SINGLE_RELATIVE_USER_URL))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(SINGLE_JSON_RESULT)));

        stubFor(put(urlEqualTo(SINGLE_RELATIVE_USER_URL))
                .willReturn(aResponse()
                        .withStatus(204)
                        .withHeader("Content-Type", "application/hal+json;charset=UTF-8")
                        .withHeader("Location", SINGLE_URL)));

        User putUser = factory.assembleUser(UserOption.VALID_ACTIVE_DEVELOPER);

        User user = client.put(SINGLE_URL, putUser, User.class);

        verify(1, putRequestedFor(urlEqualTo(SINGLE_RELATIVE_USER_URL)));
    }

    @Test
    public void sadRequestWith404NotFoundResponse() throws HttpException {
        stubFor(get(urlEqualTo(INVALID_RELATIVE_URL))
                .willReturn(aResponse()
                        .withStatus(404)));

        Matcher<String> matcher = new StringStartsWith("Error: 404");
        expectedEx.expect(HttpException.class);
        expectedEx.expectMessage(matcher);
        client.get(INVALID_URL, User.class);
    }

    @Test
    public void sadRequestWith400BadRequestResponse() throws HttpException {
        stubFor(post(urlEqualTo(RELATIVE_USER_URL))
                .willReturn(aResponse()
                        .withStatus(400)));

        Matcher<String> matcher = new StringStartsWith("Error: 400");
        expectedEx.expect(HttpException.class);
        expectedEx.expectMessage(matcher);
        client.post(USER_URL, null, User.class);
    }

    @Test
    public void sadRequestWith403ForbiddenResponse() throws HttpException {
        stubFor(get(urlEqualTo(SINGLE_RELATIVE_USER_URL))
                .willReturn(aResponse()
                        .withStatus(403)));

        Matcher<String> matcher = new StringStartsWith("Error: 403");
        expectedEx.expect(HttpException.class);
        expectedEx.expectMessage(matcher);
        client.get(SINGLE_URL, User.class);
    }

    @Test
    public void sadRequestWith405MethodNotAllowedResponse() throws HttpException {
        stubFor(get(urlEqualTo(INVALID_RELATIVE_URL))
                .willReturn(aResponse()
                        .withStatus(405)));

        Matcher<String> matcher = new StringStartsWith("Error: 405");
        expectedEx.expect(HttpException.class);
        expectedEx.expectMessage(matcher);
        client.get(INVALID_URL, User.class);
    }

    @Test
    public void sadDeleteRequestThrowingUnsupportedOperationException() {
        expectedEx.expect(UnsupportedOperationException.class);

        client.delete(null, null);
    }
}
