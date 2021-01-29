package pac1;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourses;
import pojo.WebAutomation;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OAuth2PojoDeserializationTest {
  @Test
  public void f() throws InterruptedException {
	  //hit Auth Url and get the OTP	  
	  String Url = "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=john123654";
	  
	  //Paste above url in the browser and login to google manually and you get the below url 
	  String codeUrl = "https://rahulshettyacademy.com/getCourse.php?state=john123654&code=4%2F0AY0e-g6EqLS9n-Nw7S-Z8_87Rh0s1ScXKJoqluFk9qaujBy1A1lvDAeyHqfox44Oe2bXZA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
	  String code = codeUrl.split("code=")[1].split("&")[0];
	  System.out.println("OTP code is: "+ code);
	  
	  //hit access token url and get the the access token
	  String responseAccessToken = given().urlEncodingEnabled(false)
			  							.queryParams("code", code)
			  							.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			  							.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
			  							.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
			  							.queryParams("grant_type", "authorization_code")
			  						.when().log().all()
			  							.post("https://www.googleapis.com/oauth2/v4/token").asString();	
	  JsonPath json = new JsonPath(responseAccessToken);
	  String accessToken = json.get("access_token");
	  System.out.println("Access Token is: "+accessToken);
	  
	  //pass the access token and get the response
	  GetCourses gc = given()
			  			.queryParam("access_token", accessToken)			  						
			  			.expect().defaultParser(Parser.JSON)
			  		 .when()
			  		 	.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourses.class);
	  System.out.println(gc.getLinkedIn());
	  System.out.println(gc.getInstructor());
	  
	  List<Api> apiCourses = gc.getCourses().getApi();
	  for(int i=0; i<apiCourses.size(); i++) {
		  if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
			  System.out.println(apiCourses.get(i).getPrice());
		  }
	  }
	  
	  String[] courseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};
	  List<String> expectedCourseTitleList = Arrays.asList(courseTitles);
	  List<WebAutomation> webAutomationCourses = gc.getCourses().getWebAutomation();
	  List<String> actualList = new ArrayList<String>();
	  for(int i=0; i<webAutomationCourses.size(); i++) {
		  actualList.add(webAutomationCourses.get(i).getCourseTitle());
	  }	  
	  Assert.assertTrue(actualList.equals(expectedCourseTitleList));
  }
}

