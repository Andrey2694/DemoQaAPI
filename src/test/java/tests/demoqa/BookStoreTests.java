package tests.demoqa;

import helpers.BookHelper;
import models.BookData;
import org.junit.jupiter.api.*;

import services.BookService;
import tests.BaseTest;

import java.util.List;

import static helpers.RandomNumber.getRandomNumber;
import static helpers.BookHelper.isBookDataFieldsNull;
import static helpers.CustomLogFilter.customLogFilter;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static specs.SpecsDemoqa.*;

@Tag("demoqa")
public class BookStoreTests extends BaseTest {
    private final BookService bookService = new BookService();

    @Test
    @DisplayName("Get list of books and check that fields are not empty")
    void getBooksTest() {
        List<BookData> booksData = bookService.getBookData();

        assertThat(booksData, hasSize(greaterThan(0)));
        assertThat(booksData.stream().allMatch(BookHelper::isBookDataFieldsNull), is(true));
    }

    @Test
    @DisplayName("Get book using the isbn code and check that author is valid")
    void getBookTest() {
        List<BookData> booksData = bookService.getBookData();
        int randomNumber = getRandomNumber(0, booksData.size());
        String isbn = booksData.get(randomNumber).getIsbn();

        BookData BookData = given()
                .filter(customLogFilter().withCustomTemplates())
                .spec(bookStoreRequest)
                .params("ISBN", isbn)
                .when()
                .get("Book")
                .then()
                .spec(response200)
                .body(matchesJsonSchemaInClasspath("schemas/BookCode200.json"))
                .extract().as(BookData.class);

        assertThat(isBookDataFieldsNull(BookData), is(true));
        assertThat(BookData.getAuthor(), equalTo(booksData.get(randomNumber).getAuthor()));
    }

    @Test
    @DisplayName("Get book using empty isbn code")
    void getBookFailTest() {
        given()
                .filter(customLogFilter().withCustomTemplates())
                .spec(bookStoreRequest)
                .params("ISBN", "")
                .when()
                .get("Book")
                .then()
                .spec(response400)
                .body("code", is("1205"))
                .body("message", is("ISBN supplied is not available in Books Collection!"));
    }
}