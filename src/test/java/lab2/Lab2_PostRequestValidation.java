package lab2;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Lab2_PostRequestValidation {

    // Tasks 1 to 10: Non-BDD Approach
    @Test(priority = 1)
    public void testPostRequest_NonBDD() {
        System.out.println("====== Non-BDD Approach (POST) ======");

        // Specify Base URI
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Create Request Object
        RequestSpecification httpRequest = RestAssured.given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json");

        // Task 3: Create JSON Object payload
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "morpheus");
        requestParams.put("job", "leader");

        // Add JSON payload to request body
        httpRequest.body(requestParams.toJSONString());

        // Task 4: Generate request using POST
        Response response = httpRequest.request(Method.POST, "/posts");

        // Task 5: Get Status Code
        int statusCode = response.getStatusCode();
        System.out.println("1. Status Code: " + statusCode);
        Assert.assertEquals(statusCode, 201, "Status code should be 201 for POST");

        // Task 6 & 7: Print Response & Response Body
        System.out.println("\n2. Response Body:");
        System.out.println(response.getBody().asPrettyString());

        // Task 8: Print Status Line
        String statusLine = response.getStatusLine();
        System.out.println("3. Status Line: " + statusLine);

        // Task 9: Print Content Type
        String contentType = response.getContentType();
        System.out.println("4. Content Type: " + contentType);

        // Task 10: Display Response Time
        long responseTime = response.getTime();
        System.out.println("5. Response Time: " + responseTime + " ms");
    }

    // Task 11: BDD Approach
    @Test(priority = 2)
    public void testPostRequest_BDD() {
        System.out.println("\n====== BDD Approach (POST) ======");

        // Task 3: Create JSON Object
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "morpheus");
        requestParams.put("job", "leader");

        given()
            .relaxedHTTPSValidation()
            .header("Content-Type", "application/json")
            .body(requestParams.toJSONString())
        .when()
            .post("https://jsonplaceholder.typicode.com/posts")
        .then()
            .log().all()                                         // Tasks 6, 7, 8, 9: Print response details
            .statusCode(201)                                    // Task 5: Validate Status Code (201 Created)
            .statusLine("HTTP/1.1 201 Created")                // Task 8: Validate Status Line
            .contentType("application/json; charset=utf-8")     // Task 9: Validate Content Type
            .time(lessThan(5000L));                             // Task 10: Validate Response Time
    }
}