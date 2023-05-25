package tek.api.sqa.tests;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tek.api.sqa.base.APITestConfig;
import tek.api.utility.EndPoints;

public class GetAccountTest extends APITestConfig {
       @Test
	   public void GetAccountWithExistingId() {
	 String token = getValidToken();
	 RequestSpecification request = RestAssured.given();
	 request.queryParam("primaryPersonId", 6708);
	 request.header("Authorization", "Bearer " + token);
	 
	 Response response =request.when().get(EndPoints.GET_ACCOUNT.getValue());
	 response.prettyPrint();
	 Assert.assertEquals(response.getStatusCode(), 200);
	 int primaryPersonId = response.jsonPath().get("primaryPerson.id");
	 Assert.assertEquals(primaryPersonId, 6708);
	 
}
}