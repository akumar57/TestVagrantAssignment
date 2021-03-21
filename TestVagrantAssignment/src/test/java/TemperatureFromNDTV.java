import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
public class TemperatureFromNDTV {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		// TODO Auto-generated method stub

	EstablishHttpURLConnection a = new EstablishHttpURLConnection();
		
	float expectedTempFromAPI = 0;
		try {
			try {
				 expectedTempFromAPI = a.MyGETRequest() ;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		Properties prop = new Properties();
		FileInputStream ip = new FileInputStream("C:/Users/Hp/eclipse-workspace/TestVagrantAssignment/src/main/java/com/config/config.properties");
		try { 
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.setProperty("webdriver.chrome.driver", prop.getProperty("chromeDriverPath"));
		WebDriver driver = new ChromeDriver();
		 
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(prop.getProperty("url"));
		
    	Thread.sleep(3000);
		if(driver.findElement(By.xpath("//*[@class = 'noti_wrap']")).isDisplayed())
		{
			driver.findElement(By.xpath("//*[@class='notnow']")).click();
	    	Thread.sleep(3000);
		}

	
		WebDriverWait w = new WebDriverWait(driver, 15);
		driver.findElement(By.id("h_sub_menu")).click();
		
		w.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space(text()) ='WEATHER']")));
		
		
		driver.findElement(By.xpath("//a[normalize-space(text()) ='WEATHER']")).click();
		
	   
		driver.findElement(By.id("Ahmedabad")).click();
		
		String NDTV_Temp1 = driver.findElement(By.xpath("//*[@title='Ahmedabad']/div/span")).getText();
		
		float NDTV_Temp = Float.parseFloat(NDTV_Temp1.substring(0,2));
		
		
		if(NDTV_Temp == expectedTempFromAPI)
			System.out.println("Test Case Pass..!! . Temperature From both the websites are matching..!!");
	
		else
		{
			System.out.println("TC-1 failed because Temp not matching..On NDTV temp shown ==" + NDTV_Temp + " While from openweathermap website, temp shown is== " + expectedTempFromAPI);
		}
	
		// variance logic
		Float variance = NDTV_Temp - expectedTempFromAPI;
		
		if(Math.abs(variance) <= Float.parseFloat(prop.getProperty("varianceValue"))) // we can customize the value of variance from config.propeorty file
			System.out.println("2nd Test Case passed for variance logic..!! Difference is == " +variance);
		
	
	
		driver.quit();
}
	
}
