package assessment.controllers;

import assessment.entities.user.User;
import assessment.factories.user.UserFactory;
import assessment.factories.user.UserOption;
import com.jayway.restassured.builder.ResponseBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import static assessment.controllers.ControllerTestConstants.BASE_URL_INVALID;
import static assessment.controllers.ControllerTestConstants.BASE_URL_USERS;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Tests for the User controller using Rest Assured
 *
 * Make sure both the API and Data project are running or the tests will not work
 */
public class UserControllerTest {

    private static UserFactory factory;

    @BeforeClass
    public static void setup() {
        factory = new UserFactory();
    }

    @Test
    public void happyGetAllReturningListOfUsers() {
        final String accessToken = "accessToken";

        given().
                auth().preemptive().oauth2(accessToken).
                filter((requestSpec, responseSpec, ctx) -> {
                    assertThat(requestSpec.getHeaders().getValue("Authorization"), equalTo("Bearer " + accessToken));
                    return new ResponseBuilder().setBody("ok").setStatusCode(200).build();

                }).
        when()
                .get(BASE_URL_USERS)
                .then()
                .statusCode(200);
    }

    @Test
    public void happyGetSingleUserReturnsAUser() {
        final String accessToken = "accessToken";
        User newUser = factory.assembleUser(UserOption.VALID_ACTIVE_DEVELOPER);
        given().
                auth().preemptive().oauth2(accessToken).
                filter((requestSpec, responseSpec, ctx) -> {
                    assertThat(requestSpec.getHeaders().getValue("Authorization"), equalTo("Bearer "+accessToken));
                    return new ResponseBuilder().setBody("ok").setStatusCode(201).build();

                }).
                contentType("application/json")
                .body(newUser)
                .when().post(BASE_URL_USERS)
                .then()
                .statusCode(201);

        given().
                auth().preemptive().oauth2(accessToken).
                filter((requestSpec, responseSpec, ctx) -> {
                    assertThat(requestSpec.getHeaders().getValue("Authorization"), equalTo("Bearer "+accessToken));
                    return new ResponseBuilder().setBody("ok").setStatusCode(200).build();

                }).
        when()
                .get(BASE_URL_USERS + "/userId")
                .then()
                .statusCode(200);
    }

    @Test
    public void happyPostUserReturnsNewlyCreatedUser() {
        final String accessToken = "accessToken";
        User newUser = factory.assembleUser(UserOption.VALID_ACTIVE_DEVELOPER);
        given().
                auth().preemptive().oauth2(accessToken).
                filter((requestSpec, responseSpec, ctx) -> {
                    assertThat(requestSpec.getHeaders().getValue("Authorization"), equalTo("Bearer "+accessToken));
                    return new ResponseBuilder().setBody("created").setStatusCode(201).build();

                }).
                contentType("application/json")
                .body(newUser)
                .when().post(BASE_URL_USERS)
                .then()
                .statusCode(201);

    }

    @Test
    public void happyPutUserReturnsUpdatedUser() {
        final String accessToken = "accessToken";

        User newUser = factory.assembleUser(UserOption.VALID_ACTIVE_DEVELOPER);
        given().
                auth().preemptive().oauth2(accessToken).
                filter((requestSpec, responseSpec, ctx) -> {
                    assertThat(requestSpec.getHeaders().getValue("Authorization"), equalTo("Bearer "+accessToken));
                    return new ResponseBuilder().setBody("created").setStatusCode(201).build();

                }).
                contentType("application/json")
                .body(newUser)
                .when().post(BASE_URL_USERS)
                .then()
                .statusCode(201);

        given().
                auth().preemptive().oauth2(accessToken).
                filter((requestSpec, responseSpec, ctx) -> {
                    assertThat(requestSpec.getHeaders().getValue("Authorization"), equalTo("Bearer "+accessToken));
                    return new ResponseBuilder().setBody("ok").setStatusCode(200).build();

                }).
                contentType("application/json")
                .body(newUser)
                .when().put(BASE_URL_USERS + "/userid")
                .then()
                .statusCode(200);
    }

    @Test
    public void sadGetUserThatDoesNotExistReturnsAnError() {
        final String accessToken = "accessToken";

        given().
            auth().preemptive().oauth2(accessToken).
                filter((requestSpec, responseSpec, ctx) -> {
                    assertThat(requestSpec.getHeaders().getValue("Authorization"), equalTo("Bearer "+accessToken));
                    return new ResponseBuilder().setBody("Not found").setStatusCode(404).build();

                }).
        when()
                .get(BASE_URL_USERS + "/" + "InvalidId")
                .then()
                .statusCode(404);
    }
    @Test
    public void sadGetAllOfObjectThatDoesNotExistReturnsAnError() {
        final String accessToken = "accessToken";

        given().
                auth().preemptive().oauth2(accessToken).
                filter((requestSpec, responseSpec, ctx) -> {
                    assertThat(requestSpec.getHeaders().getValue("Authorization"), equalTo("Bearer "+accessToken));
                    return new ResponseBuilder().setBody("Not found").setStatusCode(404).build();

                }).
        when()
                .get(BASE_URL_INVALID)
                .then()
                .statusCode(404);
    }

    @Test
    public void sadPostNewInvalidUserReturnsAnError() {
        final String accessToken = "accessToken";

        User badUser = factory.assembleUser(UserOption.VALID_ACTIVE_DEVELOPER);
        badUser.setFirstName(null);
        badUser.setLastName(null);
        badUser.setEmail(null);

        given().
                auth().preemptive().oauth2(accessToken).
                filter((requestSpec, responseSpec, ctx) -> {
                    assertThat(requestSpec.getHeaders().getValue("Authorization"), equalTo("Bearer "+accessToken));
                    return new ResponseBuilder().setBody("internal server error").setStatusCode(500).build();

                }).
                contentType("application/json")
                .body(badUser)
                .when().post(BASE_URL_USERS)
                .then()
                .statusCode(500);
    }

    @Test
    public void sadPutUserThatDoesNotExistReturnsAnError() {
        final String accessToken = "accessToken";

        User user = factory.assembleUser(UserOption.VALID_ACTIVE_DEVELOPER);
        given().
                auth().preemptive().oauth2(accessToken).
                filter((requestSpec, responseSpec, ctx) -> {
                    assertThat(requestSpec.getHeaders().getValue("Authorization"), equalTo("Bearer "+accessToken));
                    return new ResponseBuilder().setBody("not found").setStatusCode(404).build();

                }).
                contentType("application/json")
                .body(user)
                .when().put(BASE_URL_USERS + "/" + "InvalidId")
                .then()
                .statusCode(404);
    }
}
