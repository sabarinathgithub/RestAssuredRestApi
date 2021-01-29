package pac1;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import files.Payload;

public class BasicRestAssuredTest {

	public static void main(String[] args) {
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().log().all()
							.queryParam("key", "qaclick123")
							.header("Content-Type", "application/json")
							.body(Payload.requestBody())
						.when().log().all()
							.post("/maps/api/place/add/json")
						.then().log().all().assertThat()
							.statusCode(200)
							.body("scope", equalTo("APP"))
							.header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		//Parsing Response Json
		JsonPath json = new JsonPath(response);
		String placeId = json.getString("place_id");
		System.out.println("Place_id from the response is: " + placeId);
		
		//Update the place
		String newAddress = "70 winter walk2, USA";
		given().log().all()
			.queryParam("key", "qaclick123")
			.header("Content-Type", "application/json")
			.body("{\r\n"
					+ "\"place_id\":\""+placeId+"\",\r\n"
					+ "\"address\":\""+newAddress+"\",\r\n"
					+ "\"key\":\"qaclick123\"\r\n"
					+ "}\r\n"
					+ "")
		.when().put("/maps/api/place/update/json")
		.then().log().all()
			.assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		
		//Verify the updated place
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("key", "qaclick123");
		parameters.put("place_id", placeId);
		String getResponse = given().log().all()
								.queryParams(parameters)
							.when().get("/maps/api/place/get/json")
							.then().log().all().assertThat()
								.statusCode(200).body("address", equalTo(newAddress)).extract().response().asString();
		
		JsonPath getResponseJson = new JsonPath(getResponse);
		String actualAddress = getResponseJson.getString("address");
		System.out.println("Updated address is: " + actualAddress);
			
		Assert.assertEquals(getResponseJson.getString("address"), newAddress);
			
	}

}
