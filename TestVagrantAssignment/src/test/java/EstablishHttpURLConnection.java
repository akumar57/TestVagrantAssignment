import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

import org.json.*;

import org.testng.annotations.Test;



public class EstablishHttpURLConnection {


		@Test(priority = 1)
		public static float MyGETRequest() throws IOException, SQLException {
			

			Properties prop = new Properties();
			FileInputStream ip = new FileInputStream("C:/Users/Hp/eclipse-workspace/TestVagrantAssignment/src/main/java/com/config/config.properties");
			try { 
				prop.load(ip);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		    URL urlForGetRequest = new URL(prop.getProperty("API_Path"));
		    String readLine = null;
		    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
		    conection.setRequestMethod("GET");
		    int responseCode = conection.getResponseCode();
		    float TemperatureInCelsius = 0 ;
		    if (responseCode == HttpURLConnection.HTTP_OK) 
		    {
		        BufferedReader in = new BufferedReader(
		        new InputStreamReader(conection.getInputStream()));
		        StringBuffer response = new StringBuffer();
		        while ((readLine = in .readLine()) != null) {  	
		            response.append(readLine);
		            JSONObject  json = new JSONObject (response.toString());   
	        		 String Temperature =  json.getJSONObject("main").get("temp").toString();
	        		 
	        		TemperatureInCelsius = Float.parseFloat(Temperature) - 273 ;  
	        		  System.out.println(TemperatureInCelsius);
		        }
		      
}
			return TemperatureInCelsius;
	    
		}
}
	
	
