package pac1;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import java.util.Arrays;
import java.util.List;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

public class SpecBuilderTest {
	@Test
	public void SerializationTest() {
		
		AddPlace ap = new AddPlace();
		ap.setAccuracy(40);
		ap.setName("Frontline house");
		ap.setAddress("29, side layout, cohen 091");
		ap.setPhone_number("(+91) 983 893 3937");
		ap.setWebsite("http://google.com");
		ap.setLanguage("French-IN");		
		List<String> types = Arrays.asList("shoe park", "shop");
		ap.setTypes(types);		
		Location loc = new Location();
		loc.setLat(-39.2345);
		loc.setLng(34.333);
		ap.setLocation(loc);		
		
		RequestSpecification requestSpec = new RequestSpecBuilder()
											.setBaseUri("https://rahulshettyacademy.com")
											.addQueryParam("key", "qaclick123")
											.setContentType(ContentType.JSON)
											.build();
			
		RequestSpecification request = given().spec(requestSpec).body(ap);
		
		ResponseSpecification responseSpec = new ResponseSpecBuilder()
												.expectContentType(ContentType.JSON)
												.expectStatusCode(200)
												.build();
		Response response = request.when().post("/maps/api/place/add/json")
								.then().spec(responseSpec).extract().response();
		
		String responseString = response.asString();
		System.out.println(responseString);
	}
}

