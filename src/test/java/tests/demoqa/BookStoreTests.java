package tests.demoqa;

import models.BookData;
import org.junit.jupiter.api.*;

import tests.BaseTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static specs.SpecsDemoqa.*;


@Tag("demoqa")
public class BookStoreTests extends BaseTest {

    public Boolean isFieldsNotNull(BookData book) {
        assertThat(book.getIsbn(), is(notNullValue()));
        assertThat(book.getTitle(), is(notNullValue()));
        assertThat(book.getSubTitle(), is(notNullValue()));
        assertThat(book.getAuthor(), is(notNullValue()));
        assertThat(book.getPublish_date(), is(notNullValue()));
        assertThat(book.getPublisher(), is(notNullValue()));
        assertThat(book.getPages(), is(notNullValue()));
        assertThat(book.getDescription(), is(notNullValue()));
        assertThat(book.getWebsite(), is(notNullValue()));
        return true;
    }

    @Test
    @DisplayName("Get list of books and check that fields not empty")
    void getBooksTest() {
        List<BookData> BooksData = given()
                .spec(requestBooks)
                .when()
                .get("Books")
                .then()
                .spec(responseOk200)
                .log().all()
                .extract().body().jsonPath().getList("books", BookData.class);

        assertThat(BooksData, hasSize(greaterThan(0)));
        assertThat(BooksData.stream().allMatch(this::isFieldsNotNull), is(true));
    }

    @Test
    @DisplayName("Get book using the isbn code and check that author is valid")
    void getBookTest() {
//        List<BookData> list = BooksData.stream()
//                .filter(x -> x.getTitle().equals("Programming JavaScript Applications"))
//                .collect(Collectors.toList());
//        String isbn = list.get(0).getIsbn();
        String isbn = "9781449325862";


        BookData BookData = given()
                .spec(requestBooks)
                .params("ISBN", isbn)
                .when()
                .get("Book")
                .then()
                .spec(responseOk200)
                .log().all()
                .extract().as(BookData.class);

        assertThat(isFieldsNotNull(BookData), is(true));
        assertThat(BookData.getAuthor(), equalTo("Richard E. Silverman"));
    }

    @Test
    @DisplayName("Trying to get a book using invalid isbn code")
    void getBookFailTest() {
        String isbn = "";

        given()
                .spec(requestBooks)
                .params("ISBN", isbn)
                .when()
                .get("Book")
                .then()
                .spec(response400)
                .log().all()
                .body("code", is("1205"))
                .body("message", is("ISBN supplied is not available in Books Collection!"));
    }
}