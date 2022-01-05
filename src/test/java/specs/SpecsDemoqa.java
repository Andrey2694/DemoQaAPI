package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;

public class SpecsDemoqa {
    public static RequestSpecification accountRequest = with()
            .basePath("/Account/v1/")
            .log().uri()
            .contentType(ContentType.JSON);

    public static RequestSpecification bookStoreRequest = with()
            .basePath("/BookStore/v1/")
            .log().uri()
            .contentType(ContentType.JSON);

    public static ResponseSpecification STATUS_OK = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification STATUS_CREATED = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .build();

    public static ResponseSpecification STATUS_BAD_REQUEST = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .build();

    public static ResponseSpecification STATUS_NOT_ACCEPTABLE = new ResponseSpecBuilder()
            .expectStatusCode(406)
            .build();
}

