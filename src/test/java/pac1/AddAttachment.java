package pac1;

import org.testng.annotations.Test;

import files.Payload;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.io.File;

import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AddAttachment {
  @Test
  public void f() {
	  RestAssured.baseURI="http://localhost:8080";
	  
	  SessionFilter session = new SessionFilter();
	  //login
	  given().log().all()
	  	.filter(session)
	  	.header("Content-Type", "application/json")
	  	.body("{ \"username\": \"nerella.sabarinath\", \"password\": \"SabariTeja@2010\" }")
	  .when().log().all()
	  	.post("/rest/auth/1/session")
	  .then().log().all().assertThat()
	  	.statusCode(200);
	  
	  //add issue
	  Response addIssueResponse = given().log().all()
			  						.filter(session)
			  						.header("Content-Type", "application/json")
			  						.body(Payload.addIssuePayload())
			  					 .when().post("/rest/api/2/issue")
			  					 .then().log().all().assertThat().extract().response();
	  JsonPath addIssueJson = new JsonPath(addIssueResponse.asString());
	  String issueId = addIssueJson.get("id");
	  System.out.println("New Issue id is: "+issueId);
	  
	  
	  //Add Comment
	  String commentMessage = "Hi how are you man?";
	  Response addCommentResponse = given().log().all()
			  							.filter(session)
			  							.pathParam("id", issueId)
			  							.header("Content-Type", "application/json")
			  							.body(Payload.addCommentPayload(commentMessage))
			  						.when().post("/rest/api/2/issue/{id}/comment")
			  						.then().log().all().assertThat()
			  							.statusCode(201)
			  							.extract().response();	  							
			  							
	  
	  //add attachment
	  Response addAttachmentResponse = given().log().all()
			  								.filter(session)
			  								.pathParam("id", issueId)
			  								.header("X-Atlassian-Token", "no-check")
			  								.header("Content-Type", "multipart/form-data")
			  								.multiPart(new File("C:\\Users\\nerel\\eclipse-workspace\\RestAssuredRestApi\\JiraSampleFile.txt"))
			  							.when().post("/rest/api/2/issue/{id}/attachments")
			  							.then().log().all().assertThat().statusCode(200).extract().response();
	  
	  
	  //Get Issue
	  Response getIssueResponse = given().log().all()
			  							.filter(session)
			  							.pathParam("id", issueId)
			  					  .when().get("/rest/api/2/issue/{id}")
			  					  .then().log().all().assertThat()
			  					  		.statusCode(200)
			  					  		.extract().response();
	  	
  }
}
