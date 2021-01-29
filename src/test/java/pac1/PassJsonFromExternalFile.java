package pac1;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PassJsonFromExternalFile {
	
	@Test
	public void addBookTest() throws IOException {
		String location = "C:\\Sabarinath\\Automation\\API Testing\\RestAssured\\Udemy by Rahul Shetty\\addBookPayload.txt";
		RestAssured.baseURI="http://216.10.245.166";
		Response response = given().log().all()
								.header("Content-Type", "application/json")
								.body(new String(Files.readAllBytes(Paths.get(location))))
							.when().post("/Library/Addbook.php")
							.then().log().all().assertThat()
								.statusCode(200).extract().response();
		
		JsonPath jsonObj = new JsonPath(response.asString());
		String msg = jsonObj.getString("Msg");
		Assert.assertEquals(msg, "successfully added");
		String id = jsonObj.getString("ID");
		System.out.println("Bood id is: " + id);		
	}

}
