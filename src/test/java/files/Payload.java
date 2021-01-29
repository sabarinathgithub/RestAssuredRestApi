package files;

public class Payload {
	
	public static String requestBody() {
		return "{\r\n"
				+ "  \"location\": {\r\n"
				+ "    \"lat\": -38.383494,\r\n"
				+ "    \"lng\": 33.427362\r\n"
				+ "  },\r\n"
				+ "  \"accuracy\": 50,\r\n"
				+ "  \"name\": \"Frontline house\",\r\n"
				+ "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
				+ "  \"address\": \"29, side layout, cohen 09\",\r\n"
				+ "  \"types\": [\r\n"
				+ "    \"shoe park\",\r\n"
				+ "    \"shop\"\r\n"
				+ "  ],\r\n"
				+ "  \"website\": \"http://google.com\",\r\n"
				+ "  \"language\": \"French-IN\"\r\n"
				+ "}";
	}
	
	public static String coursePrice() {
		return "{\r\n"
				+ "  \"dashboard\": {\r\n"
				+ "    \"purchaseAmount\": 910,\r\n"
				+ "    \"website\": \"rahulshettyacademy.com\"\r\n"
				+ "  },\r\n"
				+ "  \"courses\": [\r\n"
				+ "    {\r\n"
				+ "      \"title\": \"Selenium Python\",\r\n"
				+ "      \"price\": 50,\r\n"
				+ "      \"copies\": 6\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"title\": \"Cypress\",\r\n"
				+ "      \"price\": 40,\r\n"
				+ "      \"copies\": 4\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"title\": \"RPA\",\r\n"
				+ "      \"price\": 45,\r\n"
				+ "      \"copies\": 10\r\n"
				+ "    }\r\n"
				+ "  ]\r\n"
				+ "}";
	}
	
	public static String addBookPayload(String isbn, String aisle) {
		String payload = "{\r\n"
				+ "    \r\n"
				+ "\"name\": \"Learning Selenium by JohnSmith\",\r\n"
				+ "\"isbn\": \""+isbn+"\",\r\n"
				+ "\"aisle\": \""+aisle+"\",\r\n"
				+ "\"author\": \"John Smith\"\r\n"
				+ "}";
		return payload;
	}
	
	public static String deleteBookPayload(String bookId) {
		return "{    \r\n"
				+ "\"ID\": \""+bookId+"\"\r\n"
				+ "}";
	}
	
	public static String addIssuePayload() {
		return "{\r\n"
				+ "    \"fields\": {\r\n"
				+ "        \"project\": {\r\n"
				+ "            \"key\": \"RES\"\r\n"
				+ "        },\r\n"
				+ "        \"summary\": \"Debit card payment issue1\",\r\n"
				+ "        \"description\": \"description of naa bonda\",\r\n"
				+ "        \"issuetype\": {\"name\": \"Bug\"}\r\n"
				+ "    }\r\n"
				+ "}";
	}
	
	public static String addCommentPayload(String commentMessage) {
		return "{\r\n"
				+ "    \"body\": \""+commentMessage+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}";
	}

}
