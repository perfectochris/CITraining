package mainTest.HomeDelivery;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import pageClasses.BostonGlobeHomePage;
import pageClasses.BostonGlobeSubscriptionPage;

public class HomeDeliverySignup {
	public String SELENIUM_HUB_URL;
	public String TARGET_SERVER_URL;
	public WebDriver driver;
	public WebDriverWait wait;
	public boolean device;

	@Parameters({ "targetEnvironment" })
	
	@BeforeTest
	public void beforeTest(String targetEnvironment)
			throws UnsupportedEncodingException, MalformedURLException {

		DesiredCapabilities capabilities = new DesiredCapabilities();

		switch (targetEnvironment) {
		case "Galaxy S5":
			device = true;
			capabilities.setCapability("platformName", "Android");
			// capabilities.setCapability("description", "Patrick");
			capabilities.setCapability("browserName", "mobileChrome");
			break;

		case "iPhone 6":
			device = true;
			capabilities.setCapability("platformName", "iOS");
			// capabilities.setCapability("description", "Patrick");
			capabilities.setCapability("browserName", "mobileSafari");
			break;

		case "Internet Explorer 11":
			device = false;
			DesiredCapabilities.internetExplorer();
			capabilities.setCapability("platform", Platform.ANY);
			capabilities.setCapability("browserName", "internet explorer");
			capabilities.setCapability("version", "11");
			capabilities
					.setCapability(
							InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
							true);
			break;

		case "Internet Explorer 10":
			device = false;
			capabilities.setCapability("platform", Platform.ANY);
			capabilities.setCapability("browserName", "internet explorer");
			capabilities.setCapability("version", "10");
			break;

		case "Firefox 34":
			device = false;
			capabilities.setCapability("platform", Platform.ANY);
			capabilities.setCapability("browserName", "firefox");
			capabilities.setCapability("version", "34.0");
			break;

		case "Firefox 35":
			device = false;
			capabilities.setCapability("platform", Platform.ANY);
			capabilities.setCapability("browserName", "firefox");
			capabilities.setCapability("version", "35.0");
			break;

		case "Chrome":
			device = false;
			capabilities.setCapability("platform", Platform.ANY);
			capabilities.setCapability("browserName", "chrome");
			capabilities.setCapability("version", "");
			break;
		}

		TARGET_SERVER_URL = getConfigurationProperty("TARGET_SERVER_URL",
				"test.target.server.url",
				"http://homedelivery.bostonglobe.com/");
		/*
		 * String user = System.getProperty("PerfectoUsername"); String password
		 * = System.getProperty("PerfectoPassword"); String host =
		 * System.getProperty("PerfectoCloud");
		 */
		String user = "chrise@perfectomobile.com";
		String password = "Perfecto1234";
		String host = "partners.perfectomobile.com";

		if (device) {

			System.out.println(targetEnvironment + ": device");

			user = URLEncoder.encode(user, "UTF-8");
			password = URLEncoder.encode(password, "UTF-8");
			URL gridURL = new URL("https://" + user + ':' + password + '@'
					+ host + "/nexperience/wd/hub");

			SELENIUM_HUB_URL = getConfigurationProperty("SELENIUM_HUB_URL",
					"test.selenium.hub.url", gridURL.toString());

		} else {
			System.out.println(targetEnvironment + ": desktop");
			;
			SELENIUM_HUB_URL = getConfigurationProperty("SELENIUM_HUB_URL",
					"test.selenium.hub.url",
					"http://seleniumgrid.perfectomobilelab.net:4444/wd/hub");

		}

		driver = new RemoteWebDriver(new URL(SELENIUM_HUB_URL), capabilities);

		// test starts in Codes entity list page
		driver.get(TARGET_SERVER_URL);
		System.out.println(SELENIUM_HUB_URL + " " + capabilities.getPlatform());

		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

		// driver.findElement(By.xpath("//a[text()='Home Delivery']")).click();
		wait = new WebDriverWait(driver, 20);
	}

	@Test
	public void mainTest() {
		System.out.println("### Opening homepage ###");
		driver.get("http://homedelivery.bostonglobe.com/");		
		
		BostonGlobeHomePage homepage = new BostonGlobeHomePage(driver);
		homepage.enterZip("02018");
		homepage.selectSubscriptionLength();
		
		BostonGlobeSubscriptionPage subpage = new BostonGlobeSubscriptionPage(driver);
		subpage.enterSubscriptionDetails();

	}

	
	
	@AfterTest
	public void afterTest() {
		driver.quit();
	}

	private static String getConfigurationProperty(String envKey,
			String sysKey, String defValue) {
		String retValue = defValue;
		String envValue = System.getenv(envKey);
		String sysValue = System.getProperty(sysKey);
		// system property prevails over environment variable
		if (sysValue != null) {
			retValue = sysValue;
		} else if (envValue != null) {
			retValue = envValue;
		}
		return retValue;
	}
	

}
