package pac1;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import java.util.Arrays;
import java.util.List;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;

public class SerializaitonPojoTest {

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
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		Response response = given()
								.queryParam("key", "qaclick123")
								.body(ap)
							.when()
								.post("/maps/api/place/add/json")
							.then().log().all()
								.assertThat().statusCode(200)
								.extract().response();
	}
}

