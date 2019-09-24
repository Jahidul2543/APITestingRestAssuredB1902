import RestAssuredCore.BaseAssertion;
import RestAssuredCore.BaseTest;
import RestAssuredCore.RESTCalls;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.PayloadGenerator;
import utils.URL;

public class CreateIssueTest {

    @Test
    public void createIssue(){

        /**
         *
         * 1. Session id
         * 2. Payload
         * 3. Headers
         * 4. You need to make call--> POST, GET, PUT, DELETE
         * 5. Response --> To String response --> Interact using JsonPath and get the json object value
         * 6. Do assertion of the response --> Status code, response body etc
         *
         * */
        String  sessionId = BaseTest.doLogin();
        String payload = PayloadGenerator.generatePayLoadString("CreateBug.json");
        String url = URL.getEndPoint("/rest/api/2/issue/");
        Response response = RESTCalls.POSTRequest(url,payload,sessionId);
        BaseAssertion.verifyStatusCode(response,201);
       // BaseAssertion.verifyStatusMessage(response,"HTTP/1.1 201");

    }
}
