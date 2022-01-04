package tests.demoqa;

import models.BookData;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.BaseTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static specs.SpecsDemoqa.request;
import static specs.SpecsDemoqa.responseOk200;

@Tag("demoqa")
public class BookStoreTests extends BaseTest {
    @Test
    void getBooksTest() {
        List<BookData> BookData = given()
                .spec(request)
                .when()
                .get("Books")
                .then()
                .spec(responseOk200)
                .log().all()
                .extract().body().jsonPath().getList("books", BookData.class);
    }
}