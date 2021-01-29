package pac1;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

public class DynamicJson {
	
	@Test (dataProvider="BookData")
	public void addBookParameterizedTest(String isbn, String aisle) {
		//Passing the json values dynamically from test
		RestAssured.baseURI="http://216.10.245.166";
		Response response = given().log().all()
								.header("Content-Type", "application/json")
								.body(Payload.addBookPayload(isbn, aisle))
							.when().post("/Library/DeleteBook.php")
							.then().log().all().assertThat()
								.statusCode(200).extract().response();
		JsonPath jsonObj = new JsonPath(response.asString());
		System.out.println("Book id is: " + jsonObj.get("ID"));		
	}
	
	@DataProvider(name = "BookData")
	public Object[][] getData() {
		return new Object[][] {{"73390", "aisle3410"}, {"73391", "aisle3411"}, {"73392", "aisle3412"}};
	}

}
