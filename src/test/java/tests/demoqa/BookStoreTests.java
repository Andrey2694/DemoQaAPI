package tests.demoqa;

import helpers.BaseMethods;
import models.BookData;
import org.junit.jupiter.api.*;

import tests.BaseTest;

import java.util.List;

import static helpers.BaseMethods.isBookDataFieldsNull;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static specs.SpecsDemoqa.*;


@Tag("demoqa")
public class BookStoreTests extends BaseTest {

    @Test
    @DisplayName("Get list of books and check that fields not empty")
    void getBooksTest() {
        List<BookData> BooksData = given()
                .spec(bookStoreRequest)
                .when()
                .get("Books")
                .then()
                .spec(STATUS_OK)
                .log().all()
                .extract().body().jsonPath().getList("books", BookData.class);

        assertThat(BooksData, hasSize(greaterThan(0)));
        assertThat(BooksData.stream().allMatch(BaseMethods::isBookDataFieldsNull), is(true));
    }

    @Test
    @DisplayName("Get book using the isbn code and check that author is valid")
    void getBookTest() {
        String isbn = "9781449325862";

        BookData BookData = given()
                .spec(bookStoreRequest)
                .params("ISBN", isbn)
                .when()
                .get("Book")
                .then()
                .spec(STATUS_OK)
                .log().all()
                .extract().as(BookData.class);

        assertThat(isBookDataFieldsNull(BookData), is(true));
        assertThat(BookData.getAuthor(), equalTo("Richard E. Silverman"));
    }

    @Test
    @DisplayName("Trying to get a book using invalid isbn code")
    void getBookFailTest() {
        String isbn = "";

        given()
                .spec(bookStoreRequest)
                .params("ISBN", isbn)
                .when()
                .get("Book")
                .then()
                .spec(STATUS_BAD_REQUEST)
                .log().all()
                .body("code", is("1205"))
                .body("message", is("ISBN supplied is not available in Books Collection!"));
    }
}