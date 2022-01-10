package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
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

    public static ResponseSpecification response200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.BODY)
            .build();

    public static ResponseSpecification response201 = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(LogDetail.BODY)
            .build();

    public static ResponseSpecification response400 = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .log(LogDetail.BODY)
            .build();

    public static ResponseSpecification response406 = new ResponseSpecBuilder()
            .expectStatusCode(406)
            .log(LogDetail.BODY)
            .build();
}
