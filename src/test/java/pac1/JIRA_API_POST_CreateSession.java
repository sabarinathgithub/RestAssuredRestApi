package pac1;

import org.apache.commons.codec.binary.Base64;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

public class JIRA_API_POST_CreateSession {
	@Test
	public void addIssueInJira() {
		RestAssured.baseURI="http://localhost:8080";
		
		String body = "{\r\n"
				+ "  \"username\": \"sabarinath\",\r\n"
				+ "  \"password\": \"Sabari6^\"\r\n"
				+ "}";
		
		SessionFilter session = new SessionFilter();
		Response response = given().log().all()
								.header("Content-Type","application/json")
								.body(body)
							.filter(session).when().post("/rest/auth/1/session")
							.then().log().all().assertThat()
								.statusCode(200).extract().response();
		JsonPath jsonString = new JsonPath(response.asString());
		String name = jsonString.get("session.name");
		String value = jsonString.get("session.value");
		System.out.println(name +" "+value);
	}
}
