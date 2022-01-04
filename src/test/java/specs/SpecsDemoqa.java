package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;

public class SpecsDemoqa {
    public static RequestSpecification request = with()
            .basePath("/Account/v1/")
            .contentType(ContentType.JSON);

    public static ResponseSpecification responseOk200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification response201 = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .build();

    public static ResponseSpecification response400 = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .build();

    public static ResponseSpecification response406 = new ResponseSpecBuilder()
            .expectStatusCode(406)
            .build();
}
