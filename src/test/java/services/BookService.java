package services;

import models.BookData;

import java.util.List;

import static helpers.CustomLogFilter.customLogFilter;
import static io.restassured.RestAssured.given;
import static specs.SpecsDemoqa.STATUS_OK;
import static specs.SpecsDemoqa.bookStoreRequest;

public class BookService {
    public List<BookData> getBookData() {
        List<BookData> BooksData = given()
                .filter(customLogFilter().withCustomTemplates())
                .spec(bookStoreRequest)
                .when()
                .get("Books")
                .then()
                .spec(STATUS_OK)
                .log().all()
                .extract().body().jsonPath().getList("books", BookData.class);
        return BooksData;
    }
}
