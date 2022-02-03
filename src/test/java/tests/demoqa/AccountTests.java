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
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static specs.SpecsDemoqa.*;

@Tag("demoqa")
public class AccountTests extends BaseTest {
    private final UserData USER = new UserData(App.config.userLogin(), App.config.userPassword());

    @Test
    @DisplayName("Create new user account")
    void createNewUserTest() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .spec(accountRequest)
                .when()
                .post("User")
                .then()
                .spec(response201)
                .body(matchesJsonSchemaInClasspath("schemas/UserCode201.json"))
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
                .spec(response406)
                .body("code", is("1204"))
                .body("message", is("User exists!"));
    }

    @Test
    @DisplayName("User is authorized")
    void authorizedTest() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .spec(accountRequest)
                .body(USER)
                .when()
                .post("Authorized")
                .then()
                .spec(response200)
                .body(equalTo("true"));
    }

    @Test
    @DisplayName("User is not authorized")
    void notAuthorizedTest() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .spec(accountRequest)
                .body(USER)
                .when()
                .post("Authorized")
                .then()
                .spec(response200)
                .body(equalTo("false"));
    }

    @Test
    @DisplayName("Authorization with empty body in request")
    void authorizedFailTest() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .spec(accountRequest)
                .body("")
                .when()
                .post("Authorized")
                .then()
                .spec(response400)
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
                        .spec(response200)
                        .body(matchesJsonSchemaInClasspath("schemas/GenerateTokenCode200.json"))
                        .extract().as(GenerateTokenData.class);

        assertThat(data.getExpires(), notNullValue());
        assertThat(data.getToken(), notNullValue());
        assertThat(data.getStatus(), is("Success"));
        assertThat(data.getResult(), is("User authorized successfully."));
    }

    @Test
    @DisplayName("Get token with empty body in request")
    void generateTokenFailTest() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .spec(accountRequest)
                .body("")
                .when()
                .post("GenerateToken")
                .then()
                .spec(response400)
                .body("code", is("1200"))
                .body("message", is("UserName and Password required."));
    }
}