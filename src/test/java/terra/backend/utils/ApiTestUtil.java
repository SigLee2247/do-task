package terra.backend.utils;

import static io.restassured.RestAssured.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.restassured.specification.RequestSpecification;

public class ApiTestUtil {
  public static RequestSpecification getRequestSpecification() {
    return given().log().all().contentType(APPLICATION_JSON_VALUE);
  }
}
