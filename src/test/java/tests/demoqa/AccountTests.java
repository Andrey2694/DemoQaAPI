package tests.demoqa;

import config.App;
import models.GenerateTokenData;
import models.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static specs.SpecsDemoqa.*;

@Tag("demoqa")
public class AccountTests extends BaseTest {
    private final UserData USER = new UserData(App.config.userLogin(), App.config.userPassword());
    private String userId;

    @Test
    @DisplayName("Create new user account and get userId")
    void createNewUserTest() {
        userId = given()
                .spec(request)
                .body(USER.getUserData())
                .when()
                .post("User")
                .then()
                .spec(response201)
                .log().body()
                .body("userID", is(notNullValue()))
                .body("username", is(notNullValue()))
                .body("books", is(notNullValue()))
                .extract().jsonPath().getString("userID");
    }


    @Test
    @DisplayName("Create account when user already exist")
    void createUserFailTest() {
        given()
                .spec(request)
                .body(USER.getUserData())
                .when()
                .post("User")
                .then()
                .spec(response406)
                .log().body()
                .body("code", is("1204"))
                .body("message", is("User exists!"));
    }

    @Test
    @DisplayName("User is authorized")
    void authorizedTest() {
        String response = given()
                .spec(request)
                .body(USER.getUserData())
                .when()
                .post("Authorized")
                .then()
                .spec(responseOk200)
                .log().body()
                .extract().response().asString();
        assertThat(response, is("true"));
    }

    @Test
    @DisplayName("User is not authorized")
    void notAuthorizedTest() {
        String response = given()
                .spec(request)
                .body(USER.getUserData())
                .when()
                .post("Authorized")
                .then()
                .spec(responseOk200)
                .log().body()
                .extract().response().asString();
        assertThat(response, is("false"));
    }

    @Test
    @DisplayName("Failed authorization with empty body in request")
    void authorizedFailTest() {
        given()
                .spec(request)
                .body("")
                .when()
                .post("Authorized")
                .then()
                .spec(response400)
                .log().body()
                .body("code", is("1200"))
                .body("message", is("UserName and Password required."));
    }

    @Test
    @DisplayName("Get user token when user is authorized")
    void generateTokenTest() {
        GenerateTokenData data =
                given()
                        .spec(request)
                        .body(USER.getUserData())
                        .when()
                        .post("GenerateToken")
                        .then()
                        .spec(responseOk200)
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
                .spec(request)
                .body("")
                .when()
                .post("GenerateToken")
                .then()
                .spec(response400)
                .log().body()
                .body("code", is("1200"))
                .body("message", is("UserName and Password required."));
    }
}