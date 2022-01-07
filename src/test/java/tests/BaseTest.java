package tests;

import config.App;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({AllureJunit5.class})
public class BaseTest {
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = App.config.apiUrl();
    }
}
