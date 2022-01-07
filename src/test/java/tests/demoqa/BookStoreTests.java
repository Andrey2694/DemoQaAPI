package tests.demoqa;

import helpers.BaseMethods;
import models.BookData;
import org.junit.jupiter.api.*;

import services.BookService;
import tests.BaseTest;

import java.util.List;

import static helpers.BaseMethods.getRandomNumber;
import static helpers.BaseMethods.isBookDataFieldsNull;
import static helpers.CustomLogFilter.customLogFilter;
import static io.restassured.RestAssured.given;
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
        assertThat(booksData.stream().allMatch(BaseMethods::isBookDataFieldsNull), is(true));
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
                .spec(STATUS_OK)
                .log().all()
                .extract().as(BookData.class);

        assertThat(isBookDataFieldsNull(BookData), is(true));
        assertThat(BookData.getAuthor(), equalTo(booksData.get(randomNumber).getAuthor()));
    }

    @Test
    @DisplayName("Get a book using empty isbn code")
    void getBookFailTest() {
        String isbn = "";

        given()
                .filter(customLogFilter().withCustomTemplates())
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