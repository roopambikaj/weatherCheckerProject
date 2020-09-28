package WeatherCheckerProject.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
	

	//WebDriver object

public static WebDriver open(String browserType) {
	
	if (browserType.equalsIgnoreCase("firefox")) {
		System.setProperty("webdriver.gecko.driver", "/Users/roopambikaj/eclipse-workspace/WeatherCheckerProject/drivers/geckodriver");
		return new FirefoxDriver();
		
  }
  else { 
	  
	  	System.setProperty("webdriver.chrome.driver", "/Users/roopambikaj/eclipse-workspace/WeatherCheckerProject/drivers/chromedriver");
	  	return new ChromeDriver();
		
  }
}
}
