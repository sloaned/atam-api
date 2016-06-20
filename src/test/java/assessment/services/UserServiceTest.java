package assessment.services;

import assessment.entities.User;
import assessment.factories.user.UserFactory;
import assessment.factories.user.UserOption;
import assessment.utilities.httpclient.HttpClient;
import org.apache.http.HttpException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserService service;
    private HttpClient mockClient;
    private static UserFactory factory;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setup() {
        service = new UserService();
        mockClient = mock(HttpClient.class);
        service.setClient(mockClient);
    }


    @BeforeClass
    public static void setUp() {
        factory = new UserFactory();
    }

    @Test
    public void happyGetUsersReturnsAListOfUsers() throws ParseException, HttpException {
        List<User> users = factory.assembleUsersAsList(UserOption.VALID_ACTIVE_DEVELOPER, 5);

        when(mockClient.getAll(service.url, User.class)).thenReturn(users);

        List<User> getUsers = service.getAll();

        assertFalse(getUsers.isEmpty());
    }

    @Test
    public void happyGetUserReturnsAUser() throws ParseException, HttpException {
        User user = factory.assembleUser(UserOption.VALID_ACTIVE_DEVELOPER);

        when(mockClient.get(service.url + "/userid", User.class)).thenReturn(user);

        User getUser = service.get("userid");

        assertTrue(getUser != null);
    }

    @Test
    public void happyPostUserReturnsAUser() throws HttpException {
        User user = factory.assembleUser(UserOption.VALID_ACTIVE_DEVELOPER);

        when(mockClient.post(service.url, user, User.class)).thenReturn(user);

        User postUser = service.create(user);

        assertTrue(user == postUser);
    }

    @Test
    public void happyUpdateUserReturnsUpdateUser() throws HttpException {
        User user = factory.assembleUser(UserOption.VALID_ACTIVE_DEVELOPER);

        when(mockClient.put(service.url + "/userid", user, User.class)).thenReturn(user);

        User putUser = service.update("userid", user);

        assertTrue(user == putUser);
    }

    @Test
    public void sadGetUserThatDoesNotExistThrowsHttpException() throws HttpException {

        expectedEx.expect(HttpException.class);
        when(mockClient.get(service.url + "/userid", User.class)).thenThrow(new HttpException());

        service.get("userid");
    }

    @Test
    public void sadPostUserThatIsInvalidThrowsHttpException() throws HttpException {
        User invalidUser = factory.assembleUser(UserOption.VALID_ACTIVE_DEVELOPER);
        invalidUser.setFirstName(null);

        expectedEx.expect(HttpException.class);
        when(mockClient.post(service.url, invalidUser, User.class)).thenThrow(new HttpException());
        service.create(invalidUser);
    }

    @Test
    public void sadUpdateUserThatDoesNotExistThrowsHttpException() throws HttpException {
        User user = factory.assembleUser(UserOption.VALID_ACTIVE_DEVELOPER);

        expectedEx.expect(HttpException.class);
        when(mockClient.put(service.url + "/userid", user, User.class)).thenThrow(new HttpException());

        service.update("userid", user);
    }
}
