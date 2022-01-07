package tests.demoqa;

import config.App;
import models.GenerateTokenData;
import models.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.BaseTest;

import static helpers.CustomLogFilter.customLogFilter;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static specs.SpecsDemoqa.*;

@Tag("demoqa")
public class AccountTests extends BaseTest {
    private final UserData USER = new UserData(App.config.userLogin(), App.config.userPassword());

    @Test
    @DisplayName("Create new user account and get userId")
    void createNewUserTest() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .spec(accountRequest)
                .body(USER)
                .when()
                .post("User")
                .then()
                .spec(STATUS_CREATED)
                .log().body()
                .body("userID", is(notNullValue()))
                .body("username", is(notNullValue()))
                .body("books", is(notNullValue()));
    }


    @Test
    @DisplayName("Create account when user already exist")
    void createUserFailTest() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .spec(accountRequest)
                .body(USER)
                .when()
                .post("User")
                .then()
                .spec(STATUS_NOT_ACCEPTABLE)
                .log().body()
                .body("code", is("1204"))
                .body("message", is("User exists!"));
    }

    @Test
    @DisplayName("User is authorized")
    void authorizedTest() {
        String response = given()
                .filter(customLogFilter().withCustomTemplates())
                .spec(accountRequest)
                .body(USER)
                .when()
                .post("Authorized")
                .then()
                .spec(STATUS_OK)
                .log().body()
                .extract().response().asString();
        assertThat(response, is("true"));
    }

    @Test
    @DisplayName("User is not authorized")
    void notAuthorizedTest() {
        String response = given()
                .filter(customLogFilter().withCustomTemplates())
                .spec(accountRequest)
                .body(USER)
                .when()
                .post("Authorized")
                .then()
                .spec(STATUS_OK)
                .log().body()
                .extract().response().asString();
        assertThat(response, is("false"));
    }

    @Test
    @DisplayName("Failed authorization with empty body in request")
    void authorizedFailTest() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .spec(accountRequest)
                .body("")
                .when()
                .post("Authorized")
                .then()
                .spec(STATUS_BAD_REQUEST)
                .log().body()
                .body("code", is("1200"))
                .body("message", is("UserName and Password required."));
    }

    @Test
    @DisplayName("Get user token when user is authorized")
    void generateTokenTest() {
        GenerateTokenData data =
                given()
                        .filter(customLogFilter().withCustomTemplates())
                        .spec(accountRequest)
                        .body(USER)
                        .when()
                        .post("GenerateToken")
                        .then()
                        .spec(STATUS_OK)
                        .log().body()
                        .extract().as(GenerateTokenData.class);

        assertThat(data.getExpires(), notNullValue());
        assertThat(data.getToken(), notNullValue());
        assertThat(data.getStatus(), is("Success"));
        assertThat(data.getResult(), is("User authorized successfully."));
    }

    @Test
    @DisplayName("Failed trying to take token with empty body in request")
    void generateTokenFailTest() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .spec(accountRequest)
                .body("")
                .when()
                .post("GenerateToken")
                .then()
                .spec(STATUS_BAD_REQUEST)
                .log().body()
                .body("code", is("1200"))
                .body("message", is("UserName and Password required."));
    }
}