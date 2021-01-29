package pac1;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.io.File;

import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class End_To_End_Flow {
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
	  Response addIssueResponse = given()
			  						.filter(session)
			  						.header("Content-Type", "application/json")
			  						.body(Payload.addIssuePayload())
			  					 .when().post("/rest/api/2/issue")
			  					 .then().log().all().assertThat().extract().response();
	  JsonPath addIssueJson = new JsonPath(addIssueResponse.asString());
	  String issueId = addIssueJson.get("id");
	  System.out.println("New Issue id is: "+issueId);
	  
	  
	  //Add Comment
	  String commentMessage = "This is Joh's next comments.";
	  Response addCommentResponse = given()
			  							.filter(session)
			  							.pathParam("id", issueId)
			  							.header("Content-Type", "application/json")
			  							.body(Payload.addCommentPayload(commentMessage))
			  						.when().post("/rest/api/2/issue/{id}/comment")
			  						.then().log().all().assertThat()
			  							.statusCode(201)
			  							.extract().response();	  	
	  JsonPath addCommentJson = new JsonPath(addCommentResponse.asString());
	  int commentId = addCommentJson.getInt("id");
			  							
	  
	  //add attachment
	  Response addAttachmentResponse = given()
			  								.filter(session)
			  								.pathParam("id", issueId)
			  								.header("X-Atlassian-Token", "no-check")
			  								.header("Content-Type", "multipart/form-data")
			  								.multiPart(new File("C:\\Users\\nerel\\eclipse-workspace\\RestAssuredRestApi\\JiraSampleFile.txt"))
			  							.when().post("/rest/api/2/issue/{id}/attachments")
			  							.then().log().all().assertThat().statusCode(200).extract().response();
	  	  
	  
	//Get Issue
	  Response getIssueResponse = given()
			  							.filter(session)
			  							.pathParam("id", issueId)
			  					  .when().get("/rest/api/2/issue/{id}")
			  					  .then().log().all().assertThat()
			  					  		.statusCode(200)
			  					  		.extract().response();
	  JsonPath getIssueJson = new JsonPath(getIssueResponse.asString());
	  
	  int noOfComments = getIssueJson.getInt("fields.comment.comments.size()");
	  System.out.println("No of comments is: "+noOfComments);
	  for(int i=0; i<noOfComments; i++) {
		  int actualCommentId = getIssueJson.getInt("fields.comment.comments["+i+"].id");
		  if(actualCommentId == commentId) {
			  String actualCommentMessage = getIssueJson.get("fields.comment.comments["+i+"].body");
			  System.out.println("Comment is: " + actualCommentMessage);
			  Assert.assertEquals(actualCommentMessage, commentMessage);
		  }
	  }
  }
  
  
}
