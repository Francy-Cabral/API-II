package endPoints;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import AllData.readExcel;
import AllData.writeExcel;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GenerateEndPoint {
	readExcel rx= new readExcel();
	public static List<Object[]> output = new ArrayList<Object[]>();
	
	@BeforeTest 
    public void setup() {
		RestAssured.baseURI ="https://reqres.in/";
	}
	
	@SuppressWarnings("static-access")
	@DataProvider(name = "ExcelData")
	public Object[][] readData() {
		// {"request Type"},{"request uri"}
		return rx.excelData;
		
	}

	
	
	
	@Test(dataProvider = "ExcelData")
	public void validateRequest(String requestType, String endpoint, String ReqPrams) {

		Response response;
		RequestSpecification responseSpecification = RestAssured.given().contentType(ContentType.JSON);
		String[] RequestPrams;
		

		int statuscode;
		String productId = "";
		String respBody;
		String respHeader;

		output.add(new Object[] { "Test Status", "Response Code", "Response ", "Body", "Headers" });

		switch (requestType) {
		case "GET":
			
			
			
			response = responseSpecification.get(endpoint);

			statuscode = response.getStatusCode();
			respBody = response.asPrettyString();
			if (statuscode==200) {
				
				output.add(new Object[] { "Passed",statuscode, response.toString(), respBody });

				break;
				
			}
			else {
				output.add(new Object[] { "Failed",statuscode, response.toString(), respBody });
				break;
			}
			
			
			
		case "POST":
			//RequestPrams = ReqPrams.split(",");
			response = responseSpecification.body(ReqPrams).post(endpoint);
			productId=responseSpecification.post(endpoint).then().extract().path("id");
			statuscode = response.getStatusCode();
			respBody = response.asPrettyString();
			respHeader=response.getHeaders().toString();
			
            if (statuscode==201) {
				
				output.add(new Object[] { "Passed",statuscode, response.toString(), respBody, respHeader });

				break;
				
			}
			else {
				output.add(new Object[] { "Failed",statuscode, response.toString(), respBody, respHeader });
				break;
			}
            
		case "PUT":
			response = responseSpecification.body(ReqPrams).put(endpoint+productId);
			
			statuscode = response.getStatusCode();
			respBody = response.asPrettyString();
			respHeader=response.getHeaders().toString();
			
            if (statuscode==200) {
				
				output.add(new Object[] { "Passed",statuscode, response.toString(), respBody, respHeader });

				break;
				
			}
			else {
				output.add(new Object[] { "Failed",statuscode, response.toString(), respBody, respHeader });
				break;
			}
            
		case "DELETE":
            response = responseSpecification.delete(endpoint+productId);
			
			statuscode = response.getStatusCode();
			respBody = response.asPrettyString();
			respHeader=response.getHeaders().toString();
			
            if (statuscode==202 || statuscode==204) {
				
				output.add(new Object[] { "Passed",statuscode, response.toString(), respBody, respHeader });

				break;
				
			}
			else {
				output.add(new Object[] { "Failed",statuscode, response.toString(), respBody, respHeader });
				break;
			}
			
			


}
	}
	@AfterTest
	public void writeToExcel() {
		writeExcel wx = new writeExcel(output);
	}

}
