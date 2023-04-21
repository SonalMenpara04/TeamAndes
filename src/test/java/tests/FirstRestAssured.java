package tests;

import com.envision.core.DataProviderArgs;
import com.envision.core.DataProviderUtils;
import com.envision.core.RestAssuredActions;
import com.envision.utils.ApiUtils;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsString;

public class FirstRestAssured {

    @DataProviderArgs(value = "getUser=baseUri,endPoint,statusCode,method")
    @Test(dataProviderClass = DataProviderUtils.class, dataProvider = "jsonDataProvider")
    public void testGetUserAPI(String baseUri, String endPoint, String statusCode, String method) throws IOException {
        Response response = RestAssuredActions.doGetRequest(baseUri, endPoint, method);
        response.then().and().assertThat().statusCode(Integer.parseInt(statusCode))
                .and().assertThat().body(containsString("rachel.howell@reqres.in"));
    }

    @DataProviderArgs(value = "updateUser=baseUri,endPoint,payload,statusCode,method,name,job")
    @Test(dataProviderClass = DataProviderUtils.class, dataProvider = "jsonDataProvider")
    public void testUserUpdatePostAPI(String baseUri, String endPoint, String payload, String statusCode, String method, String name, String job) throws IOException {
        String jsonBody = ApiUtils.getStringBody(System.getProperty("user.dir")
                + payload);
        jsonBody = jsonBody.replaceAll("%name%", name);
        jsonBody = jsonBody.replaceAll("%job%", job);
        Response response = RestAssuredActions.doPutRequest(baseUri, endPoint, method, jsonBody);
        response.then().and().assertThat().statusCode(Integer.parseInt(statusCode))
                .and().assertThat().body(containsString("updatedAt"));
    }
}
