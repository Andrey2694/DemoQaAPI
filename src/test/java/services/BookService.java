package services;

import models.BookData;

import java.util.List;

import static helpers.CustomLogFilter.customLogFilter;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static specs.SpecsDemoqa.response200;
import static specs.SpecsDemoqa.bookStoreRequest;

public class BookService {
    public List<BookData> getBookData() {
        List<BookData> BooksData = given()
                .filter(customLogFilter().withCustomTemplates())
                .spec(bookStoreRequest)
                .when()
                .get("Books")
                .then()
                .spec(response200)
                .body(matchesJsonSchemaInClasspath("schemas/BooksCode200.json"))
                .extract().body().jsonPath().getList("books", BookData.class);
        return BooksData;
    }
}
