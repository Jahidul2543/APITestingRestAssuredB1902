import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleAPITesting {

    /**
     *
     * Log in --> POST --> using hostname + resources, content type, body (username and password)
     * sessionid
     * Create Issue
     *
     * */


    public String login() throws IOException {

        String url ="http://localhost:8080/rest/auth/1/session";

        String payLoad = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+ "/resources/JiraLogin.json")));

        RequestSpecification requestSpecification = RestAssured.given().body(payLoad);
        requestSpecification.contentType("application/json");
        Response response = requestSpecification.post(url);

        String stringResponse = response.asString();
        JsonPath jsonResponse = new JsonPath(stringResponse);
        String sessionId = jsonResponse.get("session.value");
        System.out.println(sessionId);
        return sessionId;

    }

    @Test

    public void createIssueTest() throws IOException {
        SimpleAPITesting obj = new SimpleAPITesting();
        String sessionId = obj.login();
        String url = "http://localhost:8080/rest/api/2/issue/";

       // String  payload = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/resources/CreateBug.json")));
        String  payload = new String(Files.readAllBytes(Paths.get("/Users/jahidul/IdeaProjects/APITestingRestAssuredB1902/resources/CreateBug.json")));

        /**
         * Specify your request --> Say where is payload, where is url, what type of call
         *
         * */
        RequestSpecification requestSpecification = RestAssured.given().body(payload);
        requestSpecification.contentType("application/json");
        requestSpecification.header("Cookie" , "JSESSIONID=" + sessionId);

        Response response = requestSpecification.post(url);
        String stringResponse = response.asString();

        JsonPath jsonPath = new JsonPath(stringResponse);
        String actual = jsonPath.get("id");
        Assert.assertEquals(actual, "10204");

    }

}
