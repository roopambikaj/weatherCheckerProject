package WeatherCheckerProject;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import WeatherCheckerProject.utilities.DriverFactory;

public class ValidateWeatherChecker {
	
	
	Logger logger = Logger.getLogger(ValidateWeatherChecker.class.getName());
	
	WebDriver driver;

	@BeforeSuite
	public void setup() {
		
		driver = DriverFactory.open("Chrome");
		driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		// 1) Visit the URL https://developer.obiebank.obly.io/
		driver.get("https://serene-mountain-14043.herokuapp.com/");
				
	}

	@Test
	public void validatePostalCode() throws InterruptedException {
		
		// valid entry page
		logger.info("validate the entry page contains search text box and search button");
		Assert.assertEquals("Weather Checker",driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/h2")).getText());
		
		// check the search text
		
		Assert.assertEquals("Enter postcode",driver.findElement(By.xpath("//input[@placeholder='Enter postcode']")).getAttribute("placeholder")); 
		
		// check the search Button
		Assert.assertEquals("Search",driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/form/button")).getText());
		
		// valid incorrect post-code format
		logger.info("valid incorrect post-code format");
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/form/input")).sendKeys("999 9AA");
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/form/button")).click();
		Thread.sleep(500);
		String validateMessage = driver.findElement(By.xpath("/html/body/div[1]/div/h1")).getText();
		Assert.assertTrue("Invalid postcode",validateMessage.contains("Invalid postcode"));
		
		// valid non-existing post-code;
		logger.info("valid non-existing post-code");
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/form/input")).clear();
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/form/input")).sendKeys("B99 9AA");
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/form/button")).click();
		Thread.sleep(500);
		validateMessage = driver.findElement(By.xpath("/html/body/div[1]/div/h1")).getText();
		Assert.assertTrue("Unable to find the postcode.",validateMessage.contains("Unable to find the postcode"));
		
		// valid non-valid post-code;
		logger.info("valid non-valid post-code");
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/form/input")).clear();
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/form/input")).sendKeys("EC1A 1BB");
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/form/button")).click();
		Thread.sleep(500);
		validateMessage = driver.findElement(By.xpath("/html/body/div[1]/div/h1")).getText();

		Assert.assertTrue("Invalid postcode",validateMessage.contains("Invalid postcode"));
		
		
		// valid post-code;
		logger.info("valid post-code");
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/form/input")).clear();
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/form/input")).sendKeys("W6 0NW");
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/form/button")).click();
		Thread.sleep(500);
		
		//check for weather properties - Time, Temperature and Humidity
		logger.info("check for weather properties - Time, Temperature and Humidity");
		Assert.assertEquals("Time:",driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/table/tbody/tr[1]/th")).getText());
		Assert.assertEquals("Temperature:",driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/table/tbody/tr[9]/th")).getText());
		Assert.assertEquals("Humidity:",driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/table/tbody/tr[12]/th")).getText());
	
		//valid date and time format
		String regex = "^\\d{1,2}\\/\\d{1,2}\\/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}";
		logger.info("valid date and time format");
		//Creating a pattern object
	      Pattern pattern = Pattern.compile(regex);
	      //Matching the compiled pattern in the String
	      Matcher matcher = pattern.matcher(driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/table/tbody/tr[1]/td")).getText());
	      boolean bool = matcher.matches();
	      Assert.assertTrue("Date is valid",bool);
		
	}
	
	@AfterSuite
	public void teardown() {
		logger.info("Validation Complete!");
		driver.close();
		driver.quit();

	}

}
