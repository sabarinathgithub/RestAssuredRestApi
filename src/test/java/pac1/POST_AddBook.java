package pac1;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class POST_AddBook {
	
	@Test
	public void addBookTest() {
		RestAssured.baseURI="http://216.10.245.166";
		Response response = given().log().all()
								.header("Content-Type", "application/json")
								.body(Payload.addBookPayload("aisle4201", "19541"))
							.when().post("/Library/Addbook.php")
							.then().log().all().assertThat()
								.statusCode(200).extract().response();
		
		JsonPath jsonObj = new JsonPath(response.asString());
		String msg = jsonObj.getString("Msg");
		Assert.assertEquals(msg, "successfully added");
		String id = jsonObj.getString("ID");
		System.out.println("Bood id is: " + id);
		
		//delete the book
		given().log().all()
			.body(Payload.deleteBookPayload(id))
		.when().post("/Library/DeleteBook.php")
		.then().log().all().assertThat()
			.statusCode(200);
	}

}
