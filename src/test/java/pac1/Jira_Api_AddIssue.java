package pac1;

import org.testng.annotations.Test;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

public class Jira_Api_AddIssue {

	@Test
	public void addCommentJiraTest() {
		RestAssured.baseURI="http://localhost:8080";
		
		SessionFilter session = new SessionFilter();
		Response createSessionResponse = given().log().all()
											.header("Content-Type", "application/json")
											.body("{ \"username\": \"nerella.sabarinath\", \"password\": \"SabariTeja@2010\" }")
											.filter(session).when().post("/rest/auth/1/session")
											.then().log().all().extract().response();
		JsonPath json = new JsonPath(createSessionResponse.asString());
		String cookie = json.get("session.value");
				
		Response addIssueResponse = given().log().all()
									.header("Content-Type", "application/json")
									.cookie("JSESSIONID", cookie)
									.body(Payload.addIssuePayload())
								  .when().post("/rest/api/2/issue")
								  .then().log().all().assertThat()
								  	.statusCode(201)
								  	.extract().response();
		JsonPath addIssueJson = new JsonPath(addIssueResponse.asString());
		String issueId = addIssueJson.get("id");
		System.out.println("New issue id is: "+issueId);				
	}
}
