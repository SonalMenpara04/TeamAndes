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

    @DataProviderArgs(value = "GetSingleUserNotFoundData=baseUri,endPoint,methodType,statusCode")
    @Test(dataProviderClass = DataProviderUtils.class, dataProvider = "jsonDataProvider")
    public void testGetSingleUserNotFound(String baseUri, String endPoint, String methodType, String statusCode) {

        Response response = RestAssuredActions.doGetRequest(baseUri, endPoint, methodType);
        response.then().and().assertThat().statusCode(Integer.parseInt(statusCode))
                .and().assertThat().contentType("application/json;charset=utf-8")
                .and().assertThat().body(containsString(""));

    }

    @DataProviderArgs(value = "PostRegisterData=contentType,baseUri,endPoint,payload,methodType,email,password,statusCode")
    @Test(dataProviderClass = DataProviderUtils.class, dataProvider = "jsonDataProvider")
    public void testPostRegisterSuccessful(String contentType, String baseUri, String endPoint, String payload, String methodType, String email, String password, String statusCode) throws IOException {


        String jsonBody = ApiUtils.getStringBody(System.getProperty("user.dir") + payload);
        jsonBody = jsonBody.replaceAll("%email%", email);
        jsonBody = jsonBody.replaceAll("%password%", password);


        Response response = RestAssuredActions.doPostRequest1(contentType, baseUri, endPoint, jsonBody, methodType);
        response.then().and().assertThat().statusCode(Integer.parseInt(statusCode))
                .and().assertThat().body(containsString("token"));


    }

    @DataProviderArgs(value="patchUpdate=baseUri,endPoint,payload,statusCode,methodType")
    @Test (dataProviderClass = DataProviderUtils.class,dataProvider = "jsonDataProvider")

    public void testUserPatchUpdate(String baseUri,String endPoint, String payload, String statusCode,String method) throws Exception {
        String jsonBody = ApiUtils.getStringBody(System.getProperty("user.dir")+ payload);

        Response response=RestAssuredActions.doPatchRequest(baseUri,endPoint,method,jsonBody);
        response.then().and().assertThat().statusCode(Integer.parseInt(statusCode))
                .and().assertThat().body(containsString("updatedAt"));

    }

}
