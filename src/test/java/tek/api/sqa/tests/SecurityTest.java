package tek.api.sqa.tests;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tek.api.sqa.base.APITestConfig;
import tek.api.utility.EndPoints;

public class SecurityTest extends APITestConfig {
	
	@Test
	public void testGenerateTokenValidUser() {
		
		//first step to set Base URL done at BeforeMethod Annotation
		//Prepare Request.
		//create request Body
		//option 1) creation a Map
		//option2)creating an encapsulated object.
		//option 3) String as JSON object. (not recommended)
		Map<String, String> tokenRequestBody = new HashMap<>();
		tokenRequestBody.put("username", "supervisor");
		tokenRequestBody.put("password", "tek_supervisor");
		//Given prepare Request.
		RequestSpecification request = RestAssured.given().body(tokenRequestBody);
		//Set content type.
		request.contentType(ContentType.JSON);
		//When sending request to end-point
		Response response = request.when().post(EndPoints.TOKEN_GENERATION.getValue());
		//optional to print response in console.
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), 200);
		//Assert token is not null.
		//Using jasonPath we can get value of any entity in response.
		String generatedToken = response.jsonPath().getString("token");
		Assert.assertNotNull(generatedToken);
	}
	
	@Test(dataProvider = "invalidTokenData")
	public void tokenWithInvalidDataTest(String username, String password, String expectedErrorMessage) {
	Map<String, String> tokenRequBody = new HashMap<>();
	tokenRequBody.put("username", username);
	tokenRequBody.put("password", password);
	RequestSpecification request =RestAssured.given().body(tokenRequBody);
	request.contentType(ContentType.JSON);
	Response response =request.when().post(EndPoints.TOKEN_GENERATION.getValue());
	response.prettyPrint();
	Assert.assertEquals(response.getStatusCode(), 400);
	String errorMessage = response.jsonPath().get("errorMessage");
	Assert.assertEquals(errorMessage, expectedErrorMessage);
}
	@DataProvider(name = "invalidTokenData")
	private Object[][]invalidTokenData(){
		Object[][]data = {
				{"WrongUser", "tek_supervisor", "User not found"},
				{"supervisor", "WrongPassword", "Password Not Matched"}
		};
		return data;
	}
}