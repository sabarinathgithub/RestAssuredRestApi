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

public class Jira_Api_GET_Issue {
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
	  .then().assertThat()
	  	.statusCode(200);
	  
	  
	  //Make sure an Issue is already added in your Jira
	  String issueKey = "RES-20";
	  //Add Comment
	  String commentMessage = "This is the fourth comment by John.";
	  Response addCommentResponse = given()
			  							.filter(session)
			  							.pathParam("key", issueKey)
			  							.header("Content-Type", "application/json")
			  							.body(Payload.addCommentPayload(commentMessage))
			  						.when().post("/rest/api/2/issue/{key}/comment")
			  						.then().assertThat()
			  							.statusCode(201)
			  							.extract().response();	  	
	  JsonPath addCommentJson = new JsonPath(addCommentResponse.asString());
	  int commentId = addCommentJson.getInt("id");
		
	  
	  //Get Issue
	  Response getIssueResponse = given()
			  							.filter(session)
			  							.pathParam("key", issueKey)
			  					  .when().get("/rest/api/2/issue/{key}")
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
