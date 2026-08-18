package lab1;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Lab1_GetRequestValidation {

    // 1. Non-BDD Approach
    @Test(priority = 1)
    public void testGetRequest_NonBDD() {
        System.out.println("====== Non-BDD Approach ======");

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        RequestSpecification httpRequest = RestAssured.given().relaxedHTTPSValidation();

        Response response = httpRequest.request(Method.GET, "/posts/1");

        // Task 2: Get Status Code
        int statusCode = response.getStatusCode();
        System.out.println("1. Status Code: " + statusCode);
        Assert.assertEquals(statusCode, 200, "Status code should be 200");

        // Task 3 & 4: Print Response Body
        System.out.println("\n2. Response Body:");
        System.out.println(response.getBody().asPrettyString());

        // Task 5: Print Response Status Line
        String statusLine = response.getStatusLine();
        System.out.println("3. Status Line: " + statusLine);

        // Task 6: Print Response Content Type
        String contentType = response.getContentType();
        System.out.println("4. Content Type: " + contentType);

        // Task 7: Display Response Time
        long responseTime = response.getTime();
        System.out.println("5. Response Time: " + responseTime + " ms");
    }

    // 2. BDD Approach
    @Test(priority = 2)
    public void testGetRequest_BDD() {
        System.out.println("\n====== BDD Approach ======");

        given()
            .relaxedHTTPSValidation()
        .when()
            .get("https://jsonplaceholder.typicode.com/posts/1")
        .then()
            .log().all()                                         
            .statusCode(200)                                    
            .statusLine("HTTP/1.1 200 OK")                      
            .contentType("application/json; charset=utf-8")     
            .time(lessThan(5000L));                             
    }
}