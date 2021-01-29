package pac1;

import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

public class OAuth2Test {
  @Test
  public void f() throws InterruptedException {
	  //hit Auth Url and get the OTP	  
	  String Url = "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=john123654";
	  
	  //Paste above url in the browser and login to google manually and you get the below url 
	  String codeUrl = "https://rahulshettyacademy.com/getCourse.php?state=john123654&code=4%2F0AY0e-g6uiIKccqJojrFoObwd2tylHlqVE40BzTA_9UtlAFgVZKWVDqIvXW8Yn8YkaNmubA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent#";
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
	  String responseActualRequest = given()
			  								.queryParam("access_token", accessToken)
			  							.when().log().all()
			  								.get("https://rahulshettyacademy.com/getCourse.php").asString();
	  System.out.println("Final response is: "+ responseActualRequest);
  }
}

