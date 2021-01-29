package pac1;

import org.testng.Assert;
import files.MockPayload;
import io.restassured.path.json.JsonPath;

public class ParsingComplexMockedJson {
	
	public static void main(String[] args) {
		JsonPath json = new JsonPath(MockPayload.coursePrice());
		int purchaseAmount = json.getInt("dashboard.purchaseAmount");
		System.out.println("purchaseAmount is: " + purchaseAmount);
		
		//Print the number of courses present in the response
		int numberOfCourses = json.getInt("courses.size");
		System.out.println("numberOfCourses is: "+ numberOfCourses);
		
		//Print the first course title
		String Firsttitle = json.getString("courses[0].title");
		System.out.println("First course title is:" + Firsttitle);
		
		//Print title of each course along with the price	
		//Verify the sum of all courses price matches with PurchaseAmount
		int totalCoursePrice=0;
		for(int i=0; i<numberOfCourses; i++) {
			String courseTitle = json.getString("courses["+i+"].title");
			int coursePrice = json.getInt("courses["+i+"].price");
			System.out.println("Course title is: " + courseTitle + " and course price is: " + coursePrice);
			int numberOfCopies = json.getInt("courses["+i+"].copies");
			totalCoursePrice = totalCoursePrice + coursePrice * numberOfCopies;			
		}
		System.out.println("Total price of all courses is: "+totalCoursePrice);		
		Assert.assertEquals(totalCoursePrice, purchaseAmount);
		
		//Print number of copies sold for RPA
		for(int i=0; i<numberOfCourses; i++) {
			String courseTitle = json.get("courses["+i+"].title");
			if(courseTitle.equals("RPA")) {
				System.out.println("Number of copies of RPA is: " + json.getInt("courses["+i+"].copies"));
				break;
			}
		}
	}
}
